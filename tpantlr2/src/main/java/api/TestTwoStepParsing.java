import api.CSV13Lexer;
import api.CSV13Parser;
import constant.Constants;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.atn.PredictionMode;

void main() throws IOException {
    var input = CharStreams.fromPath(Constants.PATH_ANTLR
            .resolve("api/sample.csv"));
    var lexer = new CSV13Lexer(input);
    var tokens = new CommonTokenStream(lexer);
    var parser = new CSV13Parser(tokens);
    twoStepParsing(parser, CSV13Parser::file);
}

<P extends Parser> void twoStepParsing(P parser, Function<P, ?> root) {
//    parser.getInterpreter().setSLL(true); // try with simpler/faster SLL(*)
    parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
// we don't want error messages or recovery during first try
    parser.removeErrorListeners();
    parser.setErrorHandler(new BailErrorStrategy());
    try {
        root.apply(parser);
//        parser.startRule();
// if we get here, there was no syntax error and SLL(*) was enough;
// there is no need to try full LL(*)
    } catch (RuntimeException ex) {
        if (ex.getClass() == RuntimeException.class &&
            ex.getCause() instanceof RecognitionException) {
// The BailErrorStrategy wraps the RecognitionExceptions in
// RuntimeExceptions so we have to make sure we're detecting
// a true RecognitionException not some other kind
//            tokens.reset(); // rewind input stream
            parser.reset();
// back to standard listeners/handlers
            parser.addErrorListener(ConsoleErrorListener.INSTANCE);
            parser.setErrorHandler(new DefaultErrorStrategy());
//            parser.getInterpreter().setSLL(false); // try full LL(*)
            parser.getInterpreter().setPredictionMode(PredictionMode.LL);
            root.apply(parser);
        }
    }
}
