package predicates;
/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import org.antlr.v4.runtime.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestEnum {
	static void run(CharStream input, boolean java5) throws IOException {
		EnumParser.java5 = java5;
		var lexer = new EnumLexer(input);
		var tokens = new CommonTokenStream(lexer);
		var parser = new EnumParser(tokens);
		parser.prog();

	}
    public static void main(String[] args) throws Exception {
		run(CharStreams.fromString("""
                enum Temp { HOT, COLD }"""), false);
		run(CharStreams.fromString("""
                enum Temp { HOT, COLD }"""), true);
	}
}
