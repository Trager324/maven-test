import constant.Constants;
import lexmagic.XML12Lexer;
import lexmagic.XML12Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

void main() throws IOException {
    run(CharStreams.fromPath(Constants.PATH_ANTLR
            .resolve("lexmagic/XML-inputs/entity.xml")));
    run(CharStreams.fromPath(Constants.PATH_ANTLR
            .resolve("lexmagic/XML-inputs/cat.xml")));
    run(CharStreams.fromPath(Constants.PATH_ANTLR
            .resolve("lexmagic/XML-inputs/weekly-euc-jp.xml")));
}

void run(CharStream input) {
    var lexer = new XML12Lexer(input);
    var tokens = new CommonTokenStream(lexer);
    var parser = new XML12Parser(tokens);
    parser.document();
}

