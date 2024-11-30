import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import reference.NongreedyLexer;

void run(CharStream input) {
    var lexer = new NongreedyLexer(input);
    lexer.getAllTokens().forEach(System.out::println);
//    var tokens = new CommonTokenStream(lexer);
//    var parser = new NongreedyParser(tokens);
//    var root = parser.s();
}

void main() {
    run(CharStreams.fromString("""
            "quote:\\""
            """));
}