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
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Stack;

public class TestEvaluator {
    /**
     * Sample "calculator" (special case of collector)
     */
    public static class Evaluator extends ExprBaseListener {
        Stack<Integer> stack = new Stack<Integer>();

        public void exitE(ExprParser.EContext ctx) {
            if (ctx.getChildCount() == 3) { // operations have 3 children
                int right = stack.pop();
                int left = stack.pop();
                if (ctx.op.getType() == ExprParser.MULT) {
                    stack.push(left * right);
                } else {
                    stack.push(left + right); // must be add
                }
            }
        }

        public void visitTerminal(TerminalNode node) {
            Token symbol = node.getSymbol();
            if (symbol.getType() == ExprParser.INT) {
                stack.push(Integer.valueOf(symbol.getText()));
            }
        }
    }

    /**
     * Sample "calculator" using tree properties not stack
     */
    public static class EvaluatorWithProps extends ExprBaseListener {
        ParseTreeProperty<Integer> values = new ParseTreeProperty<Integer>();

        @Override
        public void exitS(ExprParser.SContext ctx) {
            values.put(ctx, values.get(ctx.getChild(0)));
        }

        public void exitE(ExprParser.EContext ctx) {
            if (ctx.getChildCount() == 3) { // operations have 3 children
                int left = values.get(ctx.e(0));
                int right = values.get(ctx.e(1));
                if (ctx.op.getType() == ExprParser.MULT) {
                    values.put(ctx, left * right);
                } else {
                    values.put(ctx, left + right);
                }
            } else {
                values.put(ctx, values.get(ctx.getChild(0))); // an INT
            }
        }

        public void visitTerminal(TerminalNode node) {
            Token symbol = node.getSymbol();
            if (symbol.getType() == ExprParser.INT) {
                values.put(node, Integer.valueOf(symbol.getText()));
            }
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

        var walker = new ParseTreeWalker();

        var eval = new Evaluator();
        walker.walk(eval, tree);
        System.out.println("result = " + eval.stack.pop());

        EvaluatorWithProps eval2 = new EvaluatorWithProps();
        walker.walk(eval2, tree);
        System.out.println("result with tree props = " + eval2.values.get(tree));
    }
}
