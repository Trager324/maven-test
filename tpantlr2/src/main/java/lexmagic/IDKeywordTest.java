import lexmagic.IDKeywordLexer;
import lexmagic.IDKeywordParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

void main() {
    var input = CharStreams.fromString("if if then call call;");
    var lexer = new IDKeywordLexer(input);
    var tokens = new CommonTokenStream(lexer);
    var parser = new IDKeywordParser(tokens);
    parser.prog();
}



