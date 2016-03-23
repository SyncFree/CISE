// Generated from XPR.g4 by ANTLR 4.5.1
package z3fol.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class XPRParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, LPAREN=4, RPAREN=5, LBRACKET=6, RBRACKET=7, PLUS=8, 
		MINUS=9, TIMES=10, DIV=11, GT=12, GE=13, LE=14, LT=15, EQ=16, NEQ=17, 
		ASSIGN=18, POINT=19, POW=20, OR=21, AND=22, IMPLIES=23, IFF=24, XOR=25, 
		NOT=26, SEMICOLON=27, SET_IN=28, SET_NOTIN=29, SET_UNION=30, SET_INTERSECT=31, 
		SET_DIFF=32, SET_SUB=33, EXISTS=34, FORALL=35, KWD_TYPE=36, KWD_DECLARE=37, 
		KWD_ASSERT=38, KWD_TRUE=39, KWD_FALSE=40, NUM_DECIMAL=41, IDENTIFIER_UC=42, 
		IDENTIFIER_LC=43, Whitespace=44, Newline=45, BlockComment=46, LineComment=47;
	public static final int
		RULE_number = 0, RULE_type = 1, RULE_typeIdentifier = 2, RULE_typeTuple = 3, 
		RULE_typeSet = 4, RULE_variable = 5, RULE_variableTuple = 6, RULE_variableIdentifier = 7, 
		RULE_variableWithType = 8, RULE_variableWithTypeList = 9, RULE_anyExpression = 10, 
		RULE_anyExpressionList = 11, RULE_sumop = 12, RULE_mulop = 13, RULE_arithExpression = 14, 
		RULE_arithExpressionList = 15, RULE_arithSum = 16, RULE_arithMul = 17, 
		RULE_arithPow = 18, RULE_arithAtom = 19, RULE_setExpression = 20, RULE_setSum = 21, 
		RULE_setMul = 22, RULE_setSumOp = 23, RULE_setAtom = 24, RULE_inlineSet = 25, 
		RULE_inlineSetValues = 26, RULE_inlineSetEmpty = 27, RULE_cmpop = 28, 
		RULE_cmpStatement = 29, RULE_eqop = 30, RULE_eqStatement = 31, RULE_setop = 32, 
		RULE_setStatement = 33, RULE_subsetExpression = 34, RULE_boolExpression = 35, 
		RULE_logop = 36, RULE_quantifiedStatement = 37, RULE_statement = 38, RULE_disjunction = 39, 
		RULE_conjunction = 40, RULE_statementAtom = 41, RULE_typeDeclaration = 42, 
		RULE_factDeclaration = 43, RULE_variableDeclaration = 44, RULE_variableAssignment = 45, 
		RULE_document = 46, RULE_line = 47;
	public static final String[] ruleNames = {
		"number", "type", "typeIdentifier", "typeTuple", "typeSet", "variable", 
		"variableTuple", "variableIdentifier", "variableWithType", "variableWithTypeList", 
		"anyExpression", "anyExpressionList", "sumop", "mulop", "arithExpression", 
		"arithExpressionList", "arithSum", "arithMul", "arithPow", "arithAtom", 
		"setExpression", "setSum", "setMul", "setSumOp", "setAtom", "inlineSet", 
		"inlineSetValues", "inlineSetEmpty", "cmpop", "cmpStatement", "eqop", 
		"eqStatement", "setop", "setStatement", "subsetExpression", "boolExpression", 
		"logop", "quantifiedStatement", "statement", "disjunction", "conjunction", 
		"statementAtom", "typeDeclaration", "factDeclaration", "variableDeclaration", 
		"variableAssignment", "document", "line"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'0'", "','", "':'", "'('", "')'", "'{'", "'}'", "'+'", "'-'", "'*'", 
		"'/'", "'>'", "'>='", "'<='", "'<'", "'=='", "'!='", "':='", "'.'", "'^'", 
		"'|'", "'&'", "'=>'", "'<=>'", "'XOR'", "'¬'", "';'", "'in'", "'notin'", 
		"'∪'", "'∩'", "'\\'", "'⊂'", "'Exists'", "'Forall'", "'type'", "'declare'", 
		"'assert'", "'true'", "'false'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, "LPAREN", "RPAREN", "LBRACKET", "RBRACKET", "PLUS", 
		"MINUS", "TIMES", "DIV", "GT", "GE", "LE", "LT", "EQ", "NEQ", "ASSIGN", 
		"POINT", "POW", "OR", "AND", "IMPLIES", "IFF", "XOR", "NOT", "SEMICOLON", 
		"SET_IN", "SET_NOTIN", "SET_UNION", "SET_INTERSECT", "SET_DIFF", "SET_SUB", 
		"EXISTS", "FORALL", "KWD_TYPE", "KWD_DECLARE", "KWD_ASSERT", "KWD_TRUE", 
		"KWD_FALSE", "NUM_DECIMAL", "IDENTIFIER_UC", "IDENTIFIER_LC", "Whitespace", 
		"Newline", "BlockComment", "LineComment"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "XPR.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public XPRParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class NumberContext extends ParserRuleContext {
		public TerminalNode NUM_DECIMAL() { return getToken(XPRParser.NUM_DECIMAL, 0); }
		public TerminalNode MINUS() { return getToken(XPRParser.MINUS, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitNumber(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_number);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			_la = _input.LA(1);
			if (_la==MINUS) {
				{
				setState(96);
				match(MINUS);
				}
			}

			setState(99);
			_la = _input.LA(1);
			if ( !(_la==T__0 || _la==NUM_DECIMAL) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TypeIdentifierContext typeIdentifier() {
			return getRuleContext(TypeIdentifierContext.class,0);
		}
		public TypeTupleContext typeTuple() {
			return getRuleContext(TypeTupleContext.class,0);
		}
		public TypeSetContext typeSet() {
			return getRuleContext(TypeSetContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_type);
		try {
			setState(104);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(101);
				typeIdentifier();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(102);
				typeTuple();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(103);
				typeSet();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeIdentifierContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER_UC() { return getToken(XPRParser.IDENTIFIER_UC, 0); }
		public TypeIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterTypeIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitTypeIdentifier(this);
		}
	}

	public final TypeIdentifierContext typeIdentifier() throws RecognitionException {
		TypeIdentifierContext _localctx = new TypeIdentifierContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_typeIdentifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			match(IDENTIFIER_UC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeTupleContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(XPRParser.LPAREN, 0); }
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(XPRParser.RPAREN, 0); }
		public TypeTupleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeTuple; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterTypeTuple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitTypeTuple(this);
		}
	}

	public final TypeTupleContext typeTuple() throws RecognitionException {
		TypeTupleContext _localctx = new TypeTupleContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_typeTuple);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			match(LPAREN);
			setState(109);
			type();
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(110);
				match(T__1);
				setState(111);
				type();
				}
				}
				setState(116);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(117);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeSetContext extends ParserRuleContext {
		public TypeIdentifierContext typeIdentifier() {
			return getRuleContext(TypeIdentifierContext.class,0);
		}
		public TerminalNode LBRACKET() { return getToken(XPRParser.LBRACKET, 0); }
		public TerminalNode RBRACKET() { return getToken(XPRParser.RBRACKET, 0); }
		public TypeSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeSet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterTypeSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitTypeSet(this);
		}
	}

	public final TypeSetContext typeSet() throws RecognitionException {
		TypeSetContext _localctx = new TypeSetContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_typeSet);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			typeIdentifier();
			setState(120);
			match(LBRACKET);
			setState(121);
			match(RBRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableContext extends ParserRuleContext {
		public VariableIdentifierContext variableIdentifier() {
			return getRuleContext(VariableIdentifierContext.class,0);
		}
		public VariableTupleContext variableTuple() {
			return getRuleContext(VariableTupleContext.class,0);
		}
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitVariable(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_variable);
		try {
			setState(125);
			switch (_input.LA(1)) {
			case IDENTIFIER_LC:
				enterOuterAlt(_localctx, 1);
				{
				setState(123);
				variableIdentifier();
				}
				break;
			case IDENTIFIER_UC:
				enterOuterAlt(_localctx, 2);
				{
				setState(124);
				variableTuple();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableTupleContext extends ParserRuleContext {
		public TypeIdentifierContext typeIdentifier() {
			return getRuleContext(TypeIdentifierContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(XPRParser.LPAREN, 0); }
		public List<AnyExpressionContext> anyExpression() {
			return getRuleContexts(AnyExpressionContext.class);
		}
		public AnyExpressionContext anyExpression(int i) {
			return getRuleContext(AnyExpressionContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(XPRParser.RPAREN, 0); }
		public VariableTupleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableTuple; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterVariableTuple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitVariableTuple(this);
		}
	}

	public final VariableTupleContext variableTuple() throws RecognitionException {
		VariableTupleContext _localctx = new VariableTupleContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_variableTuple);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			typeIdentifier();
			setState(128);
			match(LPAREN);
			setState(129);
			anyExpression();
			setState(132); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(130);
				match(T__1);
				setState(131);
				anyExpression();
				}
				}
				setState(134); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__1 );
			setState(136);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableIdentifierContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER_LC() { return getToken(XPRParser.IDENTIFIER_LC, 0); }
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public VariableIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterVariableIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitVariableIdentifier(this);
		}
	}

	public final VariableIdentifierContext variableIdentifier() throws RecognitionException {
		VariableIdentifierContext _localctx = new VariableIdentifierContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_variableIdentifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			match(IDENTIFIER_LC);
			setState(141);
			_la = _input.LA(1);
			if (_la==POINT) {
				{
				setState(139);
				match(POINT);
				setState(140);
				number();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableWithTypeContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public VariableIdentifierContext variableIdentifier() {
			return getRuleContext(VariableIdentifierContext.class,0);
		}
		public VariableWithTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableWithType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterVariableWithType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitVariableWithType(this);
		}
	}

	public final VariableWithTypeContext variableWithType() throws RecognitionException {
		VariableWithTypeContext _localctx = new VariableWithTypeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_variableWithType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			type();
			setState(144);
			variableIdentifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableWithTypeListContext extends ParserRuleContext {
		public List<VariableWithTypeContext> variableWithType() {
			return getRuleContexts(VariableWithTypeContext.class);
		}
		public VariableWithTypeContext variableWithType(int i) {
			return getRuleContext(VariableWithTypeContext.class,i);
		}
		public VariableWithTypeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableWithTypeList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterVariableWithTypeList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitVariableWithTypeList(this);
		}
	}

	public final VariableWithTypeListContext variableWithTypeList() throws RecognitionException {
		VariableWithTypeListContext _localctx = new VariableWithTypeListContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_variableWithTypeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			variableWithType();
			setState(151);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(147);
				match(T__1);
				setState(148);
				variableWithType();
				}
				}
				setState(153);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnyExpressionContext extends ParserRuleContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public ArithExpressionContext arithExpression() {
			return getRuleContext(ArithExpressionContext.class,0);
		}
		public SetExpressionContext setExpression() {
			return getRuleContext(SetExpressionContext.class,0);
		}
		public BoolExpressionContext boolExpression() {
			return getRuleContext(BoolExpressionContext.class,0);
		}
		public AnyExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anyExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterAnyExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitAnyExpression(this);
		}
	}

	public final AnyExpressionContext anyExpression() throws RecognitionException {
		AnyExpressionContext _localctx = new AnyExpressionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_anyExpression);
		try {
			setState(158);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(154);
				variable();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(155);
				arithExpression();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(156);
				setExpression();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(157);
				boolExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnyExpressionListContext extends ParserRuleContext {
		public List<AnyExpressionContext> anyExpression() {
			return getRuleContexts(AnyExpressionContext.class);
		}
		public AnyExpressionContext anyExpression(int i) {
			return getRuleContext(AnyExpressionContext.class,i);
		}
		public AnyExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anyExpressionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterAnyExpressionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitAnyExpressionList(this);
		}
	}

	public final AnyExpressionListContext anyExpressionList() throws RecognitionException {
		AnyExpressionListContext _localctx = new AnyExpressionListContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_anyExpressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			anyExpression();
			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(161);
				match(T__1);
				setState(162);
				anyExpression();
				}
				}
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SumopContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(XPRParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(XPRParser.MINUS, 0); }
		public SumopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sumop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterSumop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitSumop(this);
		}
	}

	public final SumopContext sumop() throws RecognitionException {
		SumopContext _localctx = new SumopContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_sumop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			_la = _input.LA(1);
			if ( !(_la==PLUS || _la==MINUS) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MulopContext extends ParserRuleContext {
		public TerminalNode TIMES() { return getToken(XPRParser.TIMES, 0); }
		public TerminalNode DIV() { return getToken(XPRParser.DIV, 0); }
		public MulopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mulop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterMulop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitMulop(this);
		}
	}

	public final MulopContext mulop() throws RecognitionException {
		MulopContext _localctx = new MulopContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_mulop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			_la = _input.LA(1);
			if ( !(_la==TIMES || _la==DIV) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithExpressionContext extends ParserRuleContext {
		public ArithSumContext arithSum() {
			return getRuleContext(ArithSumContext.class,0);
		}
		public ArithExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterArithExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitArithExpression(this);
		}
	}

	public final ArithExpressionContext arithExpression() throws RecognitionException {
		ArithExpressionContext _localctx = new ArithExpressionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_arithExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			arithSum();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithExpressionListContext extends ParserRuleContext {
		public List<ArithExpressionContext> arithExpression() {
			return getRuleContexts(ArithExpressionContext.class);
		}
		public ArithExpressionContext arithExpression(int i) {
			return getRuleContext(ArithExpressionContext.class,i);
		}
		public ArithExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithExpressionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterArithExpressionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitArithExpressionList(this);
		}
	}

	public final ArithExpressionListContext arithExpressionList() throws RecognitionException {
		ArithExpressionListContext _localctx = new ArithExpressionListContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_arithExpressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			arithExpression();
			setState(179);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(175);
				match(T__1);
				setState(176);
				arithExpression();
				}
				}
				setState(181);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithSumContext extends ParserRuleContext {
		public List<ArithMulContext> arithMul() {
			return getRuleContexts(ArithMulContext.class);
		}
		public ArithMulContext arithMul(int i) {
			return getRuleContext(ArithMulContext.class,i);
		}
		public List<SumopContext> sumop() {
			return getRuleContexts(SumopContext.class);
		}
		public SumopContext sumop(int i) {
			return getRuleContext(SumopContext.class,i);
		}
		public ArithSumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithSum; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterArithSum(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitArithSum(this);
		}
	}

	public final ArithSumContext arithSum() throws RecognitionException {
		ArithSumContext _localctx = new ArithSumContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_arithSum);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			arithMul();
			setState(188);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(183);
					sumop();
					setState(184);
					arithMul();
					}
					} 
				}
				setState(190);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithMulContext extends ParserRuleContext {
		public List<ArithPowContext> arithPow() {
			return getRuleContexts(ArithPowContext.class);
		}
		public ArithPowContext arithPow(int i) {
			return getRuleContext(ArithPowContext.class,i);
		}
		public List<MulopContext> mulop() {
			return getRuleContexts(MulopContext.class);
		}
		public MulopContext mulop(int i) {
			return getRuleContext(MulopContext.class,i);
		}
		public ArithMulContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithMul; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterArithMul(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitArithMul(this);
		}
	}

	public final ArithMulContext arithMul() throws RecognitionException {
		ArithMulContext _localctx = new ArithMulContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_arithMul);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			arithPow();
			setState(197);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(192);
					mulop();
					setState(193);
					arithPow();
					}
					} 
				}
				setState(199);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithPowContext extends ParserRuleContext {
		public ArithAtomContext arithAtom() {
			return getRuleContext(ArithAtomContext.class,0);
		}
		public TerminalNode POW() { return getToken(XPRParser.POW, 0); }
		public ArithExpressionContext arithExpression() {
			return getRuleContext(ArithExpressionContext.class,0);
		}
		public ArithPowContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithPow; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterArithPow(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitArithPow(this);
		}
	}

	public final ArithPowContext arithPow() throws RecognitionException {
		ArithPowContext _localctx = new ArithPowContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_arithPow);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			arithAtom();
			setState(203);
			_la = _input.LA(1);
			if (_la==POW) {
				{
				setState(201);
				match(POW);
				setState(202);
				arithExpression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithAtomContext extends ParserRuleContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(XPRParser.LPAREN, 0); }
		public ArithExpressionContext arithExpression() {
			return getRuleContext(ArithExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(XPRParser.RPAREN, 0); }
		public ArithAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithAtom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterArithAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitArithAtom(this);
		}
	}

	public final ArithAtomContext arithAtom() throws RecognitionException {
		ArithAtomContext _localctx = new ArithAtomContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_arithAtom);
		try {
			setState(211);
			switch (_input.LA(1)) {
			case IDENTIFIER_UC:
			case IDENTIFIER_LC:
				enterOuterAlt(_localctx, 1);
				{
				setState(205);
				variable();
				}
				break;
			case T__0:
			case MINUS:
			case NUM_DECIMAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(206);
				number();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 3);
				{
				setState(207);
				match(LPAREN);
				setState(208);
				arithExpression();
				setState(209);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetExpressionContext extends ParserRuleContext {
		public SetSumContext setSum() {
			return getRuleContext(SetSumContext.class,0);
		}
		public SetExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterSetExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitSetExpression(this);
		}
	}

	public final SetExpressionContext setExpression() throws RecognitionException {
		SetExpressionContext _localctx = new SetExpressionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_setExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			setSum();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetSumContext extends ParserRuleContext {
		public List<SetMulContext> setMul() {
			return getRuleContexts(SetMulContext.class);
		}
		public SetMulContext setMul(int i) {
			return getRuleContext(SetMulContext.class,i);
		}
		public List<SetSumOpContext> setSumOp() {
			return getRuleContexts(SetSumOpContext.class);
		}
		public SetSumOpContext setSumOp(int i) {
			return getRuleContext(SetSumOpContext.class,i);
		}
		public SetSumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setSum; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterSetSum(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitSetSum(this);
		}
	}

	public final SetSumContext setSum() throws RecognitionException {
		SetSumContext _localctx = new SetSumContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_setSum);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			setMul();
			setState(221);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SET_UNION || _la==SET_DIFF) {
				{
				{
				setState(216);
				setSumOp();
				setState(217);
				setMul();
				}
				}
				setState(223);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetMulContext extends ParserRuleContext {
		public List<SetAtomContext> setAtom() {
			return getRuleContexts(SetAtomContext.class);
		}
		public SetAtomContext setAtom(int i) {
			return getRuleContext(SetAtomContext.class,i);
		}
		public List<TerminalNode> SET_INTERSECT() { return getTokens(XPRParser.SET_INTERSECT); }
		public TerminalNode SET_INTERSECT(int i) {
			return getToken(XPRParser.SET_INTERSECT, i);
		}
		public SetMulContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setMul; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterSetMul(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitSetMul(this);
		}
	}

	public final SetMulContext setMul() throws RecognitionException {
		SetMulContext _localctx = new SetMulContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_setMul);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
			setAtom();
			setState(229);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SET_INTERSECT) {
				{
				{
				setState(225);
				match(SET_INTERSECT);
				setState(226);
				setAtom();
				}
				}
				setState(231);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetSumOpContext extends ParserRuleContext {
		public TerminalNode SET_UNION() { return getToken(XPRParser.SET_UNION, 0); }
		public TerminalNode SET_DIFF() { return getToken(XPRParser.SET_DIFF, 0); }
		public SetSumOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setSumOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterSetSumOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitSetSumOp(this);
		}
	}

	public final SetSumOpContext setSumOp() throws RecognitionException {
		SetSumOpContext _localctx = new SetSumOpContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_setSumOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			_la = _input.LA(1);
			if ( !(_la==SET_UNION || _la==SET_DIFF) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetAtomContext extends ParserRuleContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public InlineSetContext inlineSet() {
			return getRuleContext(InlineSetContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(XPRParser.LPAREN, 0); }
		public SetExpressionContext setExpression() {
			return getRuleContext(SetExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(XPRParser.RPAREN, 0); }
		public SetAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setAtom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterSetAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitSetAtom(this);
		}
	}

	public final SetAtomContext setAtom() throws RecognitionException {
		SetAtomContext _localctx = new SetAtomContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_setAtom);
		try {
			setState(240);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(234);
				variable();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(235);
				inlineSet();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(236);
				match(LPAREN);
				setState(237);
				setExpression();
				setState(238);
				match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InlineSetContext extends ParserRuleContext {
		public InlineSetEmptyContext inlineSetEmpty() {
			return getRuleContext(InlineSetEmptyContext.class,0);
		}
		public InlineSetValuesContext inlineSetValues() {
			return getRuleContext(InlineSetValuesContext.class,0);
		}
		public InlineSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineSet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterInlineSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitInlineSet(this);
		}
	}

	public final InlineSetContext inlineSet() throws RecognitionException {
		InlineSetContext _localctx = new InlineSetContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_inlineSet);
		try {
			setState(244);
			switch (_input.LA(1)) {
			case IDENTIFIER_UC:
				enterOuterAlt(_localctx, 1);
				{
				setState(242);
				inlineSetEmpty();
				}
				break;
			case LBRACKET:
				enterOuterAlt(_localctx, 2);
				{
				setState(243);
				inlineSetValues();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InlineSetValuesContext extends ParserRuleContext {
		public TerminalNode LBRACKET() { return getToken(XPRParser.LBRACKET, 0); }
		public AnyExpressionListContext anyExpressionList() {
			return getRuleContext(AnyExpressionListContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(XPRParser.RBRACKET, 0); }
		public InlineSetValuesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineSetValues; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterInlineSetValues(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitInlineSetValues(this);
		}
	}

	public final InlineSetValuesContext inlineSetValues() throws RecognitionException {
		InlineSetValuesContext _localctx = new InlineSetValuesContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_inlineSetValues);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			match(LBRACKET);
			setState(247);
			anyExpressionList();
			setState(248);
			match(RBRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InlineSetEmptyContext extends ParserRuleContext {
		public TypeIdentifierContext typeIdentifier() {
			return getRuleContext(TypeIdentifierContext.class,0);
		}
		public TerminalNode LBRACKET() { return getToken(XPRParser.LBRACKET, 0); }
		public TerminalNode RBRACKET() { return getToken(XPRParser.RBRACKET, 0); }
		public InlineSetEmptyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineSetEmpty; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterInlineSetEmpty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitInlineSetEmpty(this);
		}
	}

	public final InlineSetEmptyContext inlineSetEmpty() throws RecognitionException {
		InlineSetEmptyContext _localctx = new InlineSetEmptyContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_inlineSetEmpty);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250);
			typeIdentifier();
			setState(251);
			match(LBRACKET);
			setState(252);
			match(RBRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmpopContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(XPRParser.LT, 0); }
		public TerminalNode LE() { return getToken(XPRParser.LE, 0); }
		public TerminalNode GT() { return getToken(XPRParser.GT, 0); }
		public TerminalNode GE() { return getToken(XPRParser.GE, 0); }
		public CmpopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmpop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterCmpop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitCmpop(this);
		}
	}

	public final CmpopContext cmpop() throws RecognitionException {
		CmpopContext _localctx = new CmpopContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_cmpop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GT) | (1L << GE) | (1L << LE) | (1L << LT))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmpStatementContext extends ParserRuleContext {
		public List<ArithExpressionContext> arithExpression() {
			return getRuleContexts(ArithExpressionContext.class);
		}
		public ArithExpressionContext arithExpression(int i) {
			return getRuleContext(ArithExpressionContext.class,i);
		}
		public CmpopContext cmpop() {
			return getRuleContext(CmpopContext.class,0);
		}
		public CmpStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmpStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterCmpStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitCmpStatement(this);
		}
	}

	public final CmpStatementContext cmpStatement() throws RecognitionException {
		CmpStatementContext _localctx = new CmpStatementContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_cmpStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			arithExpression();
			setState(257);
			cmpop();
			setState(258);
			arithExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqopContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(XPRParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(XPRParser.NEQ, 0); }
		public EqopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eqop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterEqop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitEqop(this);
		}
	}

	public final EqopContext eqop() throws RecognitionException {
		EqopContext _localctx = new EqopContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_eqop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(260);
			_la = _input.LA(1);
			if ( !(_la==EQ || _la==NEQ) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqStatementContext extends ParserRuleContext {
		public List<AnyExpressionContext> anyExpression() {
			return getRuleContexts(AnyExpressionContext.class);
		}
		public AnyExpressionContext anyExpression(int i) {
			return getRuleContext(AnyExpressionContext.class,i);
		}
		public EqopContext eqop() {
			return getRuleContext(EqopContext.class,0);
		}
		public EqStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eqStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterEqStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitEqStatement(this);
		}
	}

	public final EqStatementContext eqStatement() throws RecognitionException {
		EqStatementContext _localctx = new EqStatementContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_eqStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(262);
			anyExpression();
			setState(263);
			eqop();
			setState(264);
			anyExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetopContext extends ParserRuleContext {
		public TerminalNode SET_IN() { return getToken(XPRParser.SET_IN, 0); }
		public TerminalNode SET_NOTIN() { return getToken(XPRParser.SET_NOTIN, 0); }
		public SetopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterSetop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitSetop(this);
		}
	}

	public final SetopContext setop() throws RecognitionException {
		SetopContext _localctx = new SetopContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_setop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(266);
			_la = _input.LA(1);
			if ( !(_la==SET_IN || _la==SET_NOTIN) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetStatementContext extends ParserRuleContext {
		public AnyExpressionContext anyExpression() {
			return getRuleContext(AnyExpressionContext.class,0);
		}
		public SetopContext setop() {
			return getRuleContext(SetopContext.class,0);
		}
		public SetExpressionContext setExpression() {
			return getRuleContext(SetExpressionContext.class,0);
		}
		public SetStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterSetStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitSetStatement(this);
		}
	}

	public final SetStatementContext setStatement() throws RecognitionException {
		SetStatementContext _localctx = new SetStatementContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_setStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(268);
			anyExpression();
			setState(269);
			setop();
			setState(270);
			setExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubsetExpressionContext extends ParserRuleContext {
		public List<SetExpressionContext> setExpression() {
			return getRuleContexts(SetExpressionContext.class);
		}
		public SetExpressionContext setExpression(int i) {
			return getRuleContext(SetExpressionContext.class,i);
		}
		public TerminalNode SET_SUB() { return getToken(XPRParser.SET_SUB, 0); }
		public SubsetExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subsetExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterSubsetExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitSubsetExpression(this);
		}
	}

	public final SubsetExpressionContext subsetExpression() throws RecognitionException {
		SubsetExpressionContext _localctx = new SubsetExpressionContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_subsetExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(272);
			setExpression();
			setState(273);
			match(SET_SUB);
			setState(274);
			setExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoolExpressionContext extends ParserRuleContext {
		public TerminalNode KWD_TRUE() { return getToken(XPRParser.KWD_TRUE, 0); }
		public TerminalNode KWD_FALSE() { return getToken(XPRParser.KWD_FALSE, 0); }
		public TerminalNode LPAREN() { return getToken(XPRParser.LPAREN, 0); }
		public QuantifiedStatementContext quantifiedStatement() {
			return getRuleContext(QuantifiedStatementContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(XPRParser.RPAREN, 0); }
		public BoolExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterBoolExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitBoolExpression(this);
		}
	}

	public final BoolExpressionContext boolExpression() throws RecognitionException {
		BoolExpressionContext _localctx = new BoolExpressionContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_boolExpression);
		try {
			setState(282);
			switch (_input.LA(1)) {
			case KWD_TRUE:
				enterOuterAlt(_localctx, 1);
				{
				setState(276);
				match(KWD_TRUE);
				}
				break;
			case KWD_FALSE:
				enterOuterAlt(_localctx, 2);
				{
				setState(277);
				match(KWD_FALSE);
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 3);
				{
				setState(278);
				match(LPAREN);
				setState(279);
				quantifiedStatement();
				setState(280);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogopContext extends ParserRuleContext {
		public TerminalNode IMPLIES() { return getToken(XPRParser.IMPLIES, 0); }
		public TerminalNode IFF() { return getToken(XPRParser.IFF, 0); }
		public TerminalNode XOR() { return getToken(XPRParser.XOR, 0); }
		public LogopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterLogop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitLogop(this);
		}
	}

	public final LogopContext logop() throws RecognitionException {
		LogopContext _localctx = new LogopContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_logop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IMPLIES) | (1L << IFF) | (1L << XOR))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuantifiedStatementContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public VariableWithTypeListContext variableWithTypeList() {
			return getRuleContext(VariableWithTypeListContext.class,0);
		}
		public TerminalNode FORALL() { return getToken(XPRParser.FORALL, 0); }
		public TerminalNode EXISTS() { return getToken(XPRParser.EXISTS, 0); }
		public QuantifiedStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quantifiedStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterQuantifiedStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitQuantifiedStatement(this);
		}
	}

	public final QuantifiedStatementContext quantifiedStatement() throws RecognitionException {
		QuantifiedStatementContext _localctx = new QuantifiedStatementContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_quantifiedStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(290);
			_la = _input.LA(1);
			if (_la==EXISTS || _la==FORALL) {
				{
				setState(286);
				_la = _input.LA(1);
				if ( !(_la==EXISTS || _la==FORALL) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(287);
				variableWithTypeList();
				setState(288);
				match(T__2);
				}
			}

			setState(292);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public List<DisjunctionContext> disjunction() {
			return getRuleContexts(DisjunctionContext.class);
		}
		public DisjunctionContext disjunction(int i) {
			return getRuleContext(DisjunctionContext.class,i);
		}
		public LogopContext logop() {
			return getRuleContext(LogopContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(294);
			disjunction();
			setState(298);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IMPLIES) | (1L << IFF) | (1L << XOR))) != 0)) {
				{
				setState(295);
				logop();
				setState(296);
				disjunction();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DisjunctionContext extends ParserRuleContext {
		public List<ConjunctionContext> conjunction() {
			return getRuleContexts(ConjunctionContext.class);
		}
		public ConjunctionContext conjunction(int i) {
			return getRuleContext(ConjunctionContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(XPRParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(XPRParser.OR, i);
		}
		public DisjunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_disjunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterDisjunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitDisjunction(this);
		}
	}

	public final DisjunctionContext disjunction() throws RecognitionException {
		DisjunctionContext _localctx = new DisjunctionContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_disjunction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(300);
			conjunction();
			setState(305);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(301);
				match(OR);
				setState(302);
				conjunction();
				}
				}
				setState(307);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConjunctionContext extends ParserRuleContext {
		public List<StatementAtomContext> statementAtom() {
			return getRuleContexts(StatementAtomContext.class);
		}
		public StatementAtomContext statementAtom(int i) {
			return getRuleContext(StatementAtomContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(XPRParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(XPRParser.AND, i);
		}
		public ConjunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conjunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterConjunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitConjunction(this);
		}
	}

	public final ConjunctionContext conjunction() throws RecognitionException {
		ConjunctionContext _localctx = new ConjunctionContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_conjunction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(308);
			statementAtom();
			setState(313);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(309);
				match(AND);
				setState(310);
				statementAtom();
				}
				}
				setState(315);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementAtomContext extends ParserRuleContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public SetStatementContext setStatement() {
			return getRuleContext(SetStatementContext.class,0);
		}
		public SubsetExpressionContext subsetExpression() {
			return getRuleContext(SubsetExpressionContext.class,0);
		}
		public CmpStatementContext cmpStatement() {
			return getRuleContext(CmpStatementContext.class,0);
		}
		public EqStatementContext eqStatement() {
			return getRuleContext(EqStatementContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(XPRParser.LPAREN, 0); }
		public QuantifiedStatementContext quantifiedStatement() {
			return getRuleContext(QuantifiedStatementContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(XPRParser.RPAREN, 0); }
		public BoolExpressionContext boolExpression() {
			return getRuleContext(BoolExpressionContext.class,0);
		}
		public TerminalNode NOT() { return getToken(XPRParser.NOT, 0); }
		public StatementAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementAtom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterStatementAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitStatementAtom(this);
		}
	}

	public final StatementAtomContext statementAtom() throws RecognitionException {
		StatementAtomContext _localctx = new StatementAtomContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_statementAtom);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(317);
			_la = _input.LA(1);
			if (_la==NOT) {
				{
				setState(316);
				match(NOT);
				}
			}

			setState(329);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(319);
				variable();
				}
				break;
			case 2:
				{
				setState(320);
				setStatement();
				}
				break;
			case 3:
				{
				setState(321);
				subsetExpression();
				}
				break;
			case 4:
				{
				setState(322);
				cmpStatement();
				}
				break;
			case 5:
				{
				setState(323);
				eqStatement();
				}
				break;
			case 6:
				{
				setState(324);
				match(LPAREN);
				setState(325);
				quantifiedStatement();
				setState(326);
				match(RPAREN);
				}
				break;
			case 7:
				{
				setState(328);
				boolExpression();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeDeclarationContext extends ParserRuleContext {
		public TerminalNode KWD_TYPE() { return getToken(XPRParser.KWD_TYPE, 0); }
		public TypeIdentifierContext typeIdentifier() {
			return getRuleContext(TypeIdentifierContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterTypeDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitTypeDeclaration(this);
		}
	}

	public final TypeDeclarationContext typeDeclaration() throws RecognitionException {
		TypeDeclarationContext _localctx = new TypeDeclarationContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_typeDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(331);
			match(KWD_TYPE);
			setState(332);
			typeIdentifier();
			setState(334);
			_la = _input.LA(1);
			if (_la==LPAREN || _la==IDENTIFIER_UC) {
				{
				setState(333);
				type();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FactDeclarationContext extends ParserRuleContext {
		public QuantifiedStatementContext quantifiedStatement() {
			return getRuleContext(QuantifiedStatementContext.class,0);
		}
		public FactDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterFactDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitFactDeclaration(this);
		}
	}

	public final FactDeclarationContext factDeclaration() throws RecognitionException {
		FactDeclarationContext _localctx = new FactDeclarationContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_factDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(336);
			quantifiedStatement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationContext extends ParserRuleContext {
		public VariableWithTypeContext variableWithType() {
			return getRuleContext(VariableWithTypeContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(XPRParser.ASSIGN, 0); }
		public AnyExpressionContext anyExpression() {
			return getRuleContext(AnyExpressionContext.class,0);
		}
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitVariableDeclaration(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_variableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(338);
			variableWithType();
			setState(341);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(339);
				match(ASSIGN);
				setState(340);
				anyExpression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableAssignmentContext extends ParserRuleContext {
		public VariableIdentifierContext variableIdentifier() {
			return getRuleContext(VariableIdentifierContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(XPRParser.ASSIGN, 0); }
		public AnyExpressionContext anyExpression() {
			return getRuleContext(AnyExpressionContext.class,0);
		}
		public VariableAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableAssignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterVariableAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitVariableAssignment(this);
		}
	}

	public final VariableAssignmentContext variableAssignment() throws RecognitionException {
		VariableAssignmentContext _localctx = new VariableAssignmentContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_variableAssignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(343);
			variableIdentifier();
			setState(344);
			match(ASSIGN);
			setState(345);
			anyExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DocumentContext extends ParserRuleContext {
		public List<LineContext> line() {
			return getRuleContexts(LineContext.class);
		}
		public LineContext line(int i) {
			return getRuleContext(LineContext.class,i);
		}
		public DocumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_document; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterDocument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitDocument(this);
		}
	}

	public final DocumentContext document() throws RecognitionException {
		DocumentContext _localctx = new DocumentContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_document);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(355);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << LPAREN) | (1L << LBRACKET) | (1L << MINUS) | (1L << NOT) | (1L << EXISTS) | (1L << FORALL) | (1L << KWD_TYPE) | (1L << KWD_TRUE) | (1L << KWD_FALSE) | (1L << NUM_DECIMAL) | (1L << IDENTIFIER_UC) | (1L << IDENTIFIER_LC))) != 0)) {
				{
				{
				setState(347);
				line();
				setState(349); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(348);
					match(SEMICOLON);
					}
					}
					setState(351); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SEMICOLON );
				}
				}
				setState(357);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LineContext extends ParserRuleContext {
		public TypeDeclarationContext typeDeclaration() {
			return getRuleContext(TypeDeclarationContext.class,0);
		}
		public FactDeclarationContext factDeclaration() {
			return getRuleContext(FactDeclarationContext.class,0);
		}
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public VariableAssignmentContext variableAssignment() {
			return getRuleContext(VariableAssignmentContext.class,0);
		}
		public LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).enterLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XPRListener ) ((XPRListener)listener).exitLine(this);
		}
	}

	public final LineContext line() throws RecognitionException {
		LineContext _localctx = new LineContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_line);
		try {
			setState(362);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(358);
				typeDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(359);
				factDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(360);
				variableDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(361);
				variableAssignment();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\61\u016f\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\3\2\5\2d\n\2\3\2\3\2\3\3\3"+
		"\3\3\3\5\3k\n\3\3\4\3\4\3\5\3\5\3\5\3\5\7\5s\n\5\f\5\16\5v\13\5\3\5\3"+
		"\5\3\6\3\6\3\6\3\6\3\7\3\7\5\7\u0080\n\7\3\b\3\b\3\b\3\b\3\b\6\b\u0087"+
		"\n\b\r\b\16\b\u0088\3\b\3\b\3\t\3\t\3\t\5\t\u0090\n\t\3\n\3\n\3\n\3\13"+
		"\3\13\3\13\7\13\u0098\n\13\f\13\16\13\u009b\13\13\3\f\3\f\3\f\3\f\5\f"+
		"\u00a1\n\f\3\r\3\r\3\r\7\r\u00a6\n\r\f\r\16\r\u00a9\13\r\3\16\3\16\3\17"+
		"\3\17\3\20\3\20\3\21\3\21\3\21\7\21\u00b4\n\21\f\21\16\21\u00b7\13\21"+
		"\3\22\3\22\3\22\3\22\7\22\u00bd\n\22\f\22\16\22\u00c0\13\22\3\23\3\23"+
		"\3\23\3\23\7\23\u00c6\n\23\f\23\16\23\u00c9\13\23\3\24\3\24\3\24\5\24"+
		"\u00ce\n\24\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u00d6\n\25\3\26\3\26\3"+
		"\27\3\27\3\27\3\27\7\27\u00de\n\27\f\27\16\27\u00e1\13\27\3\30\3\30\3"+
		"\30\7\30\u00e6\n\30\f\30\16\30\u00e9\13\30\3\31\3\31\3\32\3\32\3\32\3"+
		"\32\3\32\3\32\5\32\u00f3\n\32\3\33\3\33\5\33\u00f7\n\33\3\34\3\34\3\34"+
		"\3\34\3\35\3\35\3\35\3\35\3\36\3\36\3\37\3\37\3\37\3\37\3 \3 \3!\3!\3"+
		"!\3!\3\"\3\"\3#\3#\3#\3#\3$\3$\3$\3$\3%\3%\3%\3%\3%\3%\5%\u011d\n%\3&"+
		"\3&\3\'\3\'\3\'\3\'\5\'\u0125\n\'\3\'\3\'\3(\3(\3(\3(\5(\u012d\n(\3)\3"+
		")\3)\7)\u0132\n)\f)\16)\u0135\13)\3*\3*\3*\7*\u013a\n*\f*\16*\u013d\13"+
		"*\3+\5+\u0140\n+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\5+\u014c\n+\3,\3,\3,\5"+
		",\u0151\n,\3-\3-\3.\3.\3.\5.\u0158\n.\3/\3/\3/\3/\3\60\3\60\6\60\u0160"+
		"\n\60\r\60\16\60\u0161\7\60\u0164\n\60\f\60\16\60\u0167\13\60\3\61\3\61"+
		"\3\61\3\61\5\61\u016d\n\61\3\61\2\2\62\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`\2\13\4\2\3\3++\3\2\n"+
		"\13\3\2\f\r\4\2  \"\"\3\2\16\21\3\2\22\23\3\2\36\37\3\2\31\33\3\2$%\u0169"+
		"\2c\3\2\2\2\4j\3\2\2\2\6l\3\2\2\2\bn\3\2\2\2\ny\3\2\2\2\f\177\3\2\2\2"+
		"\16\u0081\3\2\2\2\20\u008c\3\2\2\2\22\u0091\3\2\2\2\24\u0094\3\2\2\2\26"+
		"\u00a0\3\2\2\2\30\u00a2\3\2\2\2\32\u00aa\3\2\2\2\34\u00ac\3\2\2\2\36\u00ae"+
		"\3\2\2\2 \u00b0\3\2\2\2\"\u00b8\3\2\2\2$\u00c1\3\2\2\2&\u00ca\3\2\2\2"+
		"(\u00d5\3\2\2\2*\u00d7\3\2\2\2,\u00d9\3\2\2\2.\u00e2\3\2\2\2\60\u00ea"+
		"\3\2\2\2\62\u00f2\3\2\2\2\64\u00f6\3\2\2\2\66\u00f8\3\2\2\28\u00fc\3\2"+
		"\2\2:\u0100\3\2\2\2<\u0102\3\2\2\2>\u0106\3\2\2\2@\u0108\3\2\2\2B\u010c"+
		"\3\2\2\2D\u010e\3\2\2\2F\u0112\3\2\2\2H\u011c\3\2\2\2J\u011e\3\2\2\2L"+
		"\u0124\3\2\2\2N\u0128\3\2\2\2P\u012e\3\2\2\2R\u0136\3\2\2\2T\u013f\3\2"+
		"\2\2V\u014d\3\2\2\2X\u0152\3\2\2\2Z\u0154\3\2\2\2\\\u0159\3\2\2\2^\u0165"+
		"\3\2\2\2`\u016c\3\2\2\2bd\7\13\2\2cb\3\2\2\2cd\3\2\2\2de\3\2\2\2ef\t\2"+
		"\2\2f\3\3\2\2\2gk\5\6\4\2hk\5\b\5\2ik\5\n\6\2jg\3\2\2\2jh\3\2\2\2ji\3"+
		"\2\2\2k\5\3\2\2\2lm\7,\2\2m\7\3\2\2\2no\7\6\2\2ot\5\4\3\2pq\7\4\2\2qs"+
		"\5\4\3\2rp\3\2\2\2sv\3\2\2\2tr\3\2\2\2tu\3\2\2\2uw\3\2\2\2vt\3\2\2\2w"+
		"x\7\7\2\2x\t\3\2\2\2yz\5\6\4\2z{\7\b\2\2{|\7\t\2\2|\13\3\2\2\2}\u0080"+
		"\5\20\t\2~\u0080\5\16\b\2\177}\3\2\2\2\177~\3\2\2\2\u0080\r\3\2\2\2\u0081"+
		"\u0082\5\6\4\2\u0082\u0083\7\6\2\2\u0083\u0086\5\26\f\2\u0084\u0085\7"+
		"\4\2\2\u0085\u0087\5\26\f\2\u0086\u0084\3\2\2\2\u0087\u0088\3\2\2\2\u0088"+
		"\u0086\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u008b\7\7"+
		"\2\2\u008b\17\3\2\2\2\u008c\u008f\7-\2\2\u008d\u008e\7\25\2\2\u008e\u0090"+
		"\5\2\2\2\u008f\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090\21\3\2\2\2\u0091"+
		"\u0092\5\4\3\2\u0092\u0093\5\20\t\2\u0093\23\3\2\2\2\u0094\u0099\5\22"+
		"\n\2\u0095\u0096\7\4\2\2\u0096\u0098\5\22\n\2\u0097\u0095\3\2\2\2\u0098"+
		"\u009b\3\2\2\2\u0099\u0097\3\2\2\2\u0099\u009a\3\2\2\2\u009a\25\3\2\2"+
		"\2\u009b\u0099\3\2\2\2\u009c\u00a1\5\f\7\2\u009d\u00a1\5\36\20\2\u009e"+
		"\u00a1\5*\26\2\u009f\u00a1\5H%\2\u00a0\u009c\3\2\2\2\u00a0\u009d\3\2\2"+
		"\2\u00a0\u009e\3\2\2\2\u00a0\u009f\3\2\2\2\u00a1\27\3\2\2\2\u00a2\u00a7"+
		"\5\26\f\2\u00a3\u00a4\7\4\2\2\u00a4\u00a6\5\26\f\2\u00a5\u00a3\3\2\2\2"+
		"\u00a6\u00a9\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\31"+
		"\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa\u00ab\t\3\2\2\u00ab\33\3\2\2\2\u00ac"+
		"\u00ad\t\4\2\2\u00ad\35\3\2\2\2\u00ae\u00af\5\"\22\2\u00af\37\3\2\2\2"+
		"\u00b0\u00b5\5\36\20\2\u00b1\u00b2\7\4\2\2\u00b2\u00b4\5\36\20\2\u00b3"+
		"\u00b1\3\2\2\2\u00b4\u00b7\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b5\u00b6\3\2"+
		"\2\2\u00b6!\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b8\u00be\5$\23\2\u00b9\u00ba"+
		"\5\32\16\2\u00ba\u00bb\5$\23\2\u00bb\u00bd\3\2\2\2\u00bc\u00b9\3\2\2\2"+
		"\u00bd\u00c0\3\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf#\3"+
		"\2\2\2\u00c0\u00be\3\2\2\2\u00c1\u00c7\5&\24\2\u00c2\u00c3\5\34\17\2\u00c3"+
		"\u00c4\5&\24\2\u00c4\u00c6\3\2\2\2\u00c5\u00c2\3\2\2\2\u00c6\u00c9\3\2"+
		"\2\2\u00c7\u00c5\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8%\3\2\2\2\u00c9\u00c7"+
		"\3\2\2\2\u00ca\u00cd\5(\25\2\u00cb\u00cc\7\26\2\2\u00cc\u00ce\5\36\20"+
		"\2\u00cd\u00cb\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\'\3\2\2\2\u00cf\u00d6"+
		"\5\f\7\2\u00d0\u00d6\5\2\2\2\u00d1\u00d2\7\6\2\2\u00d2\u00d3\5\36\20\2"+
		"\u00d3\u00d4\7\7\2\2\u00d4\u00d6\3\2\2\2\u00d5\u00cf\3\2\2\2\u00d5\u00d0"+
		"\3\2\2\2\u00d5\u00d1\3\2\2\2\u00d6)\3\2\2\2\u00d7\u00d8\5,\27\2\u00d8"+
		"+\3\2\2\2\u00d9\u00df\5.\30\2\u00da\u00db\5\60\31\2\u00db\u00dc\5.\30"+
		"\2\u00dc\u00de\3\2\2\2\u00dd\u00da\3\2\2\2\u00de\u00e1\3\2\2\2\u00df\u00dd"+
		"\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0-\3\2\2\2\u00e1\u00df\3\2\2\2\u00e2"+
		"\u00e7\5\62\32\2\u00e3\u00e4\7!\2\2\u00e4\u00e6\5\62\32\2\u00e5\u00e3"+
		"\3\2\2\2\u00e6\u00e9\3\2\2\2\u00e7\u00e5\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8"+
		"/\3\2\2\2\u00e9\u00e7\3\2\2\2\u00ea\u00eb\t\5\2\2\u00eb\61\3\2\2\2\u00ec"+
		"\u00f3\5\f\7\2\u00ed\u00f3\5\64\33\2\u00ee\u00ef\7\6\2\2\u00ef\u00f0\5"+
		"*\26\2\u00f0\u00f1\7\7\2\2\u00f1\u00f3\3\2\2\2\u00f2\u00ec\3\2\2\2\u00f2"+
		"\u00ed\3\2\2\2\u00f2\u00ee\3\2\2\2\u00f3\63\3\2\2\2\u00f4\u00f7\58\35"+
		"\2\u00f5\u00f7\5\66\34\2\u00f6\u00f4\3\2\2\2\u00f6\u00f5\3\2\2\2\u00f7"+
		"\65\3\2\2\2\u00f8\u00f9\7\b\2\2\u00f9\u00fa\5\30\r\2\u00fa\u00fb\7\t\2"+
		"\2\u00fb\67\3\2\2\2\u00fc\u00fd\5\6\4\2\u00fd\u00fe\7\b\2\2\u00fe\u00ff"+
		"\7\t\2\2\u00ff9\3\2\2\2\u0100\u0101\t\6\2\2\u0101;\3\2\2\2\u0102\u0103"+
		"\5\36\20\2\u0103\u0104\5:\36\2\u0104\u0105\5\36\20\2\u0105=\3\2\2\2\u0106"+
		"\u0107\t\7\2\2\u0107?\3\2\2\2\u0108\u0109\5\26\f\2\u0109\u010a\5> \2\u010a"+
		"\u010b\5\26\f\2\u010bA\3\2\2\2\u010c\u010d\t\b\2\2\u010dC\3\2\2\2\u010e"+
		"\u010f\5\26\f\2\u010f\u0110\5B\"\2\u0110\u0111\5*\26\2\u0111E\3\2\2\2"+
		"\u0112\u0113\5*\26\2\u0113\u0114\7#\2\2\u0114\u0115\5*\26\2\u0115G\3\2"+
		"\2\2\u0116\u011d\7)\2\2\u0117\u011d\7*\2\2\u0118\u0119\7\6\2\2\u0119\u011a"+
		"\5L\'\2\u011a\u011b\7\7\2\2\u011b\u011d\3\2\2\2\u011c\u0116\3\2\2\2\u011c"+
		"\u0117\3\2\2\2\u011c\u0118\3\2\2\2\u011dI\3\2\2\2\u011e\u011f\t\t\2\2"+
		"\u011fK\3\2\2\2\u0120\u0121\t\n\2\2\u0121\u0122\5\24\13\2\u0122\u0123"+
		"\7\5\2\2\u0123\u0125\3\2\2\2\u0124\u0120\3\2\2\2\u0124\u0125\3\2\2\2\u0125"+
		"\u0126\3\2\2\2\u0126\u0127\5N(\2\u0127M\3\2\2\2\u0128\u012c\5P)\2\u0129"+
		"\u012a\5J&\2\u012a\u012b\5P)\2\u012b\u012d\3\2\2\2\u012c\u0129\3\2\2\2"+
		"\u012c\u012d\3\2\2\2\u012dO\3\2\2\2\u012e\u0133\5R*\2\u012f\u0130\7\27"+
		"\2\2\u0130\u0132\5R*\2\u0131\u012f\3\2\2\2\u0132\u0135\3\2\2\2\u0133\u0131"+
		"\3\2\2\2\u0133\u0134\3\2\2\2\u0134Q\3\2\2\2\u0135\u0133\3\2\2\2\u0136"+
		"\u013b\5T+\2\u0137\u0138\7\30\2\2\u0138\u013a\5T+\2\u0139\u0137\3\2\2"+
		"\2\u013a\u013d\3\2\2\2\u013b\u0139\3\2\2\2\u013b\u013c\3\2\2\2\u013cS"+
		"\3\2\2\2\u013d\u013b\3\2\2\2\u013e\u0140\7\34\2\2\u013f\u013e\3\2\2\2"+
		"\u013f\u0140\3\2\2\2\u0140\u014b\3\2\2\2\u0141\u014c\5\f\7\2\u0142\u014c"+
		"\5D#\2\u0143\u014c\5F$\2\u0144\u014c\5<\37\2\u0145\u014c\5@!\2\u0146\u0147"+
		"\7\6\2\2\u0147\u0148\5L\'\2\u0148\u0149\7\7\2\2\u0149\u014c\3\2\2\2\u014a"+
		"\u014c\5H%\2\u014b\u0141\3\2\2\2\u014b\u0142\3\2\2\2\u014b\u0143\3\2\2"+
		"\2\u014b\u0144\3\2\2\2\u014b\u0145\3\2\2\2\u014b\u0146\3\2\2\2\u014b\u014a"+
		"\3\2\2\2\u014cU\3\2\2\2\u014d\u014e\7&\2\2\u014e\u0150\5\6\4\2\u014f\u0151"+
		"\5\4\3\2\u0150\u014f\3\2\2\2\u0150\u0151\3\2\2\2\u0151W\3\2\2\2\u0152"+
		"\u0153\5L\'\2\u0153Y\3\2\2\2\u0154\u0157\5\22\n\2\u0155\u0156\7\24\2\2"+
		"\u0156\u0158\5\26\f\2\u0157\u0155\3\2\2\2\u0157\u0158\3\2\2\2\u0158[\3"+
		"\2\2\2\u0159\u015a\5\20\t\2\u015a\u015b\7\24\2\2\u015b\u015c\5\26\f\2"+
		"\u015c]\3\2\2\2\u015d\u015f\5`\61\2\u015e\u0160\7\35\2\2\u015f\u015e\3"+
		"\2\2\2\u0160\u0161\3\2\2\2\u0161\u015f\3\2\2\2\u0161\u0162\3\2\2\2\u0162"+
		"\u0164\3\2\2\2\u0163\u015d\3\2\2\2\u0164\u0167\3\2\2\2\u0165\u0163\3\2"+
		"\2\2\u0165\u0166\3\2\2\2\u0166_\3\2\2\2\u0167\u0165\3\2\2\2\u0168\u016d"+
		"\5V,\2\u0169\u016d\5X-\2\u016a\u016d\5Z.\2\u016b\u016d\5\\/\2\u016c\u0168"+
		"\3\2\2\2\u016c\u0169\3\2\2\2\u016c\u016a\3\2\2\2\u016c\u016b\3\2\2\2\u016d"+
		"a\3\2\2\2 cjt\177\u0088\u008f\u0099\u00a0\u00a7\u00b5\u00be\u00c7\u00cd"+
		"\u00d5\u00df\u00e7\u00f2\u00f6\u011c\u0124\u012c\u0133\u013b\u013f\u014b"+
		"\u0150\u0157\u0161\u0165\u016c";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}