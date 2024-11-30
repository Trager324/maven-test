grammar ContextDependentPredicate;

prog: vec5
  ;

vec5
locals [int i=1]
: ( {$i<=5}? INT {$i++;} )* // match 5 INTs
;

INT: [0-9]+;

WS: [ \n] -> skip;
