/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/
package lexmagic;

import constant.Constants;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class ShiftVarComments {
    public static class CommentShifter extends Cymbol12ParserBaseListener {
        BufferedTokenStream tokens;
        TokenStreamRewriter rewriter;

        /**
         * Create TokenStreamRewriter attached to token stream
         * sitting between the Cymbol lexer and parser.
         */
        public CommentShifter(BufferedTokenStream tokens) {
            this.tokens = tokens;
            rewriter = new TokenStreamRewriter(tokens);
        }

        @Override
        public void exitVarDecl(Cymbol12Parser.VarDeclContext ctx) {
            var semi = ctx.getStop();
            int i = semi.getTokenIndex();
            var cmtChannel =
                    tokens.getHiddenTokensToRight(i, Cymbol12Lexer.COMMENTS);
            if (cmtChannel != null) {
                Token cmt = cmtChannel.getFirst();
                if (cmt != null) {
                    String txt = cmt.getText().substring(2);
                    String newCmt = "/* " + txt.trim() + " */\n";
                    rewriter.insertBefore(ctx.start, newCmt);
                    rewriter.replace(cmt, "\n");
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        var input = CharStreams.fromPath(Constants.PATH_ANTLR
                .resolve("lexmagic/t.cym"));
        var lexer = new Cymbol12Lexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new Cymbol12Parser(tokens);
        var tree = parser.file();

        var walker = new ParseTreeWalker();
        var shifter = new CommentShifter(tokens);
        walker.walk(shifter, tree);
        System.out.print(shifter.rewriter.getText());
    }
}
