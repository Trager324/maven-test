package com.syd.java19.struct;

import java.util.NoSuchElementException;

public class MyQueue<E> {
    int size = 0;

    public void push(E val) {
        tail = tail.next = new Node<>(val);
        size++;
    }    Node<E> head = new Node<>(), tail = head;

    public E peek() {
        if (isEmpty()) throw new NoSuchElementException();
        return head.next.val;
    }

    public E pop() {
        E res = peek();
        head.next = head.next.next;
        size--;
        return res;
    }

    public boolean isEmpty() {return size == 0;}

    public int size() {return size;}

    private static class Node<E> {
        E val;
        Node<E> next;

        Node() {}

        Node(E v) {val = v;}

        Node(E v, Node<E> n) {
            val = v;
            next = n;
        }
    }


}

