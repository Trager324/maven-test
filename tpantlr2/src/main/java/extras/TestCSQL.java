/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/
package extras;

import constant.Constants;
import org.antlr.v4.runtime.*;

public class TestCSQL {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws Exception {
        var in = CharStreams.fromPath(Constants.PATH_ANTLR
                .resolve("extras/t.csql"));

        var fauxLexer = new CharsAsTokens(in, CSQL.tokenNames);
        var tokens = new CommonTokenStream(fauxLexer);
        var parser = new CSQL(tokens);
        var tree = parser.prog();
        System.out.println(tree.toStringTree(parser));
//		tree.inspect(parser);
    }
}
