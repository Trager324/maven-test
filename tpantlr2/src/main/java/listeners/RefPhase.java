/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/
package listeners;

import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class RefPhase extends Cymbol7BaseListener {
    ParseTreeProperty<Scope> scopes;
    GlobalScope globals;
    Scope currentScope; // resolve symbols starting in this scope

    public RefPhase(GlobalScope globals, ParseTreeProperty<Scope> scopes) {
        this.scopes = scopes;
        this.globals = globals;
    }

    public void enterFile(Cymbol7Parser.FileContext ctx) {
        currentScope = globals;
    }

    public void enterFunctionDecl(Cymbol7Parser.FunctionDeclContext ctx) {
        currentScope = scopes.get(ctx);
    }

    public void exitFunctionDecl(Cymbol7Parser.FunctionDeclContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }

    public void enterBlock(Cymbol7Parser.BlockContext ctx) {
        currentScope = scopes.get(ctx);
    }

    public void exitBlock(Cymbol7Parser.BlockContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }

    public void exitVar(Cymbol7Parser.VarContext ctx) {
        var name = ctx.ID().getSymbol().getText();
        var var = currentScope.resolve(name);
        if (var == null) {
            CheckSymbols.error(ctx.ID().getSymbol(), "no such variable: " + name);
        }
        if (var instanceof FunctionSymbol) {
            CheckSymbols.error(ctx.ID().getSymbol(), name + " is not a variable");
        }
    }

    public void exitCall(Cymbol7Parser.CallContext ctx) {
        // can only handle f(...) not expr(...)
        var funcName = ctx.ID().getText();
        var meth = currentScope.resolve(funcName);
        if (meth == null) {
            CheckSymbols.error(ctx.ID().getSymbol(), "no such function: " + funcName);
        }
        if (meth instanceof VariableSymbol) {
            CheckSymbols.error(ctx.ID().getSymbol(), funcName + " is not a function");
        }
    }
}
