package errors;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;

public class TestCall {
    static void run(CharStream input) {
        var lexer = new CallLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new CallParser(tokens);
        parser.addErrorListener(new DiagnosticErrorListener());
        parser.stat();
    }

    public static void main(String[] args) {
        run(CharStreams.fromString("f(34);"));
        run(CharStreams.fromString("f((34);"));
        run(CharStreams.fromString("f((34)));"));
    }
}
