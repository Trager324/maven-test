import constant.Constants;
import lexmagic.Tags12Lexer;
import lexmagic.Tags12Parser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

void main() throws IOException {
    var input = CharStreams.fromPath(PATH_XML
            .resolve("cat.xml"));
    var lexer = new Tags12Lexer(input);
    lexer.getAllTokens().forEach(System.out::println);
    var tokens = new CommonTokenStream(lexer);
    var parser = new Tags12Parser(tokens);
    var tree = parser.file();

}

Path PATH_XML = Constants.PATH_ANTLR.resolve("lexmagic/XML-inputs");

