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
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.Stack;

public class TestLEvaluator {
    /**
     * Sample "calculator"
     */
    public static class Evaluator extends LExprBaseListener {
        Stack<Integer> stack = new Stack<Integer>();

        public void exitMult(LExprParser.MultContext ctx) {
            int right = stack.pop();
            int left = stack.pop();
            stack.push(left * right);
        }

        public void exitAdd(LExprParser.AddContext ctx) {
            int right = stack.pop();
            int left = stack.pop();
            stack.push(left + right);
        }

        public void exitInt(LExprParser.IntContext ctx) {
            stack.push(Integer.valueOf(ctx.INT().getText()));
        }
    }

    public static void main(String[] args) throws Exception {
        var input = CharStreams.fromPath(Constants.PATH_ANTLR
                .resolve("listeners/t.expr"));
        var lexer = new LExprLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new LExprParser(tokens);
        parser.setBuildParseTree(true);      // tell ANTLR to build a parse tree
        var tree = parser.s(); // parse
        // show tree in text form
        System.out.println(tree.toStringTree(parser));

        var walker = new ParseTreeWalker();
        var eval = new Evaluator();
        walker.walk(eval, tree);
        System.out.println("stack result = " + eval.stack.pop());
    }
}
