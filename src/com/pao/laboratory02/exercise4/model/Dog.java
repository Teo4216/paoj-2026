package com.pao.laboratory02.exercise4.model;

public class Dog extends Animal {

    public Dog(String name, int age) {
        super(name, age);
    }

    @Override
    public String sound() {
        return "Ham!";
    }
}