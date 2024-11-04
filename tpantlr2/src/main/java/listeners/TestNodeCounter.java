package listeners; /***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.FileInputStream;
import java.io.InputStream;

public class TestNodeCounter {
    /**
     * Sample "collector"
     */
    public static class NodeCounter extends ExprBaseListener {
        public int interior = 0;
        public int leaves = 0;

        public void enterEveryRule(ParserRuleContext ctx) {
            interior++;
        }

        public void visitTerminal(TerminalNode node) {
            leaves++;
        }
    }

    public static void main(String[] args) throws Exception {
        String inputFile = null;
        if (args.length > 0) inputFile = args[0];
        InputStream is = System.in;
        if (inputFile != null) is = new FileInputStream(inputFile);
        var input = new ANTLRInputStream(is);
        var lexer = new ExprLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new ExprParser(tokens);
        var tree = parser.s(); // parse
        // show tree in text form
        System.out.println(tree.toStringTree(parser));

        ParseTreeWalker walker = new ParseTreeWalker();
        NodeCounter counter = new NodeCounter();
        walker.walk(counter, tree);
        System.out.println(counter.interior + " interior nodes");
        System.out.println(counter.leaves + " leaf nodes");
    }
}
