package api;

import constant.Constants;
import org.antlr.v4.runtime.*;

/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/
public class TestCSV {
    public static void run(CharStream input) {
        var lex = new CSVLexer(input);
        // copy text out of sliding buffer and store in tokens
        lex.setTokenFactory(new CommonTokenFactory(true));
        TokenStream tokens = new UnbufferedTokenStream<CommonToken>(lex);
        CSVParser parser = new CSVParser(tokens);
        parser.setBuildParseTree(false);
        parser.file();
    }

    public static void main(String[] args) throws Exception {
        run(CharStreams.fromPath(Constants.PATH_ANTLR.resolve("api/sample.csv")));
    }
}
