import constant.Constants;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import reference.FuzzyJavaLexer;
import reference.FuzzyJavaParser;

void run(CharStream input) {
    var lexer = new FuzzyJavaLexer(input);
    var tokens = new CommonTokenStream(lexer);
    var parser = new FuzzyJavaParser(tokens);
    var tree = parser.file();
}

void main() throws IOException {
    run(CharStreams.fromPath(Constants.PATH_ANTLR
            .resolve("reference/C.java")));
}

