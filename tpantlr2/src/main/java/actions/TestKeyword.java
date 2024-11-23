package actions;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class TestKeyword {
    static void run(CharStream input) {
        var lexer = new KeywordsLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new KeywordsParser(tokens);
        parser.stat();
    }

    public static void main(String[] args) throws Exception {
        run(CharStreams.fromString("x = 34;"));
        run(CharStreams.fromString("if = 34;"));
        run(CharStreams.fromString("if 1 then i = 4;"));
    }
}
