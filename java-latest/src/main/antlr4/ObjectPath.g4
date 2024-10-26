grammar ObjectPath;

// Entry point of the parser
path: segment ('.' segment)*;

// A segment can be either a wildcard '*' or '**' or an escaped part or a normal path part
segment: piece*;

piece: WILDCARD_MULTI
    | WILDCARD_SINGLE
    | chars
    ;

chars: (NORMAL_CHAR | ESCAPED_CHAR)+
   ;

// Tokens
WILDCARD_SINGLE: '*';
WILDCARD_MULTI: '**';
META_CHAR: [.*\\?{}[\]|()];
NORMAL_CHAR: ~[.*\\?{}[\]|()];
ESCAPE_CHAR: '\\' ;
ESCAPED_CHAR: ESCAPE_CHAR META_CHAR ;

WS: [ \t\n\r];
