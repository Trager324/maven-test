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

import java.io.FileInputStream;
import java.io.InputStream;

public class TestR {
	public static void main(String[] args) throws Exception {
		var input = CharStreams.fromPath(Constants.PATH_ANTLR.resolve("examples/t.R"));
		var lexer = new RLexer(input);
		var tokens = new CommonTokenStream(lexer);
//		tokens.fill();
//		for (Object tok : tokens.getTokens()) {
//			System.out.println(tok);
//		}
		var filter = new RFilter(tokens);
		filter.stream(); // call start rule: stream
		tokens.reset();
//		for (Object tok : tokens.getTokens()) {
//			System.out.println(tok);
//		}
		RParser parser = new RParser(tokens);
		parser.setBuildParseTree(true);
		RuleContext tree = parser.prog();
		//tree.save(parser, "/tmp/R.ps"); // Generate postscript
		System.out.println(tree.toStringTree(parser));

	}
}
