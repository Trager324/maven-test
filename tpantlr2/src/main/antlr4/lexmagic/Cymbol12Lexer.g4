/** Simple statically-typed programming language with functions and variables
 *  taken from "Language Implementation Patterns" book.
 */
lexer grammar Cymbol12Lexer;

//@lexer::members {
//    public static final int WHITESPACE = 1;
//    public static final int COMMENTS = 2;
//}

channels { WHITESPACE, COMMENTS }

EXCLAMATION  : '!';
EQ  : '=';
EQEQ  : '==';
SEMI: ';';
COLON: ':';
COMMA: ',';
LPAREN: '(';
RPAREN: ')';
LCURLY: '{';
RCURLY: '}';
LBRACK: '[';
RBRACK: ']';
PLUS: '+';
DASH: '-';
STAR: '*';
SLASH: '/';

KINT: 'int';
KDOUBLE: 'double';
KFLOAT: 'float';
KVOID: 'void';

IF: 'if';
ELSE: 'else';
THEN: 'then';
RETURN: 'return';


ID  :   LETTER (LETTER | [0-9])* ;
fragment
LETTER : [a-zA-Z] ;

INT :   [0-9]+ ;

WS  :   [ \t\n\r]+ -> channel(WHITESPACE) ;  // channel(1)

SL_COMMENT
    :   '//' .*? '\n' -> channel(COMMENTS)   // channel(2)
    ;
