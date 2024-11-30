import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import reference.SetType;

void run(CharStream input) {
    var lexer = new SetType(input);
    lexer.getAllTokens().forEach(System.out::println);
}

void main() {
    run(CharStreams.fromString("""
            "double"
            'single'
            """));
}