import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import reference.ActionsLexer;

void run(CharStream input) {
    var lexer = new ActionsLexer(input);
    lexer.getAllTokens().forEach(System.out::println);
}


void main() {
    run(CharStreams.fromString("{\"foo}"));
    run(CharStreams.fromString("[\"foo]"));
    run(CharStreams.fromString("<\"foo>"));
}