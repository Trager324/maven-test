package listeners; /***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/

import constant.Constants;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LoadCSV {
    public static class Loader extends CSV7BaseListener {
        public static final String EMPTY = "";
        /**
         * Load a list of row maps that map field name to value
         */
        List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
        /**
         * List of column names
         */
        List<String> header;
        /**
         * Build up a list of fields in current row
         */
        List<String> currentRowFieldValues;

        public void exitHdr(CSV7Parser.HdrContext ctx) {
            header = new ArrayList<String>();
            header.addAll(currentRowFieldValues);
        }

        public void enterRow(CSV7Parser.RowContext ctx) {
            currentRowFieldValues = new ArrayList<String>();
        }

        public void exitRow(CSV7Parser.RowContext ctx) {
            // If this is the header row, do nothing
            // if ( ctx.parent instanceof CSV7Parser.HdrContext ) return; OR:
            if (ctx.getParent().getRuleIndex() == CSV7Parser.RULE_hdr) return;
            // It's a data row
            Map<String, String> m = new LinkedHashMap<String, String>();
            int i = 0;
            for (String v : currentRowFieldValues) {
                m.put(header.get(i), v);
                i++;
            }
            rows.add(m);
        }

        public void exitString(CSV7Parser.StringContext ctx) {
            currentRowFieldValues.add(ctx.STRING().getText());
        }

        public void exitText(CSV7Parser.TextContext ctx) {
            currentRowFieldValues.add(ctx.TEXT().getText());
        }

        public void exitEmpty(CSV7Parser.EmptyContext ctx) {
            currentRowFieldValues.add(EMPTY);
        }
    }

    public static void main(String[] args) throws Exception {
        var input = CharStreams.fromPath(Constants.PATH_ANTLR
                .resolve("listeners/t.csv"));
        var lexer = new CSV7Lexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new CSV7Parser(tokens);
        parser.setBuildParseTree(true); // tell ANTLR to build a parse tree
        var tree = parser.file();

        var walker = new ParseTreeWalker();
        var loader = new Loader();
        walker.walk(loader, tree);
        System.out.println(loader.rows);
    }
}
