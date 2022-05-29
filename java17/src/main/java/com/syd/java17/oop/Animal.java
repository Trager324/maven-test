package com.syd.java17.oop;

/**
 * @author songyide
 * @date 2022/5/20
 */
public class Animal {
    public void eat() {
        System.out.println("Animal eat");
    }
}

class Dog extends Animal {
    @Override
    public void eat() {
        System.out.println("Dog eat");
    }

    public void eat(String food) {
        System.out.println("Dog eat " + food);
    }

    public static void main(String[] args) {
        Animal animal = new Dog();
        if (animal instanceof Dog) {
            Dog dog = (Dog) animal;
        }
    }
}
