/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/
package listeners;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class DefPhase extends Cymbol7BaseListener {
    ParseTreeProperty<Scope> scopes = new ParseTreeProperty<>();
    GlobalScope globals;
    Scope currentScope; // define symbols in this scope

    public void enterFile(Cymbol7Parser.FileContext ctx) {
        globals = new GlobalScope(null);
        currentScope = globals;
    }

    public void exitFile(Cymbol7Parser.FileContext ctx) {
        System.out.println(globals);
    }

    public void enterFunctionDecl(Cymbol7Parser.FunctionDeclContext ctx) {
        var name = ctx.ID().getText();
        var typeTokenType = ctx.type().start.getType();
        var type = Symbol.getType(typeTokenType);

        // push new scope by making new one that points to enclosing scope
        var function = new FunctionSymbol(name, type, currentScope);
        currentScope.define(function); // Define function in current scope
        saveScope(ctx, function);      // Push: set function's parent to current
        currentScope = function;       // Current scope is now function scope
    }

    void saveScope(ParserRuleContext ctx, Scope s) {scopes.put(ctx, s);}

    public void exitFunctionDecl(Cymbol7Parser.FunctionDeclContext ctx) {
        System.out.println(currentScope);
        currentScope = currentScope.getEnclosingScope(); // pop scope
    }

    public void enterBlock(Cymbol7Parser.BlockContext ctx) {
        // push new local scope
        currentScope = new LocalScope(currentScope);
        saveScope(ctx, currentScope);
    }

    public void exitBlock(Cymbol7Parser.BlockContext ctx) {
        System.out.println(currentScope);
        currentScope = currentScope.getEnclosingScope(); // pop scope
    }

    public void exitFormalParameter(Cymbol7Parser.FormalParameterContext ctx) {
        defineVar(ctx.type(), ctx.ID().getSymbol());
    }

    public void exitVarDecl(Cymbol7Parser.VarDeclContext ctx) {
        defineVar(ctx.type(), ctx.ID().getSymbol());
    }

    void defineVar(Cymbol7Parser.TypeContext typeCtx, Token nameToken) {
        var typeTokenType = typeCtx.start.getType();
        var type = Symbol.getType(typeTokenType);
        var var = new VariableSymbol(nameToken.getText(), type);
        currentScope.define(var); // Define symbol in current scope
    }
}
