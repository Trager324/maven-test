import constant.Constants;
import lexmagic.ModeTagsLexer;
import lexmagic.ModeTagsParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

Path PATH_XML = Constants.PATH_ANTLR.resolve("lexmagic/XML-inputs");

void main() throws IOException {
    var input = CharStreams.fromString("Hello <name>John</name>\n");
    var lexer = new ModeTagsLexer(input);
    lexer.getAllTokens().forEach(System.out::println);
    var tokens = new CommonTokenStream(lexer);
    var parser = new ModeTagsParser(tokens);
    var tree = parser.file();

}


