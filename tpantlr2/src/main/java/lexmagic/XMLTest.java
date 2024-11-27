import lexmagic.XML12Lexer;
import lexmagic.XML12Parser;
import org.antlr.v4.gui.TestRig;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

void main() throws Exception {
//    run(CharStreams.fromPath(Constants.PATH_ANTLR
//            .resolve("lexmagic/XML-inputs/entity.xml")));
//    run(CharStreams.fromPath(Constants.PATH_ANTLR
//            .resolve("lexmagic/XML-inputs/cat.xml")));
//    run(CharStreams.fromPath(Constants.PATH_ANTLR
//            .resolve("lexmagic/XML-inputs/weekly-euc-jp.xml")));
    var args = new String[]{"lexmagic.XML12", "document", "-ps", "t.ps",
            "/Users/songyd/IdeaProjects/maven-test-dev/tpantlr2/src/main/antlr4/lexmagic/XML-inputs/entity.xml",};
    runGUI(args);
}

void runGUI(String[] args) throws Exception {
    var rig = new TestRig(args);
    rig.process();

}

void run(CharStream input) {
    var lexer = new XML12Lexer(input);
    var tokens = new CommonTokenStream(lexer);
    var parser = new XML12Parser(tokens);
    parser.document();
}

