package evaluation.filesystem.invariant;

import com.microsoft.z3.*;
import evaluation.filesystem.filesystem;

/**
 * Created by najafzad on 14/03/16.
 */
public class te {

    public BoolExpr invariant(Context ctx) throws Z3Exception {

        // get precondition of operation op1
        BoolExpr precondition_old = app.getPrecondition(ctx, op1));

        // get invariant
        BoolExpr invariant = app.getInvariants(ctx, op1));

        BoolExpr expr = ctx.mkAnd(invariant, precondition_old);

        // apply effect of operation op2
        Expr effect = app.apply(ctx, op2);

        // get precondition of operation op1 over new state
        BoolExpr precondition_new = app.preCondition(ctx, op1));

        // stabilityRule
        BoolExpr stabilityRule = ctx.mkImplies(expr, precondition_new);

        //create a new solver
        Solver solver = ctx.mkSolver();

        // solve the negation of proof
        solver.add(ctx.mkNot(correct));

        // find a model(counter-example) that  satisfies the solver's rule
        Model m = st.Check(ctx, solver, Status.SATISFIABLE);
    }