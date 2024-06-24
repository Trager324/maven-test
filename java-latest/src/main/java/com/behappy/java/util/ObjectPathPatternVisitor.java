package com.behappy.java.util;

import org.antlr.v4.runtime.tree.ParseTree;

import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * language=regex
 *
 * @see Pattern#MULTILINE
 * @see Pattern#DOTALL
 */
class ObjectPathPatternVisitor extends ObjectPathBaseVisitor<String> {
    ObjectPathPatternVisitor() {
    }

    Pattern getPattern(ParseTree tree) {
        var builder = visit(tree);
        return Pattern.compile(builder);
    }

    @Override
    public String visit(ParseTree tree) {
        return super.visit(tree);
    }

    @Override
    public String visitPath(ObjectPathParser.PathContext ctx) {
        var joiner = new StringJoiner("\\.");

        for (var segment : ctx.segment()) {
            joiner.add(visit(segment));
        }

        return joiner.toString();
    }

    @Override
    public String visitSegment(ObjectPathParser.SegmentContext ctx) {
        var builder = new StringBuilder();

        for (var piece : ctx.piece()) {
            builder.append(visit(piece));
        }

        return builder.toString();
    }

    @Override
    public String visitPiece(ObjectPathParser.PieceContext ctx) {
        var builder = new StringBuilder();
        if (ctx.WILDCARD_MULTI() != null) {
            builder.append(".*");
        } else if (ctx.WILDCARD_SINGLE() != null) {
            builder.append("[^.]*");
        } else if (ctx.chars() != null) {
            builder.append(visitChars(ctx.chars()));
        }
        return builder.toString();
    }

    @Override
    public String visitChars(ObjectPathParser.CharsContext ctx) {
        var builder = new StringBuilder();
        for (var child : ctx.children) {
            builder.append(Pattern.quote(child.getText()));
        }
        return builder.toString();
    }
}
