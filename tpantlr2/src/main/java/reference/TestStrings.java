import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import reference.Strings;

void run(CharStream input) {
    var lexer = new Strings(input);
    lexer.getAllTokens().forEach(System.out::println);
}

void main() {
    run(CharStreams.fromString("""
            "hi"
            "mom"
            """));
}