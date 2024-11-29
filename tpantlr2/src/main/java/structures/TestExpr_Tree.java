/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/
package structures;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class TestExpr_Tree {
    public static void main(String[] args) throws Exception {
        var input = CharStreams.fromStream(System.in);
        var lexer = new Expr0Lexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new Expr0Parser(tokens);
        parser.setBuildParseTree(true);
        var tree = parser.expr();
        System.out.println(tree.toStringTree(parser));
    }
}
