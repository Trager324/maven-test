package errors;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;

public class TestVec {
    public static void main(String[] args) {
        var input = CharStreams.fromString("[1,2,3,4,5,6]");
        var lexer = new VecLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new VecParser(tokens);
        parser.addErrorListener(new DiagnosticErrorListener());
        parser.vec4();
    }
}
