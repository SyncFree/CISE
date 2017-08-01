/*******************************************************************************
 * ===========================================================
 * This file is part of the CISE tool software.
 *
 * The CISE tool software contains proprietary and confidential information of Inria.
 * All rights reserved. Reproduction, adaptation or distribution, in whole or in part, is
 * forbidden except by express written permission of Inria.
 * Version V1.5.1., July 2017
 * Authors: Mahsa Najafzadeh, Michał Jabczyński, Marc Shapiro
 * Copyright (C) 2017, Inria
 * ===========================================================
 ******************************************************************************/

// Generated from XPR.g4 by ANTLR 4.5.1
package z3fol.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class XPRLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "LPAREN", "RPAREN", "LBRACKET", "RBRACKET", "PLUS", 
		"MINUS", "TIMES", "DIV", "GT", "GE", "LE", "LT", "EQ", "NEQ", "ASSIGN", 
		"POINT", "POW", "OR", "AND", "IMPLIES", "IFF", "XOR", "NOT", "SEMICOLON", 
		"SET_IN", "SET_NOTIN", "SET_UNION", "SET_INTERSECT", "SET_DIFF", "SET_SUB", 
		"EXISTS", "FORALL", "KWD_TYPE", "KWD_DECLARE", "KWD_ASSERT", "KWD_TRUE", 
		"KWD_FALSE", "NUM_DECIMAL", "IDENTIFIER_UC", "IDENTIFIER_LC", "Whitespace", 
		"Newline", "BlockComment", "LineComment"
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


	public XPRLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "XPR.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\61\u011b\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3"+
		"\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16"+
		"\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\23"+
		"\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\31"+
		"\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\35"+
		"\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3#"+
		"\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3%\3%\3%\3%\3%\3&\3&\3&\3&\3&\3&\3&"+
		"\3&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3)\3*\6"+
		"*\u00e1\n*\r*\16*\u00e2\3+\3+\7+\u00e7\n+\f+\16+\u00ea\13+\3,\3,\7,\u00ee"+
		"\n,\f,\16,\u00f1\13,\3-\6-\u00f4\n-\r-\16-\u00f5\3-\3-\3.\3.\5.\u00fc"+
		"\n.\3.\5.\u00ff\n.\3.\3.\3/\3/\3/\3/\7/\u0107\n/\f/\16/\u010a\13/\3/\3"+
		"/\3/\3/\3/\3\60\3\60\3\60\3\60\7\60\u0115\n\60\f\60\16\60\u0118\13\60"+
		"\3\60\3\60\3\u0108\2\61\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f"+
		"\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63"+
		"\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61\3\2"+
		"\b\3\2\62;\4\2C\\aa\6\2\62;C\\aac|\4\2aac|\4\2\13\13\"\"\4\2\f\f\17\17"+
		"\u0122\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3"+
		"\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2"+
		"\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2"+
		"/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2"+
		"\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2"+
		"G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3"+
		"\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2"+
		"\2\3a\3\2\2\2\5c\3\2\2\2\7e\3\2\2\2\tg\3\2\2\2\13i\3\2\2\2\rk\3\2\2\2"+
		"\17m\3\2\2\2\21o\3\2\2\2\23q\3\2\2\2\25s\3\2\2\2\27u\3\2\2\2\31w\3\2\2"+
		"\2\33y\3\2\2\2\35|\3\2\2\2\37\177\3\2\2\2!\u0081\3\2\2\2#\u0084\3\2\2"+
		"\2%\u0087\3\2\2\2\'\u008a\3\2\2\2)\u008c\3\2\2\2+\u008e\3\2\2\2-\u0090"+
		"\3\2\2\2/\u0092\3\2\2\2\61\u0095\3\2\2\2\63\u0099\3\2\2\2\65\u009d\3\2"+
		"\2\2\67\u009f\3\2\2\29\u00a1\3\2\2\2;\u00a4\3\2\2\2=\u00aa\3\2\2\2?\u00ac"+
		"\3\2\2\2A\u00ae\3\2\2\2C\u00b0\3\2\2\2E\u00b2\3\2\2\2G\u00b9\3\2\2\2I"+
		"\u00c0\3\2\2\2K\u00c5\3\2\2\2M\u00cd\3\2\2\2O\u00d4\3\2\2\2Q\u00d9\3\2"+
		"\2\2S\u00e0\3\2\2\2U\u00e4\3\2\2\2W\u00eb\3\2\2\2Y\u00f3\3\2\2\2[\u00fe"+
		"\3\2\2\2]\u0102\3\2\2\2_\u0110\3\2\2\2ab\7\62\2\2b\4\3\2\2\2cd\7.\2\2"+
		"d\6\3\2\2\2ef\7<\2\2f\b\3\2\2\2gh\7*\2\2h\n\3\2\2\2ij\7+\2\2j\f\3\2\2"+
		"\2kl\7}\2\2l\16\3\2\2\2mn\7\177\2\2n\20\3\2\2\2op\7-\2\2p\22\3\2\2\2q"+
		"r\7/\2\2r\24\3\2\2\2st\7,\2\2t\26\3\2\2\2uv\7\61\2\2v\30\3\2\2\2wx\7@"+
		"\2\2x\32\3\2\2\2yz\7@\2\2z{\7?\2\2{\34\3\2\2\2|}\7>\2\2}~\7?\2\2~\36\3"+
		"\2\2\2\177\u0080\7>\2\2\u0080 \3\2\2\2\u0081\u0082\7?\2\2\u0082\u0083"+
		"\7?\2\2\u0083\"\3\2\2\2\u0084\u0085\7#\2\2\u0085\u0086\7?\2\2\u0086$\3"+
		"\2\2\2\u0087\u0088\7<\2\2\u0088\u0089\7?\2\2\u0089&\3\2\2\2\u008a\u008b"+
		"\7\60\2\2\u008b(\3\2\2\2\u008c\u008d\7`\2\2\u008d*\3\2\2\2\u008e\u008f"+
		"\7~\2\2\u008f,\3\2\2\2\u0090\u0091\7(\2\2\u0091.\3\2\2\2\u0092\u0093\7"+
		"?\2\2\u0093\u0094\7@\2\2\u0094\60\3\2\2\2\u0095\u0096\7>\2\2\u0096\u0097"+
		"\7?\2\2\u0097\u0098\7@\2\2\u0098\62\3\2\2\2\u0099\u009a\7Z\2\2\u009a\u009b"+
		"\7Q\2\2\u009b\u009c\7T\2\2\u009c\64\3\2\2\2\u009d\u009e\7\u00ae\2\2\u009e"+
		"\66\3\2\2\2\u009f\u00a0\7=\2\2\u00a08\3\2\2\2\u00a1\u00a2\7k\2\2\u00a2"+
		"\u00a3\7p\2\2\u00a3:\3\2\2\2\u00a4\u00a5\7p\2\2\u00a5\u00a6\7q\2\2\u00a6"+
		"\u00a7\7v\2\2\u00a7\u00a8\7k\2\2\u00a8\u00a9\7p\2\2\u00a9<\3\2\2\2\u00aa"+
		"\u00ab\7\u222c\2\2\u00ab>\3\2\2\2\u00ac\u00ad\7\u222b\2\2\u00ad@\3\2\2"+
		"\2\u00ae\u00af\7^\2\2\u00afB\3\2\2\2\u00b0\u00b1\7\u2284\2\2\u00b1D\3"+
		"\2\2\2\u00b2\u00b3\7G\2\2\u00b3\u00b4\7z\2\2\u00b4\u00b5\7k\2\2\u00b5"+
		"\u00b6\7u\2\2\u00b6\u00b7\7v\2\2\u00b7\u00b8\7u\2\2\u00b8F\3\2\2\2\u00b9"+
		"\u00ba\7H\2\2\u00ba\u00bb\7q\2\2\u00bb\u00bc\7t\2\2\u00bc\u00bd\7c\2\2"+
		"\u00bd\u00be\7n\2\2\u00be\u00bf\7n\2\2\u00bfH\3\2\2\2\u00c0\u00c1\7v\2"+
		"\2\u00c1\u00c2\7{\2\2\u00c2\u00c3\7r\2\2\u00c3\u00c4\7g\2\2\u00c4J\3\2"+
		"\2\2\u00c5\u00c6\7f\2\2\u00c6\u00c7\7g\2\2\u00c7\u00c8\7e\2\2\u00c8\u00c9"+
		"\7n\2\2\u00c9\u00ca\7c\2\2\u00ca\u00cb\7t\2\2\u00cb\u00cc\7g\2\2\u00cc"+
		"L\3\2\2\2\u00cd\u00ce\7c\2\2\u00ce\u00cf\7u\2\2\u00cf\u00d0\7u\2\2\u00d0"+
		"\u00d1\7g\2\2\u00d1\u00d2\7t\2\2\u00d2\u00d3\7v\2\2\u00d3N\3\2\2\2\u00d4"+
		"\u00d5\7v\2\2\u00d5\u00d6\7t\2\2\u00d6\u00d7\7w\2\2\u00d7\u00d8\7g\2\2"+
		"\u00d8P\3\2\2\2\u00d9\u00da\7h\2\2\u00da\u00db\7c\2\2\u00db\u00dc\7n\2"+
		"\2\u00dc\u00dd\7u\2\2\u00dd\u00de\7g\2\2\u00deR\3\2\2\2\u00df\u00e1\t"+
		"\2\2\2\u00e0\u00df\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e2"+
		"\u00e3\3\2\2\2\u00e3T\3\2\2\2\u00e4\u00e8\t\3\2\2\u00e5\u00e7\t\4\2\2"+
		"\u00e6\u00e5\3\2\2\2\u00e7\u00ea\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e8\u00e9"+
		"\3\2\2\2\u00e9V\3\2\2\2\u00ea\u00e8\3\2\2\2\u00eb\u00ef\t\5\2\2\u00ec"+
		"\u00ee\t\4\2\2\u00ed\u00ec\3\2\2\2\u00ee\u00f1\3\2\2\2\u00ef\u00ed\3\2"+
		"\2\2\u00ef\u00f0\3\2\2\2\u00f0X\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f2\u00f4"+
		"\t\6\2\2\u00f3\u00f2\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f5"+
		"\u00f6\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f8\b-\2\2\u00f8Z\3\2\2\2\u00f9"+
		"\u00fb\7\17\2\2\u00fa\u00fc\7\f\2\2\u00fb\u00fa\3\2\2\2\u00fb\u00fc\3"+
		"\2\2\2\u00fc\u00ff\3\2\2\2\u00fd\u00ff\7\f\2\2\u00fe\u00f9\3\2\2\2\u00fe"+
		"\u00fd\3\2\2\2\u00ff\u0100\3\2\2\2\u0100\u0101\b.\2\2\u0101\\\3\2\2\2"+
		"\u0102\u0103\7\61\2\2\u0103\u0104\7,\2\2\u0104\u0108\3\2\2\2\u0105\u0107"+
		"\13\2\2\2\u0106\u0105\3\2\2\2\u0107\u010a\3\2\2\2\u0108\u0109\3\2\2\2"+
		"\u0108\u0106\3\2\2\2\u0109\u010b\3\2\2\2\u010a\u0108\3\2\2\2\u010b\u010c"+
		"\7,\2\2\u010c\u010d\7\61\2\2\u010d\u010e\3\2\2\2\u010e\u010f\b/\2\2\u010f"+
		"^\3\2\2\2\u0110\u0111\7\61\2\2\u0111\u0112\7\61\2\2\u0112\u0116\3\2\2"+
		"\2\u0113\u0115\n\7\2\2\u0114\u0113\3\2\2\2\u0115\u0118\3\2\2\2\u0116\u0114"+
		"\3\2\2\2\u0116\u0117\3\2\2\2\u0117\u0119\3\2\2\2\u0118\u0116\3\2\2\2\u0119"+
		"\u011a\b\60\2\2\u011a`\3\2\2\2\13\2\u00e2\u00e8\u00ef\u00f5\u00fb\u00fe"+
		"\u0108\u0116\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}