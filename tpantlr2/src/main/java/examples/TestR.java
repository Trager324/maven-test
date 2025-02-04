package examples;

import constant.Constants;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/
public class TestR {
    public static void run(CharStream input) {
        var lexer = new R2Lexer(input);
        var tokens = new CommonTokenStream(lexer);
        R2Parser parser = new R2Parser(tokens);
        parser.setBuildParseTree(true);
        var tree = parser.prog();
//		tree.inspect(parser); // show in gui
        //tree.save(parser, "/tmp/R.ps"); // Generate postscript
        System.out.println(tree.toStringTree(parser));
    }

    public static void main(String[] args) throws Exception {
        run(CharStreams.fromPath(Constants.PATH_ANTLR.resolve("examples/t.R")));
    }
}
