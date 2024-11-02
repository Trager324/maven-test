package examples;

import constant.Constants;
import org.antlr.v4.runtime.CharStreams;

import java.io.IOException;

public class TestCSV2 {
    public static void main(String[] args) throws IOException {
        var input = CharStreams.fromPath(Constants.PATH_ANTLR.resolve("examples/data.csv"));
        var lexer = new CSV2Lexer(input);
        lexer.getAllTokens().forEach(System.out::println);

    }
}
