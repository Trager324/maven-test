package listeners;

/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
 ***/
public class Symbol { // A generic programming language symbol
    public enum Type {
        T_INVALID, T_VOID, T_INT, T_FLOAT;
    }

    public static Type getType(int tokenType) {
        return switch (tokenType) {
            case Cymbol7Parser.K_VOID -> Type.T_VOID;
            case Cymbol7Parser.K_INT -> Type.T_INT;
            case Cymbol7Parser.K_FLOAT -> Type.T_FLOAT;
            default -> Type.T_INVALID;
        };
    }

    String name;      // All symbols at least have a name
    Type type;
    Scope scope;      // All symbols know what scope contains them.

    public Symbol(String name) {this.name = name;}

    public Symbol(String name, Type type) {
        this(name);
        this.type = type;
    }

    public String getName() {return name;}

    public String toString() {
        if (type != Type.T_INVALID) return '<' + getName() + ":" + type + '>';
        return getName();
    }
}
