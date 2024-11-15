/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
package errors;
import org.antlr.v4.runtime.*;

public class TestBail {
    public static class BailSimpleLexer extends Simple2Lexer {
        public BailSimpleLexer(CharStream input) { super(input); }
        public void recover(LexerNoViableAltException e) {
            throw new RuntimeException(e); // Bail out
        }
    }

    public static void main(String[] args) throws Exception {
        var input = CharStreams.fromString("System.in");
        var lexer = new BailSimpleLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new Simple2Parser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());
        parser.prog();
    }
}
