/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/
package api;

import constant.Constants;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class TestSimpleMyToken {
    public static void main(String[] args) throws IOException {
        var input = CharStreams.fromPath(Constants.PATH_ANTLR
                .resolve("api/Simple-input"));
        var lexer = new Simple13Lexer(input);
        var factory = new MyTokenFactory(input);
        lexer.setTokenFactory(factory);
        var tokens = new CommonTokenStream(lexer);

        // now, print all tokens
        tokens.fill();
        var alltokens = tokens.getTokens();
        alltokens.forEach(System.out::println);

        // now parse
        var parser = new Simple13Parser(tokens);
        parser.setTokenFactory(factory);
        var t = parser.s();
        System.out.println(t.toStringTree(parser));
    }
}
