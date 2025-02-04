package tour; /***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/

import constant.Constants;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class InsertSerialID {

    public static void run(CharStream input) {

        var lexer = new JavaLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new JavaParser(tokens);
        var tree = parser.compilationUnit(); // parse

        var walker = new ParseTreeWalker(); // create standard walker
        InsertSerialIDListener extractor = new InsertSerialIDListener(tokens);
        walker.walk(extractor, tree); // initiate walk of tree with listener

        // print back ALTERED stream
        System.out.println(extractor.rewriter.getText());

    }

    public static void main(String[] args) throws Exception {
        run(CharStreams.fromPath(Constants.PATH_ANTLR.resolve("tour/Demo.java")));
    }
}
