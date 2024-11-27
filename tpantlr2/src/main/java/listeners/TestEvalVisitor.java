package listeners; /***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/

import constant.Constants;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

public class TestEvalVisitor {
    // a4 -visitor LExpr.g4

    /**
     * Visitor "calculator"
     */
    public static class EvalVisitor extends ExprBaseVisitor<Integer> {
        public Integer visitE(ExprParser.EContext ctx) {
            if (ctx.getChildCount() == 3) { // operations have 3 children
                if (ctx.op.getType() == ExprParser.MULT) {
                    return visit(ctx.e(0)) * visit(ctx.e(1));
                } else {
                    return visit(ctx.e(0)) + visit(ctx.e(1)); // must be add
                }
            }
            return visitChildren(ctx);   // must be e above INT
        }

        @Override
        public Integer visitTerminal(TerminalNode node) {
            if (node.getSymbol().getType() == ExprParser.INT) {
                return Integer.valueOf(node.getText());
            }
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        var input = CharStreams.fromPath(Constants.PATH_ANTLR
                .resolve("listeners/t.expr"));
        var lexer = new ExprLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new ExprParser(tokens);
        parser.setBuildParseTree(true);      // tell ANTLR to build a parse tree
        var tree = parser.s(); // parse
        // show tree in text form
        System.out.println(tree.toStringTree(parser));

        var evalVisitor = new EvalVisitor();
        int result = evalVisitor.visit(tree);
        System.out.println("visitor result = " + result);
    }
}
