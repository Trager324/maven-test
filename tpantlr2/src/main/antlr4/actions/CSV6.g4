grammar CSV6;

@header {
import java.util.*;
import java.util.stream.Stream;
}

/** Derived from rule "file : hdr row+ ;" */
file
locals [
int i=0]
     : hdr {
$hdr.cols = Stream.of($hdr.text.split(","))
        .map(String::trim)
        .toList();
     } ( rows+=row[$hdr.cols] {$i++;} )+
       {
       System.out.println($i+" rows");
       for (RowContext r : $rows) {
           System.out.println("row token interval: "+r.getSourceInterval());
       }
       }
     ;

hdr
returns [List<String> cols]: row[null] {System.out.println("header: '"+$text.trim()+"'");} ;

/** Derived from rule "row : field (',' field)* '\r'? '\n' ;" */
row[List<String> columns] returns [Map<String,String> values]
locals [int col=0]
@init {
    $values = new HashMap<String,String>();
}
@after {
    if ($values!=null && $values.size()>0) {
        System.out.println("values = "+$values);
    }
}
// rule row cont'd...
    :   field
        {
        if ($columns!=null) {
            $values.put($columns.get($col++), $field.text.trim());
        }
        }
        (   ',' field
            {
            if ($columns!=null) {
                $values.put($columns.get($col++), $field.text.trim());
            }
            }
        )* '\r'? '\n'
    ;

field
    :   TEXT
    |   STRING
    |
    ;

TEXT : ~[,\n\r"]+ ;
STRING : '"' ('""'|~'"')* '"' ; // quote-quote is an escaped quote
