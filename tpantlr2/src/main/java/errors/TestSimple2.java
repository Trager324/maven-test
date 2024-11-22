package errors;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;

public class TestSimple2 {
    public static void main(String[] args) {
        var input = CharStreams.fromString("""
                class T {
                  int int x;
                }
                """);
        var lexer = new Simple2Lexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new Simple2Parser(tokens);
        parser.addErrorListener(new DiagnosticErrorListener());
        parser.prog();
    }
}
