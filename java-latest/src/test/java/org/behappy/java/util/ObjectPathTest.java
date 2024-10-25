package org.behappy.java.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectPathTest {

    @Test
    void testSimple() {
        var path = ObjectPath.getPattern("foo.*.baz");
        var pred = path.asMatchPredicate();
        assertTrue(pred.test("foo.bar.baz"));
        assertFalse(pred.test("foo.bar"));
        assertTrue(pred.test("foo.anything.baz"));
        assertTrue(pred.test("foo..baz"));
        assertFalse(pred.test("foo.*.qux.baz"));
    }

    @Test
    void testEscape() {
        var path = ObjectPath.getPattern("foo.\\.\\*\\\\*");
        var pred = path.asMatchPredicate();
        assertTrue(pred.test("foo."));
        assertFalse(pred.test("foo.bar"));
        assertTrue(pred.test("foo.anything.baz"));
        assertTrue(pred.test("foo..baz"));
        assertFalse(pred.test("foo.*.qux.baz"));
    }
}