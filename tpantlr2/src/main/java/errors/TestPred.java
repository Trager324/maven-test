package errors;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;

public class TestPred {
    public static void main(String[] args) {
        CharStream input;
        input = CharStreams.fromString("x = 0;");
        var lexer = new PredLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new PredParser(tokens);
        parser.addErrorListener(new DiagnosticErrorListener());
        parser.assign();
    }
}
