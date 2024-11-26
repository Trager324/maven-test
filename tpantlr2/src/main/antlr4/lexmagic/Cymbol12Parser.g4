/** Simple statically-typed programming language with functions and variables
 *  taken from "Language Implementation Patterns" book.
 */
parser grammar Cymbol12Parser;
options { tokenVocab=Cymbol12Lexer; }

@lexer::members {
    public static final int WHITESPACE = 1;
    public static final int COMMENTS = 2;
}

file:   (functionDecl | varDecl)+ ;

varDecl
    :   type ID (EQ expr)? SEMI
    ;
type:   KFLOAG | KINT | KVOID ; // user-defined types

functionDecl
    :   type ID LPAREN formalParameters? RPAREN block // "void f(int x) {...}"
    ;

formalParameters
    :   formalParameter (COMMA formalParameter)*
    ;
formalParameter
    :   type ID
    ;

block:  LCURLY stat* RCURLY ;   // possibly empty statement block

stat:   block
    |   varDecl
    |   IF expr THEN stat (ELSE stat)?
    |   RETURN expr? SEMI
    |   expr EQ expr SEMI // assignment
    |   expr SEMI          // func call
    ;

expr:   ID LPAREN exprList? RPAREN    // func call like f(), f(x), f(1,2)
    |   expr LBRACK expr RBRACK       // array index like a[i], a[i][j]
    |   DASH expr                // unary minus
    |   EXCLAMATION expr                // boolean not
    |   expr STAR expr
    |   expr (PLUS|DASH) expr
    |   expr EQEQ expr          // equality comparison (lowest priority op)
    |   ID                      // variable reference
    |   INT
    |   LPAREN expr RPAREN
    ;

exprList : expr (COMMA expr)* ;   // arg list
