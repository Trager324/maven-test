import constant.Constants;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import reference.DynScopeLexer;
import reference.DynScopeParser;

void run(CharStream input) {
    var lexer = new DynScopeLexer(input);
    var tokens = new CommonTokenStream(lexer);
    var parser = new DynScopeParser(tokens);
    var tree = parser.prog();
}

void main() throws IOException {
    run(CharStreams.fromString("""
            {
              int i;
              i = 0;
              j = 3;
            }
            """));
    run(CharStreams.fromPath(Constants.PATH_ANTLR.resolve("reference/nested-input")));
}
