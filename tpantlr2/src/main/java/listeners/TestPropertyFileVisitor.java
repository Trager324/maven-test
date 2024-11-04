package listeners; /***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/

import constant.Constants;
import org.antlr.v4.misc.OrderedHashMap;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.Map;

public class TestPropertyFileVisitor {
    public static class PropertyFileVisitor extends
            PropertyFileBaseVisitor<Void> {
        Map<String, String> props = new OrderedHashMap<String, String>();

        public Void visitProp(PropertyFileParser.PropContext ctx) {
            String id = ctx.ID().getText(); // prop : ID '=' STRING '\n' ;
            String value = ctx.STRING().getText();
            props.put(id, value);
            return null; // Java says must return something even when Void
        }
    }

    public static void main(String[] args) throws Exception {
        var input = CharStreams.fromPath(Constants.PATH_ANTLR
                .resolve("listeners/t.properties"));
        var lexer = new PropertyFileLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new PropertyFileParser(tokens);
        var tree = parser.file();

        var loader = new PropertyFileVisitor();
        loader.visit(tree);
        System.out.println(loader.props); // print results
    }
}
