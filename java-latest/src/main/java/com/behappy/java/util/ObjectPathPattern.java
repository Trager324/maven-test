package com.behappy.java.util;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.annotation.Nullable;
import java.util.List;

public class ObjectPathPattern {
    enum NodeType {
        GROUP(null),
        STRING(null),
        SEPARATOR("."),
        STAR("*"),;
        final String value;

        NodeType(@Nullable String value) {this.value = value;}
    }

    static sealed class Node {
        final NodeType type;
        final String value;
        Node(NodeType type) {
            this(type, type.value);
        }
        Node(NodeType type, @Nullable String value) {
            this.type = type;
            this.value = value;
        }
    }

    static final class GroupNode extends Node {
        GroupNode(List<Node> children) {
            super(NodeType.GROUP);
        }
    }

    static final class SeparatorNode extends Node {
        SeparatorNode() {
            super(NodeType.SEPARATOR);
        }
    }

    static final class StarNode extends Node {
        StarNode() {
            super(NodeType.STAR);
        }
    }

    static final class StringNode extends Node {
        StringNode(String value) {
            super(NodeType.STRING, value);
        }
    }

    public static void main(String[] args) {
        // Sample pattern
        String pattern = "foo.*.baz";

        // Initialize the lexer and parser
        ObjectPathLexer lexer = new ObjectPathLexer(CharStreams.fromString(pattern));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ObjectPathParser parser = new ObjectPathParser(tokens);

        // Parse the pattern
        ParseTree tree = parser.path();

        // Build the DFA using the visitor
        ObjectPathPatternVisitor dfaVisitor = new ObjectPathPatternVisitor();

        // Test paths
        String[] testPaths = {
                "foo.bar.baz",   // should match
                "foo.baz",       // should not match
                "foo.anything.baz", // should match
                "foo..baz",      // should match (if '**' is used)
                "foo.*.qux.baz" // should not match
        };
    }

}
