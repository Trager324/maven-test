package actions;

import constant.Constants;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class TestCSV6 {
    public static void main(String[] args) throws IOException {
        var input = CharStreams.fromPath(Constants.PATH_ANTLR
                .resolve("actions/users.csv"));
        var lexer = new CSV6Lexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new CSV6Parser(tokens);
        parser.file();
    }
}
