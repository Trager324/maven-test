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
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

public class TestE_Listener2 {
    public static class UnderlineListener extends BaseErrorListener {
        public void syntaxError(Recognizer<?, ?> recognizer,
                                Object offendingSymbol,
                                int line, int charPositionInLine,
                                String msg,
                                RecognitionException e) {
            System.err.println("line " + line + ":" + charPositionInLine + " " + msg);
            underlineError(recognizer, (Token) offendingSymbol,
                    line, charPositionInLine);
        }

        protected void underlineError(Recognizer<?, ?> recognizer,
                                      Token offendingToken, int line,
                                      int charPositionInLine) {
            CommonTokenStream tokens =
                    (CommonTokenStream) recognizer.getInputStream();
            String input = tokens.getTokenSource().getInputStream().toString();
            String[] lines = input.split("\n");
            String errorLine = lines[line - 1];
            System.err.println(errorLine);
            for (int i = 0; i < charPositionInLine; i++) System.err.print(" ");
            int start = offendingToken.getStartIndex();
            int stop = offendingToken.getStopIndex();
            if (start >= 0 && stop >= 0) {
                for (int i = start; i <= stop; i++) System.err.print("^");
            }
            System.err.println();
        }
    }

    public static void main(String[] args) throws Exception {
        var input = CharStreams.fromString("class T { int i; }");
        var lexer = new Simple2Lexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new Simple2Parser(tokens);
        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(new UnderlineListener());
        parser.prog();
    }
}
