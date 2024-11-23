/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/
package actions.tools;

import constant.Constants;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Calc {
    static void run(InputStream is) throws Exception {
        var br = new BufferedReader(new InputStreamReader(is));
        var expr = br.readLine();              // get first expression
        int line = 1;                             // track input expr line numbers

        var parser = new Expr5Parser(null); // share single parser instance
        parser.setBuildParseTree(false);          // don't need trees

        while (expr != null) {             // while we have more expressions
            // create new lexer and token stream for each line (expression)
            var input = CharStreams.fromString(expr + "\n");
            var lexer = new Expr5Lexer(input);
            lexer.setLine(line);           // notify lexer of input position
            lexer.setCharPositionInLine(0);
            var tokens = new CommonTokenStream(lexer);
            parser.setInputStream(tokens); // notify parser of new token stream
            parser.stat();                 // start the parser
            expr = br.readLine();          // see if there's another line
            line++;
        }
    }

    public static void main(String[] args) throws Exception {
        run(new FileInputStream(Constants.PATH_ANTLR
                .resolve("actions/t.expr")
                .toFile()));
        run(System.in);
    }
}
