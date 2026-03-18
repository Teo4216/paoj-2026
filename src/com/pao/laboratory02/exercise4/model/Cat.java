package com.pao.laboratory02.exercise4.model;

/**
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │  Implementează clasa Cat                                                │
 * └─────────────────────────────────────────────────────────────────────────┘
 */
public class Cat extends Animal {

    public Cat(String name, int age) {
        // Apelăm constructorul clasei de bază (Animal)
        super(name, age);
    }

    @Override
    public String sound() {
        // Returnăm sunetul specific pisicii
        return "Miau!";
    }
}