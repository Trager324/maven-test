parser grammar Simple13Parser;
options {
  // get token types from SimpleLexer.tokens; don't name it
  // SimpleParser.tokens as ANTLR will overwrite!
  tokenVocab=Simple13Lexer;
}

s : ( ID | INT )* SEMI ;
