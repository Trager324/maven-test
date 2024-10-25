package org.behappy.java.util;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class ObjectPath {
    static final ObjectPathPatternVisitor VISITOR = new ObjectPathPatternVisitor();

    public static Pattern getPattern(String rawPattern) {
        var lexer = new ObjectPathLexer(CharStreams.fromString(rawPattern));
        var tokens = new CommonTokenStream(lexer);
        var parser = new ObjectPathParser(tokens);
        var tree = parser.path();
        var pattern = VISITOR.visit(tree);
        log.trace(pattern);
        return Pattern.compile(pattern);
    }

    public static void main(String[] args) {
        // Sample pattern
        var path = getPattern("foo.*.baz");

        // Test paths
        for (String testPath : List.of(
                "foo.bar.baz",   // should match
                "foo.baz",       // should not match
                "foo.anything.baz", // should match
                "foo..baz",      // should match (if '**' is used)
                "foo.*.qux.baz" // should not match
        )) {
            log.info(testPath + ": " + path.matcher(testPath).matches());
        }
    }

}
