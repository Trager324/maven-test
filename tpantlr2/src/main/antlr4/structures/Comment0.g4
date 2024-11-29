grammar Comment0;
file : (ID {System.out.println($ID.text);})+ ;
ID : 'a'..'z'+ ;
LINE_COMMENT : '//' .*? '\r'? '\n' -> skip ;
COMMENT      : '/*' .*? '*/' -> skip ;
WS : (' '|'\t'|'\n')+ -> skip ;
