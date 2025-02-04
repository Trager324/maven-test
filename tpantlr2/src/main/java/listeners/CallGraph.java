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
import org.antlr.v4.runtime.misc.MultiMap;
import org.antlr.v4.runtime.misc.OrderedHashSet;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.stringtemplate.v4.ST;

import java.util.Set;

public class CallGraph {
    /**
     * A graph model of the output. Tracks call from one function to
     * another by mapping function to list of called functions. E.g.,
     * f -> [g, h]
     * Can dump DOT two ways (StringBuilder and ST). Sample output:
     * digraph G {
     * ... setup ...
     * f -> g;
     * g -> f;
     * g -> h;
     * h -> h;
     * }
     */
    static class Graph {
        // I'm using org.antlr.v4.runtime.misc: OrderedHashSet, MultiMap
        Set<String> nodes = new OrderedHashSet<>(); // list of functions
        MultiMap<String, String> edges = new MultiMap<>(); // caller->callee

        public void edge(String source, String target) {
            edges.map(source, target);
        }

        public String toString() {
            return "edges: " + edges.toString() + ", functions: " + nodes;
        }

        public String toDOT() {
            StringBuilder buf = new StringBuilder("""
                    digraph G {
                      ranksep=.25;
                      edge [arrowsize=.5]
                      node [shape=circle, fontname="ArialNarrow",
                            fontsize=12, fixedsize=true, height=.45];
                    \s\s""");
            for (String node : nodes) { // print all nodes first
                buf.append(node);
                buf.append("; ");
            }
            buf.append("\n");
            for (String src : edges.keySet()) {
                for (String trg : edges.get(src)) {
                    buf.append("  ");
                    buf.append(src);
                    buf.append(" -> ");
                    buf.append(trg);
                    buf.append(";\n");
                }
            }
            buf.append("}\n");
            return buf.toString();
        }

        /**
         * Fill StringTemplate:
         * digraph G {
         * rankdir=LR;
         * <edgePairs:{edge| <edge.a> -> <edge.b>;}; separator="\n">
         * <childless:{f | <f>;}; separator="\n">
         * }
         * <p>
         * Just as an example. Much cleaner than buf.append method
         */
        public ST toST() {
            ST st = new ST("""
                    digraph G {
                      ranksep=.25;
                      edge [arrowsize=.5]
                      node [shape=circle, fontname="ArialNarrow",
                            fontsize=12, fixedsize=true, height=.45];
                      <funcs:{f | <f>; }>
                      <edgePairs:{edge| <edge.a> -> <edge.b>;}; separator="\\n">
                    }
                    """
            );
            st.add("edgePairs", edges.getPairs());
            st.add("funcs", nodes);
            return st;
        }
    }

    static class FunctionListener extends CymbolBaseListener {
        Graph graph = new Graph();
        String currentFunctionName = null;

        @Override
        public void enterFunctionDecl(CymbolParser.FunctionDeclContext ctx) {
            currentFunctionName = ctx.ID().getText();
            graph.nodes.add(currentFunctionName);
        }

        @Override
        public void exitCall(CymbolParser.CallContext ctx) {
            String funcName = ctx.ID().getText();
            // map current function to the callee
            graph.edge(currentFunctionName, funcName);
        }
    }

    public static void main(String[] args) throws Exception {
        var input = CharStreams.fromPath(Constants.PATH_ANTLR
                .resolve("listeners/t.cymbol"));
        var lexer = new CymbolLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new CymbolParser(tokens);
        parser.setBuildParseTree(true);
        var tree = parser.file();
        // show tree in text form
//        System.out.println(tree.toStringTree(parser));

        var walker = new ParseTreeWalker();
        var collector = new FunctionListener();
        walker.walk(collector, tree);
        System.out.println(collector.graph.toString());
//        System.out.println(collector.graph.toDOT());

        // Here's another example that uses StringTemplate to generate output
        System.out.println(collector.graph.toST().render());
    }
}
