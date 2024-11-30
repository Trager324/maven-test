import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import reference.Enum3;

void run(CharStream input) {
    var lexer = new Enum3(input);
    lexer.getAllTokens().forEach(System.out::println);
}

void main() {
    run(CharStreams.fromString("enum abc"));
}