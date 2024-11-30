import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

void main() {
    var input = CharStreams.fromString("34;\na;\n;\n");
    var lexer = new MyELangLexer(input);
    var tokens = new CommonTokenStream(lexer);
    var parser = new MyELangParser(tokens);
    var tree = parser.stat();
    System.out.println(tree.toStringTree());
}
