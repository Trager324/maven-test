/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/
package errors;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.ANTLRErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerNoViableAltException;

@Slf4j
public class TestBail {
    public static class BailSimpleLexer extends Simple2Lexer {
        public BailSimpleLexer(CharStream input) {super(input);}

        @Override
        public void recover(LexerNoViableAltException e) {
//            throw new RuntimeException(e); // Bail out
            throw new RuntimeException(e.getMessage(), e); // Bail out
        }
    }
//    public static class BailSimpleParser extends Simple2Parser {
//        public BailSimpleParser(TokenStream input) {super(input);}
//    }

    public static void run(CharStream input) {
        run(input, new BailErrorStrategy());
    }

    public static void run(CharStream input, ANTLRErrorStrategy handler) {
        try {
            var lexer = new BailSimpleLexer(input);
//            var lexer = new Simple2Lexer(input);
            var tokens = new CommonTokenStream(lexer);
            var parser = new Simple2Parser(tokens);
            parser.setErrorHandler(handler);
            parser.prog();
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    public static void main(String[] args) throws Exception {
//        run(CharStreams.fromString("# class T { int i; }"));
        run(CharStreams.fromString("class { int i; }"));
        run(CharStreams.fromString("class { }"), new MyErrorStrategy());
    }
}
