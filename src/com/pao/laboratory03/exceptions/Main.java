package com.pao.laboratory03.exceptions;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void riskyMethod() {
        String s = null;
        s.length(); // Aici va crăpa pentru că încercăm să aflăm lungimea lui null
    }

    public static void validateAge(int age) {
        if (age < 0 || age > 150) {
            throw new InvalidAgeException("Vârsta " + age + " nu este validă (0-150)");
        }
    }

    public static void addToList(List<String> list, String name) {
        if (list.contains(name)) {
            throw new DuplicateEntryException("'" + name + "' există deja în listă");
        }
        list.add(name);
    }

    public static void process(int age) throws InvalidAgeException {
        validateAge(age);
    }
    public static void main(String[] args) {

        System.out.println("=== a) Unchecked — NullPointerException ===");
        try {
            riskyMethod();
        } catch (NullPointerException e) {
            System.out.println("Prins: " + e.getMessage());
        } finally {
            System.out.println("Finally se execută mereu!");
        }

        System.out.println("\n=== b) Custom exceptions ===");
        try {
            validateAge(-5);
        } catch (InvalidAgeException e) {
            System.out.println("InvalidAgeException: " + e.getMessage());
        }

        try {
            List<String> names = new ArrayList<>();
            names.add("Ana");
            addToList(names, "Ana"); // Aici va arunca excepția
        } catch (DuplicateEntryException e) {
            System.out.println("DuplicateEntryException: " + e.getMessage());
        }

        System.out.println("\n=== c) Multi-catch ===");
        try {
            validateAge(200);
        } catch (InvalidAgeException | DuplicateEntryException e) {
            System.out.println("Excepție prinsă: " + e.getMessage());
        }

        System.out.println("\n=== d) Catch ordering (specific → general) ===");
        try {
            validateAge(-1);
        } catch (InvalidAgeException e) {
            System.out.println("InvalidAgeException prinsă specific: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Prinsă de clasa de bază: " + e.getMessage());
        }

        System.out.println("\n=== e) Throw vs throws ===");
        try {
            process(999);
        } catch (InvalidAgeException e) {
            System.out.println("Metoda process() a aruncat: " + e.getMessage());
        }
    }
}