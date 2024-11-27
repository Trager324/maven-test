import constant.Constants;
import lexmagic.SimplePyLexer;
import lexmagic.SimplePyParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;

void main() throws IOException {
    listTokens(CharStreams.fromPath(Constants.PATH_ANTLR
            .resolve("lexmagic/f.py")));
    run(CharStreams.fromPath(Constants.PATH_ANTLR
            .resolve("lexmagic/f.py")));
}

void listTokens(CharStream input) {
    System.out.println("\n----------------");
    var lexer = getLexer(input);
    lexer.getAllTokens().forEach(System.out::println);
}

void run(CharStream input) {
    System.out.println("\n----------------");
    var lexer = getLexer(input);
    var parser = getParser(lexer);
    var tree = parser.file();
}

SimplePyLexer getLexer(CharStream input) {
    return new SimplePyLexer(input);
}

SimplePyParser getParser(Lexer lexer) {
    var tokens = new CommonTokenStream(lexer);
    return new SimplePyParser(tokens);
}

