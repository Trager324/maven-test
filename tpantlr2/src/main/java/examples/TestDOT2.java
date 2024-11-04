package examples;

import constant.Constants;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import util.ParseTreeVisualizer;

import java.io.IOException;

public class TestDOT2 {
    public static void main() throws IOException {
        var input = CharStreams.fromPath(Constants.PATH_ANTLR.resolve("examples/t.dot"));
        var lexer = new DOT2Lexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new DOT2Parser(tokens);
        var tree = parser.graph();
        ParseTreeVisualizer.visualize(tree, parser);
    }
}
