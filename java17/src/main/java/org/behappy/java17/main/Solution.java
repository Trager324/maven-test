package org.behappy.java17.main;


class Solution {
    static class A {
        public A() {
            var b = new A(1);
            System.out.println(b);
        }
        public A(int a) {
            System.out.println(a);
        }
    }

    public static void main(String[] args) {
        new A();
    }
}
