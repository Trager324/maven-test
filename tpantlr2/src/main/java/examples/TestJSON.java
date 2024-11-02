package examples;

import constant.Constants;
import org.antlr.v4.runtime.CharStreams;

import java.io.IOException;

public class TestJSON {
    public static void main(String[] args) throws IOException {
        var input = CharStreams.fromPath(Constants.PATH_ANTLR.resolve("examples/t.json"));
        var lexer = new JSONLexer(input);
        lexer.getAllTokens().forEach(System.out::println);
    }
}
