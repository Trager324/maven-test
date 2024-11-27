/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/
package tour;

import org.antlr.v4.runtime.TokenStream;

public class ExtractInterfaceListener extends Java4BaseListener {
    Java4Parser parser;

    public ExtractInterfaceListener(Java4Parser parser) {
        this.parser = parser;
    }

    /**
     * Listen to matches of classDeclaration
     */
    @Override
    public void enterClassDeclaration(Java4Parser.ClassDeclarationContext ctx) {
        System.out.println("class " + ctx.Identifier() + " {");
    }

    @Override
    public void exitClassDeclaration(Java4Parser.ClassDeclarationContext ctx) {
        System.out.println("}");
    }

    /**
     * Listen to matches of methodDeclaration
     */
    @Override
    public void enterMethodDeclaration(
            Java4Parser.MethodDeclarationContext ctx
    ) {
        // need parser to get tokens
        TokenStream tokens = parser.getTokenStream();
        String type = "void";
        if (ctx.type() != null) {
            type = tokens.getText(ctx.type());
        }
        String args = tokens.getText(ctx.formalParameters());
        System.out.println("\t" + type + " " + ctx.Identifier() + args + ";");
    }

}
