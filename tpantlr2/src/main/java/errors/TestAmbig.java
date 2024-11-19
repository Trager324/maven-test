package errors;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;
import org.antlr.v4.runtime.atn.PredictionMode;

public class TestAmbig {
    public static void main(String[] args) {
        CharStream input;
//        input = CharStreams.fromString("class T { int i; }");
        input = CharStreams.fromString("f();");
        var lexer = new AmbigLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new AmbigParser(tokens);
        parser.addErrorListener(new DiagnosticErrorListener());
        parser.getInterpreter().setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);
        parser.stat();

    }
}
