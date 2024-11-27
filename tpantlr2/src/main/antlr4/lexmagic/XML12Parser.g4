parser grammar XML12Parser;
options { tokenVocab=XML12Lexer; }

document    :   prolog? misc* element misc*;

prolog      :   XMLDeclOpen attribute* SPECIAL_CLOSE ;

content     :   chardata?
                ((element | reference | CDATA | PI | COMMENT) chardata?)* ;

element     :   '<' Name attribute* '>' content '<' '/' Name '>'
            |   '<' Name attribute* '/>'
            ;

reference   :   EntityRef | CharRef ;

attribute   :   Name '=' STRING ; // Our STRING is AttValue in spec
/** ``All text that is not markup constitutes the character data of
 *  the document.''
 */
chardata    :   TEXT | SEA_WS ;

misc        :   COMMENT | PI | SEA_WS ;

//prolog : XMLDecl versionInfo encodingDecl? standalone? SPECIAL_CLOSE ;
//versionInfo : {_input.LT(1).getText().equals("version")}? Name '=' STRING ;
//encodingDecl : {_input.LT(1).getText().equals("encoding")}? Name '=' STRING ;
//standalone : {_input.LT(1).getText().equals("standalone")}? Name '=' STRING ;
