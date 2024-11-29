import left_recursion_removal.Expr14Lexer;
import left_recursion_removal.Expr14Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;


void main() {
    run(CharStreams.fromString("1*2+3;"));
    run(CharStreams.fromString("1+2*3;"));
}

void run(CharStream input) {
    var lexer = new Expr14Lexer(input);
    var tokens = new CommonTokenStream(lexer);
    var parser = new Expr14Parser(tokens);
    System.out.println(parser.stat().toStringTree());

}

