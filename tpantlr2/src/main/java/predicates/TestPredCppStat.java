/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import predicates.PredCppStatLexer;
import predicates.PredCppStatParser;

void main() throws Exception {
    run(CharStreams.fromString("""
            f(i);"""));
    run(CharStreams.fromString("""
            T(i);"""));
}

static void run(CharStream input) throws IOException {
    var lexer = new PredCppStatLexer(input);
    var tokens = new CommonTokenStream(lexer);
    var parser = new PredCppStatParser(tokens);
    parser.stat();
}
