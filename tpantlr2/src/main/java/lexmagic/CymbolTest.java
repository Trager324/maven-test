import lexmagic.Cymbol12Lexer;
import lexmagic.Cymbol12Parser;
import lexmagic.Cymbol12_2Lexer;
import lexmagic.Cymbol12_2Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;

void main() {
    var lexer = getLexer(CharStreams.fromString("int i = 3; // testing\n"));
    lexer.getAllTokens().forEach(System.out::println);
    System.out.println("\n\n----------------");
    var lexer2 = getLexer2(CharStreams.fromString("int i = 3; // testing\n"));
    lexer2.getAllTokens().forEach(System.out::println);
}

Cymbol12Lexer getLexer(CharStream input) {
    return new Cymbol12Lexer(input);
}

Cymbol12Parser getParser(Lexer lexer) {
    var tokens = new CommonTokenStream(lexer);
    return new Cymbol12Parser(tokens);
}

Cymbol12_2Lexer getLexer2(CharStream input) {
    return new Cymbol12_2Lexer(input);
}

Cymbol12_2Parser getParser2(Lexer lexer) {
    var tokens = new CommonTokenStream(lexer);
    return new Cymbol12_2Parser(tokens);
}
