grammar XPR;

@header {package z3fol.antlr;}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

// Tokens
LPAREN: '(';
RPAREN: ')';
LBRACKET: '{';
RBRACKET: '}';
PLUS: '+';
MINUS: '-';
TIMES: '*';
DIV: '/';
GT: '>';
GE: '>=';
LE: '<=';
LT: '<';
EQ: '==';
NEQ: '!=';
ASSIGN: ':=';
POINT: '.';
POW: '^';
OR: '|';
AND: '&';
IMPLIES: '=>';
IFF: '<=>';
XOR: 'XOR';
NOT: '¬';
SEMICOLON: ';';

SET_IN: 'in';
SET_NOTIN: 'notin';
SET_UNION: '∪';
SET_INTERSECT: '∩';
SET_DIFF: '\\';
SET_SUB: '⊂';

EXISTS: 'Exists';
FORALL: 'Forall';
KWD_TYPE: 'type';
KWD_DECLARE: 'declare';
KWD_ASSERT: 'assert';
KWD_TRUE: 'true';
KWD_FALSE: 'false';

NUM_DECIMAL: [0-9]+;
IDENTIFIER_UC : [A-Z_][a-zA-Z0-9_]*;
IDENTIFIER_LC : [a-z_][a-zA-Z0-9_]*;

Whitespace: [ \t]+ -> skip;
Newline: ('\r' '\n'? | '\n') -> skip;
BlockComment: '/*' .*? '*/' -> skip;
LineComment: '//' ~[\r\n]* -> skip;

// ---------------------------------------------------------------------------

// General rules
number: MINUS? ('0' | NUM_DECIMAL);

// Variable type. Can be either a plain identifier, a tuple or a generic type (eg. Set).
type: typeIdentifier | typeTuple | typeSet;
typeIdentifier: IDENTIFIER_UC;
typeTuple: LPAREN type (',' type)* RPAREN;
typeSet: typeIdentifier LBRACKET RBRACKET;

// Variable usage
variable: variableIdentifier | variableTuple;
variableTuple: typeIdentifier LPAREN anyExpression (',' anyExpression)+ RPAREN;
variableIdentifier: IDENTIFIER_LC ('.' number)?;
variableWithType: type variableIdentifier;
variableWithTypeList: variableWithType (',' variableWithType)*;

// General expression
anyExpression: variable | arithExpression | setExpression | boolExpression;
anyExpressionList: anyExpression (',' anyExpression)*;

// Arithmetic expression
sumop: PLUS | MINUS;
mulop: TIMES | DIV;
arithExpression: arithSum;
arithExpressionList: arithExpression (',' arithExpression)*;
arithSum: arithMul (sumop arithMul)*;
arithMul: arithPow (mulop arithPow)*;
arithPow: arithAtom (POW arithExpression)?;
arithAtom: variable | number | LPAREN arithExpression RPAREN;

// Set expression
setExpression: setSum;
setSum: setMul (setSumOp setMul)*;
setMul: setAtom (SET_INTERSECT setAtom)*;
setSumOp: SET_UNION | SET_DIFF;
setAtom: variable | inlineSet | LPAREN setExpression RPAREN;
inlineSet: inlineSetEmpty | inlineSetValues;
inlineSetValues: LBRACKET anyExpressionList RBRACKET;
inlineSetEmpty: typeIdentifier  LBRACKET RBRACKET;

// Comparison statement
cmpop: LT | LE | GT | GE;
cmpStatement: arithExpression cmpop arithExpression;

// Equality statement
eqop: EQ | NEQ;
eqStatement: anyExpression eqop anyExpression;

// Set statement
setop: SET_IN | SET_NOTIN;
setStatement: anyExpression setop setExpression;
subsetExpression: setExpression SET_SUB setExpression;

// Bool expression
boolExpression : KWD_TRUE | KWD_FALSE | LPAREN quantifiedStatement RPAREN;

// General logic statement
logop: IMPLIES | IFF | XOR;
quantifiedStatement: ((FORALL | EXISTS) variableWithTypeList ':')? statement;
statement: disjunction (logop disjunction)?;
disjunction: conjunction (OR conjunction)*;
conjunction: statementAtom (AND statementAtom)*;
statementAtom: NOT? (variable| setStatement | subsetExpression | cmpStatement | eqStatement | LPAREN quantifiedStatement RPAREN | boolExpression );

// ---------------------------------------------------------------------------

// Document line varieties
typeDeclaration: KWD_TYPE typeIdentifier type?;
factDeclaration: quantifiedStatement;
variableDeclaration: variableWithType (ASSIGN anyExpression)?;
variableAssignment: variableIdentifier ASSIGN anyExpression;

// Document structure
document: (line ';'+)*;
line: typeDeclaration | factDeclaration | variableDeclaration | variableAssignment;