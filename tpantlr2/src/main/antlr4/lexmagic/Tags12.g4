grammar Tags12;
file : (TAG|ENTITY|TEXT|CDATA)* ;

COMMENT : '<!--' .*? '-->' -> skip ;
CDATA : '<![CDATA[' .*? ']]>' ;
TAG : '<' .*? '>' ; // must come after other tag-like structures
ENTITY : '&' .*? ';' ;
TEXT : ~[<&]+ ;     // any sequence of chars except < and & chars

BAD_COMMENT1: '<!--' .*? '--->'
{System.err.println("Can't have ---> end comment");} -> skip ;
BAD_COMMENT2: '<!--' ('--'|.)*? '-->'
{System.err.println("Can't have -- in comment");} -> skip ;
