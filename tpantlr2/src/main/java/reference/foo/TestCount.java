import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import reference.foo.CountLexer;
import reference.foo.CountParser;

void main() {
    var input = CharStreams.fromString("9, 10, 11");
    var lexer = new CountLexer(input);
    var tokens = new CommonTokenStream(lexer);
    var parser = new CountParser(tokens);
    var tree = parser.list();

}
