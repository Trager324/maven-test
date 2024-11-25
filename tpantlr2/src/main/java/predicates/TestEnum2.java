package predicates;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/

public class TestEnum2 {
    static void run(CharStream input, boolean java5) throws IOException {
        Enum2Lexer.java5 = java5; // in lexer not parser
        var lexer = new Enum2Lexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new Enum2Parser(tokens);
        parser.prog();

    }
    public static void main(String[] args) throws Exception {
        run(CharStreams.fromString("""
                enum Temp { HOT, COLD }"""), false);
        run(CharStreams.fromString("""
                enum Temp { HOT, COLD }"""), true);
    }
}
