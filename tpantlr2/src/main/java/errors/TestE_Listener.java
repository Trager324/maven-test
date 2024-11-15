/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/
package errors;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.Collections;
import java.util.List;

public class TestE_Listener {
    public static class VerboseListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                                Object offendingSymbol,
                                int line, int charPositionInLine,
                                String msg,
                                RecognitionException e) {
            List<String> stack = ((Parser) recognizer).getRuleInvocationStack();
            Collections.reverse(stack);
            System.err.println("rule stack: " + stack);
            System.err.println("line " + line + ":" + charPositionInLine + " at " +
                               offendingSymbol + ": " + msg);
        }

    }

    public static void main(String[] args) throws Exception {
        CharStream input;
//        input = CharStreams.fromString("class T { int i; }");
        input = CharStreams.fromString("class T ; { int i; }");
//        input = CharStreams.fromString("""
//                class T {
//                  int f(x) { a = 3 4 5; }
//                }
//                """);
        var lexer = new Simple2Lexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new Simple2Parser(tokens);
        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(new VerboseListener()); // add ours
        parser.prog(); // parse as usual
    }
}
