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
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class CheckSymbols {

    public static void error(Token t, String msg) {

        System.err.printf("line %d:%d %s\n", t.getLine(), t.getCharPositionInLine(),
                msg);
    }

    public void process(String[] args) throws Exception {
        var input = CharStreams.fromPath(Constants.PATH_ANTLR
                .resolve("listeners/vars.cymbol"));
        var lexer = new Cymbol7Lexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new Cymbol7Parser(tokens);
        parser.setBuildParseTree(true);
        var tree = parser.file();
        // show tree in text form
//        System.out.println(tree.toStringTree(parser));

        var walker = new ParseTreeWalker();
        var def = new DefPhase();
        walker.walk(def, tree);
        // create next phase and feed symbol table info from def to ref phase
        RefPhase ref = new RefPhase(def.globals, def.scopes);
        walker.walk(ref, tree);
    }

    public static void main(String[] args) throws Exception {
        new CheckSymbols().process(args);
    }
}
