package z3fol.xpr;

import com.microsoft.z3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import z3fol.antlr.XPRBaseListener;
import z3fol.antlr.XPRParser;
import z3fol.model.State;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentListener extends XPRBaseListener {

    private static final Logger log = LoggerFactory.getLogger(DocumentListener.class);
    private List<BoolExpr> facts = new ArrayList<>();
    private final Context z3ctx;
    private State rootState;

    private List<Expr> arguments = new ArrayList<>();

    public DocumentListener(Context z3ctx, State state) {
        this.z3ctx = z3ctx;
        this.rootState = state;

        // Add default types
        state.putSort("Int", z3ctx.mkIntSort());
        state.putSort("Bool", z3ctx.mkBoolSort());
    }

    @Override
    public void enterDocument(XPRParser.DocumentContext ctx) {
        ctx.line().forEach(this::parseLine);
    }

    private void parseLine(XPRParser.LineContext ctx) {
        //log.debug("Parsing line: {}", ctx.getText());

        if (ctx.typeDeclaration() != null) parseTypeDeclaration(ctx.typeDeclaration(), rootState);
        if (ctx.variableDeclaration() != null) parseVariableDeclaration(ctx.variableDeclaration(), rootState);
        if (ctx.variableAssignment() != null) parseVariableAssignment(ctx.variableAssignment(), rootState);
        if (ctx.factDeclaration() != null) facts.add(parseQuantifiedStatement(ctx.factDeclaration().quantifiedStatement(), rootState.copy()));

    }

    private void parseVariableAssignment(XPRParser.VariableAssignmentContext ctx, State state) {
        String name = ctx.variableIdentifier().getText();
        Expr value = parseAnyExpression(ctx.anyExpression(), state);

        Sort expectedSort = state.getExpr(name).getSort();
        Sort obtainedSort = value.getSort();

        if (!expectedSort.equals(obtainedSort)) {
            throw new XPRTypeMismatchException(expectedSort, obtainedSort);
        }

        state.putExpr(name, value);
        state.addExpr(value);
    }

    private BoolExpr parseQuantifiedStatement(XPRParser.QuantifiedStatementContext ctx, State state) {

        if (ctx.variableWithTypeList() == null) return parseStatement(ctx.statement(), state);

        List<String> declaredVars = ctx.variableWithTypeList()
                .variableWithType()
                .stream()
                .flatMap(c -> declareVariableFromContext(c, state).stream())
                .collect(Collectors.toList());

        BoolExpr body = parseStatement(ctx.statement(), state);

        Sort[] argTypes = declaredVars.stream().map(s -> state.getExpr(s).getSort()).toArray(Z3Utils::sortAllocator);
        Symbol[] argSymbols = declaredVars.stream().map(z3ctx::mkSymbol).toArray(Z3Utils::symbolAllocator);

        if (ctx.FORALL() != null) return z3ctx.mkForall(argTypes, argSymbols, body, 1, null, null, null, null);
        if (ctx.EXISTS() != null) return z3ctx.mkExists(argTypes, argSymbols, body, 1, null, null, null, null);
        throw new IllegalStateException();
    }

    private BoolExpr parseStatement(XPRParser.StatementContext ctx, State state) {
        BoolExpr[] res = ctx.disjunction().stream().map(c -> parseDisjunction(c, state)).toArray(Z3Utils::boolExprAllocator);
        if (ctx.logop() != null) {
            if (ctx.logop().IMPLIES() != null) return z3ctx.mkImplies(res[0], res[1]);
            if (ctx.logop().IFF() != null) return z3ctx.mkIff(res[0], res[1]);
            if (ctx.logop().XOR() != null) return z3ctx.mkXor(res[0], res[1]);
            throw new IllegalStateException();
        }
        return res[0];
    }

    private BoolExpr parseDisjunction(XPRParser.DisjunctionContext ctx, State state) {
        BoolExpr[] res = ctx.conjunction().stream().map(c -> parseConjunction(c, state)).toArray(Z3Utils::boolExprAllocator);
        return (res.length == 1) ? res[0] : z3ctx.mkOr(res);
    }

    private BoolExpr parseConjunction(XPRParser.ConjunctionContext ctx, State state) {
        BoolExpr[] res = ctx.statementAtom().stream().map(c -> parseStatementAtom(c, state)).toArray(Z3Utils::boolExprAllocator);
        return (res.length == 1) ? res[0] : z3ctx.mkAnd(res);
    }

    private BoolExpr parseStatementAtom(XPRParser.StatementAtomContext ctx, State state) {

        BoolExpr result = null;

        if (ctx.boolExpression() != null) return parseBoolExpression(ctx.boolExpression(), state);
        if (ctx.eqStatement() != null) result = parseEqStatement(ctx.eqStatement(), state);
        if (ctx.cmpStatement() != null) result = parseCmpStatement(ctx.cmpStatement(), state);
        if (ctx.setStatement() != null) result = parseSetStatement(ctx.setStatement(), state);
        if (ctx.subsetExpression() != null) result = parseSubsetExpression(ctx.subsetExpression(), state);
        if (ctx.quantifiedStatement() != null) result = parseQuantifiedStatement(ctx.quantifiedStatement(), state.copy());
        if (ctx.variable() != null) result = (BoolExpr) parseVariable(ctx.variable(), state);
        if (result == null) throw new IllegalStateException();
        if (ctx.NOT() != null) result = z3ctx.mkNot(result);

        return result;
    }

    private BoolExpr parseSubsetExpression(XPRParser.SubsetExpressionContext ctx, State state) {
        Expr set0 = parseSetExpression(ctx.setExpression(0), state);
        Expr set1 = parseSetExpression(ctx.setExpression(1), state);
        return (BoolExpr) z3ctx.mkSetSubset(set0, set1);
    }

    private BoolExpr parseSetStatement(XPRParser.SetStatementContext ctx, State state) {
        Expr e1 = parseAnyExpression(ctx.anyExpression(), state);
        Expr e2 = parseSetExpression(ctx.setExpression(), state);
        BoolExpr inSet = (BoolExpr) z3ctx.mkSetMembership(e1, e2);
        if (ctx.setop().SET_IN() != null) return inSet;
        if (ctx.setop().SET_NOTIN() != null) return z3ctx.mkNot(inSet);
        throw new IllegalStateException();
    }

    private BoolExpr parseCmpStatement(XPRParser.CmpStatementContext ctx, State state) {
        ArithExpr e1 = parseArithExpression(ctx.arithExpression(0), state);
        ArithExpr e2 = parseArithExpression(ctx.arithExpression(1), state);
        if (ctx.cmpop().LT() != null) return z3ctx.mkLt(e1, e2);
        if (ctx.cmpop().LE() != null) return z3ctx.mkLe(e1, e2);
        if (ctx.cmpop().GT() != null) return z3ctx.mkGt(e1, e2);
        if (ctx.cmpop().GE() != null) return z3ctx.mkGe(e1, e2);
        throw new IllegalStateException();
    }

    private BoolExpr parseEqStatement(XPRParser.EqStatementContext ctx, State state) {
        Expr e1 = parseAnyExpression(ctx.anyExpression(0), state);
        Expr e2 = parseAnyExpression(ctx.anyExpression(1), state);
        if (ctx.eqop().EQ() != null) return z3ctx.mkEq(e1, e2);
        if (ctx.eqop().NEQ() != null) return z3ctx.mkNot(z3ctx.mkEq(e1, e2));
        throw new IllegalStateException();
    }

    private Expr parseAnyExpression(XPRParser.AnyExpressionContext ctx, State state) {
        if (ctx.arithExpression() != null) return parseArithExpression(ctx.arithExpression(), state);
        if (ctx.setExpression() != null) return parseSetExpression(ctx.setExpression(), state);
        if (ctx.variable() != null) return parseVariable(ctx.variable(), state);
        if (ctx.boolExpression() != null) return parseBoolExpression(ctx.boolExpression(), state);
        throw new IllegalStateException();
    }

    private BoolExpr parseBoolExpression(XPRParser.BoolExpressionContext ctx, State state) {
        if (ctx.KWD_TRUE() != null) return z3ctx.mkTrue();
        if (ctx.KWD_FALSE() != null) return z3ctx.mkFalse();
        if (ctx.quantifiedStatement() != null) return parseQuantifiedStatement(ctx.quantifiedStatement(), state);
        throw new IllegalStateException();
    }

    private Expr parseVariable(XPRParser.VariableContext ctx, State state) {
        if (ctx.variableIdentifier() != null) return parseVariableIdentifier(ctx.variableIdentifier(), state);
        if (ctx.variableTuple() != null) return parseVariableTuple(ctx.variableTuple(), state);
        throw new IllegalStateException();
    }

    private Expr parseVariableIdentifier(XPRParser.VariableIdentifierContext ctx, State state) {
        Expr result = state.getExpr(ctx.getText());
        if (result == null) throw new UnknownVariableException(ctx.getText());
        return result;
    }

    private Expr parseVariableTuple(XPRParser.VariableTupleContext ctx, State state) {
        Expr[] vars = ctx.anyExpression().stream().map(c -> parseAnyExpression(c, state)).toArray(Z3Utils::exprAllocator);
        String tupleName = ctx.typeIdentifier().getText();
        Sort sort = state.getSort(tupleName);
        if (sort == null) throw new IllegalStateException("Cannot use type " + tupleName + " - type not declared");
        if (!(sort instanceof TupleSort)) throw new IllegalStateException("Type " + tupleName + " is not a tuple");
        TupleSort tupleSort = (TupleSort) sort;
        return tupleSort.mkDecl().apply(vars);
    }

    private Expr parseSetExpression(XPRParser.SetExpressionContext ctx, State state) {
        return parseSetSum(ctx.setSum(), state);

    }

    private Expr parseSetSum(XPRParser.SetSumContext ctx, State state) {
        Expr[] exprs = ctx.setMul().stream().map(c -> parseSetMul(c, state)).toArray(Z3Utils::exprAllocator);
        Expr accu = exprs[0];
        int i = 0;
        for (XPRParser.SetSumOpContext op: ctx.setSumOp()) {
            i += 1;
            if (op.SET_UNION() != null) {
                accu = z3ctx.mkSetUnion(accu, exprs[i]);
            } else { // set difference
                accu = z3ctx.mkSetDifference(accu, exprs[i]);
            }
        }
        return accu;
    }

    private Expr parseSetMul(XPRParser.SetMulContext ctx, State state) {
        Expr[] exprs = ctx.setAtom().stream().map(c -> parseSetAtom(c, state)).toArray(Z3Utils::exprAllocator);
        return exprs.length == 1 ? exprs[0] : z3ctx.mkSetIntersection(exprs);
    }

    private Expr parseSetAtom(XPRParser.SetAtomContext ctx, State state) {
        if (ctx.variable() != null) return parseVariable(ctx.variable(), state);
        if (ctx.setExpression() != null) return parseSetExpression(ctx.setExpression(), state);
        if (ctx.inlineSet() != null) return parseInlineSet(ctx.inlineSet(), state);
        throw new IllegalStateException();
    }

    private Expr parseInlineSet(XPRParser.InlineSetContext ctx, State state) {
        if (ctx.inlineSetEmpty() != null) return parseInlineSetEmpty(ctx.inlineSetEmpty(), state);
        if (ctx.inlineSetValues() != null) return parseInlineSetValues(ctx.inlineSetValues(), state);
        throw new IllegalStateException();
    }

    private Expr parseInlineSetEmpty(XPRParser.InlineSetEmptyContext ctx, State state) {
        String name = ctx.typeIdentifier().getText();
        Sort domain = state.getSort(name);
        if (domain == null) {
            throw new UnknownSortException(name);
        }
        return z3ctx.mkEmptySet(domain);
    }

    private Expr parseInlineSetValues(XPRParser.InlineSetValuesContext ctx, State state) {
        Expr[] vars = ctx.anyExpressionList().anyExpression().stream().map(c -> parseAnyExpression(c, state)).toArray(Z3Utils::exprAllocator);
        Sort setElementSort = vars[0].getSort();
        Expr set = z3ctx.mkEmptySet(setElementSort);
        for(Expr element: vars) {
            if (!setElementSort.equals(element.getSort())) {
                throw new XPRTypeMismatchException(setElementSort, element.getSort());
            }
            set = z3ctx.mkSetAdd(set, element);
        }
        return set;
    }

    private ArithExpr parseArithExpression(XPRParser.ArithExpressionContext ctx, State state) {
        return parseArithSum(ctx.arithSum(), state);
    }

    private ArithExpr parseArithSum(XPRParser.ArithSumContext ctx, State state) {
        ArithExpr[] values = ctx.arithMul().stream().map(c -> parseArithMul(c, state)).toArray(Z3Utils::arithExprAllocator);

        ArithExpr expr = values[0];
        List<z3fol.antlr.XPRParser.SumopContext> ops = ctx.sumop();
        for (int i = 0; i < ops.size(); ++i) {
            if (ops.get(i).PLUS() != null) {
                expr = z3ctx.mkAdd(expr, values[i + 1]);
            } else {
                expr = z3ctx.mkSub(expr, values[i + 1]);
            }
        }
        return expr;
    }


    private ArithExpr parseArithMul(XPRParser.ArithMulContext ctx, State state) {
        ArithExpr[] values = ctx.arithPow().stream().map(c -> parseArithPow(c, state)).toArray(Z3Utils::arithExprAllocator);

        ArithExpr expr = values[0];
        List<z3fol.antlr.XPRParser.MulopContext> ops = ctx.mulop();
        for (int i = 0; i < ops.size(); ++i) {
            if (ops.get(i).TIMES() != null) {
                expr = z3ctx.mkMul(expr, values[i + 1]);
            } else {
                expr = z3ctx.mkDiv(expr, values[i + 1]);
            }
        }
        return expr;
    }

    private ArithExpr parseArithPow(XPRParser.ArithPowContext ctx, State state) {
        ArithExpr base = parseArithAtom(ctx.arithAtom(), state);
        if (ctx.POW() != null) {
            ArithExpr exponent = parseArithExpression(ctx.arithExpression(), state);
            return z3ctx.mkPower(base, exponent);
        } else return base;
    }

    private ArithExpr parseArithAtom(XPRParser.ArithAtomContext ctx, State state) {
        if (ctx.variable() != null) return (ArithExpr) parseVariable(ctx.variable(), state);
        if (ctx.number() != null) return parseNumber(ctx.number());
        if (ctx.arithExpression() != null) return parseArithExpression(ctx.arithExpression(), state);
        throw new IllegalStateException();
    }

    private ArithExpr parseNumber(XPRParser.NumberContext ctx) {
        return z3ctx.mkInt(ctx.getText());
    }

    private void parseVariableDeclaration(XPRParser.VariableDeclarationContext ctx, State state) {
        declareVariableFromContext(ctx.variableWithType(), state);
        if (ctx.anyExpression() != null) {
            String name = ctx.variableWithType().variableIdentifier().getText();
            Expr value = parseAnyExpression(ctx.anyExpression(), state);
            Sort expectedSort = state.getExpr(name).getSort();
            Sort obtainedSort = value.getSort();
            if (!expectedSort.equals(obtainedSort)) {
                throw new XPRTypeMismatchException(expectedSort, obtainedSort);
            }
            state.putExpr(name, value);
            state.addExpr(value);
        }
    }

    private List<String> declareVariableFromContext(XPRParser.VariableWithTypeContext ctx, State state) {
        return declareVariable(ctx.variableIdentifier().getText(), getType(ctx.type(), state), state);
    }

    private List<String> declareVariable(String name, Sort sort, State state) {
        List<String> result = new ArrayList<>();
       // if (state.getExpr(name) != null) {
       //     log.warn("Variable \"" + name + "\" redeclared - IGNORING");
       // }
        if (sort instanceof TupleSort) {
            TupleSort tuplesort = (TupleSort) sort;
            // instead of making a new tuple const, we make consts for each element of a tuple...
            int i = 0;
            List<Expr> tupleArgs = new ArrayList<>();
            for(FuncDecl fd: tuplesort.getFieldDecls()) {
                String subName = name + "." + i;
                Sort s = fd.getRange();
                result.addAll(declareVariable(subName, s, state));
                tupleArgs.add(state.getExpr(subName));
                i += 1;
            }
            // ...and create a tuple based o the declared args
            Expr[] tupleArgsArray = tupleArgs.stream().toArray(Z3Utils::exprAllocator); //Java, why are you so ugly?
            state.putExpr(name, tuplesort.mkDecl().apply(tupleArgsArray));
            state.addExpr(tuplesort.mkDecl().apply(tupleArgsArray));
            // the name of a tuple is not inserted, as it is not subject to a quantifier
        } else {
            state.putExpr(name, z3ctx.mkConst(name, sort));
            state.addExpr(z3ctx.mkConst(name, sort));
            result.add(name);
        }
        return result;
    }

    public void parseTypeDeclaration(XPRParser.TypeDeclarationContext ctx, State state) {
        String name = ctx.typeIdentifier().getText();
        if (ctx.type() == null) {
            state.putSort(name, z3ctx.mkUninterpretedSort(name));
        } else {
            state.putSort(name, getType(name, ctx.type(), state));
        }
    }

    private Sort getType(XPRParser.TypeContext ctx, State state) {
        return getType("__anonymous__", ctx, state);
    }

    private Sort getType(String name, XPRParser.TypeContext ctx, State state) {
        if (ctx.typeIdentifier() != null) {
            String idName = ctx.typeIdentifier().getText();
            Sort sort = state.getSort(idName);
            if (sort == null) throw new UnknownSortException(idName);
            return sort;
        }

        if (ctx.typeTuple() != null) {
            List<XPRParser.TypeContext> subTypes = ctx.typeTuple().type();
            Symbol[] fieldNames = new Symbol[subTypes.size()];
            Sort[] fieldSorts = new Sort[subTypes.size()];
            for (int i = 0; i < fieldNames.length; ++i) fieldNames[i] = z3ctx.mkSymbol(i);
            for (int i = 0; i < fieldSorts.length; ++i) fieldSorts[i] = getType(subTypes.get(i), state);
            return z3ctx.mkTupleSort(z3ctx.mkSymbol(name), fieldNames, fieldSorts);
        }
        if (ctx.typeSet() != null) return parseTypeSet(ctx.typeSet(), state);
        throw new IllegalStateException();
    }

    private Sort parseTypeSet(XPRParser.TypeSetContext ctx, State state) {
        return z3ctx.mkSetSort(state.getSort(ctx.typeIdentifier().getText()));
    }



    public List<BoolExpr> facts() {
        return facts;
    }


    public List<Expr> argumenst() {
        return arguments;
    }
}