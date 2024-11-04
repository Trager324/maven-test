package tour;

import constant.Constants;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.IOException;

public class TestDataListener extends DataBaseListener {
    @Override
    public void enterSequence(DataParser.SequenceContext ctx) {
        System.out.println(ctx.n);

        System.out.println(ctx.INT().stream()
                .map(TerminalNode::toString)
                .toList());
    }


    public static void main(String[] args) throws IOException {

        var input = CharStreams.fromPath(Constants.PATH_ANTLR.resolve("tour/t.data"));
        var lexer = new DataLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new DataParser(tokens);
        var tree = parser.file(); // parse

        var walker = new ParseTreeWalker();
        walker.walk(new TestDataListener(), tree);
    }
}
