/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/
package listeners;

import constant.Constants;
import org.antlr.v4.misc.OrderedHashMap;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.Map;

public class TestPropertyFile {
    public static class PropertyFileLoader extends PropertyFileBaseListener {
        Map<String, String> props = new OrderedHashMap<>();

        public void exitProp(PropertyFileParser.PropContext ctx) {
            String id = ctx.ID().getText(); // prop : ID '=' STRING '\n' ;
            String value = ctx.STRING().getText();
            props.put(id, value);
        }
    }

    public static void main(String[] args) throws Exception {
        var input = CharStreams.fromPath(Constants.PATH_ANTLR
                .resolve("listeners/t.properties"));
        var lexer = new PropertyFileLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new PropertyFileParser(tokens);
        var tree = parser.file();

        // create a standard ANTLR parse tree walker
        var walker = new ParseTreeWalker();
        // create listener then feed to walker
        var loader = new PropertyFileLoader();
        walker.walk(loader, tree);        // walk parse tree
        System.out.println(loader.props); // print results
    }
}
