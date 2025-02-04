package org.behappy.algo.structure.test;

import org.behappy.algo.structure.Stack;
import org.behappy.algo.structure.test.common.JavaCollectionTest;
import org.behappy.algo.structure.test.common.StackTest;
import org.behappy.algo.structure.test.common.Utils;
import org.behappy.algo.structure.test.common.Utils.TestData;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class StackTests {

    @Test
    public void testArrayStack() {
        TestData data = Utils.generateTestData(1000);

        String aName = "Stack [array]";
        Stack.ArrayStack<Integer> aStack = new Stack.ArrayStack<Integer>();
        Collection<Integer> aCollection = aStack.toCollection();

        assertTrue(StackTest.testStack(aStack, aName,
                data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(aCollection, Integer.class, aName,
                data.unsorted, data.sorted, data.invalid));
    }

    @Test
    public void testLinkedStack() {
        TestData data = Utils.generateTestData(1000);

        String lName = "Stack [linked]";
        Stack.LinkedStack<Integer> lStack = new Stack.LinkedStack<Integer>();
        Collection<Integer> lCollection = lStack.toCollection();

        assertTrue(StackTest.testStack(lStack, lName,
                data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(lCollection, Integer.class, lName,
                data.unsorted, data.sorted, data.invalid));
    }
}
