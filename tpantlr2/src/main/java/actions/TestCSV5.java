package actions;

import constant.Constants;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class TestCSV5 {
    public static void main(String[] args) throws IOException {
        System.out.println(Double.MAX_VALUE == Float.MAX_VALUE);
        System.out.println(Double.POSITIVE_INFINITY == Float.POSITIVE_INFINITY);


        var input = CharStreams.fromPath(Constants.PATH_ANTLR
                .resolve("actions/users.csv"));
        var lexer = new CSV5Lexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new CSV5Parser(tokens);
        parser.file();
    }
}
