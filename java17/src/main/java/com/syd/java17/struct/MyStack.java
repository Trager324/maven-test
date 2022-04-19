package com.syd.java17.struct;

import java.util.EmptyStackException;

public class MyStack<E> {
    private static class Node<E> {
        E val;
        Node<E> next;
        Node() {}
        Node(E v) {val = v;}
        Node(E v, Node<E> n) {val = v;next = n;}
    }
    Node<E> head = new Node<>();
    int size = 0;
    public void push(E val) {
        head.next = new Node<>(val, head.next);
        size++;
    }
    public E peek() {
        Node<E> n = head.next;
        if (n == null) throw new EmptyStackException();
        return n.val;
    }
    public E pop() {
        E res = peek();
        head.next = head.next.next;
        size--;
        return res;
    }
    public boolean isEmpty() {return size == 0;}
    public int size() {return size;}
}

