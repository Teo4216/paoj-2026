package com.pao.laboratory08.exercise1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) {
        List<Student> studenti = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String nume = parts[0].trim();
                    int varsta = Integer.parseInt(parts[1].trim());
                    String oras = parts[2].trim();
                    String strada = parts[3].trim();

                    Adresa adresa = new Adresa(oras, strada);
                    Student student = new Student(nume, varsta, adresa);
                    studenti.add(student);
                }
            }
        } catch (Exception e) {
            System.out.println("Eroare la citirea fisierului: " + e.getMessage());
            return;
        }


        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) return;

        String commandLine = scanner.nextLine().trim();
        String[] commandParts = commandLine.split(" ", 2);
        String comanda = commandParts[0];

        try {
            switch (comanda) {
                case "PRINT" -> {
                    for (Student s : studenti) {
                        System.out.println(s);
                    }
                }
                case "SHALLOW" -> {
                    if (commandParts.length < 2) return;
                    String numeCautat = commandParts[1];
                    Student target = gasesteStudent(studenti, numeCautat);

                    if (target != null) {
                        Student clona = target.shallowClone();
                        clona.getAdresa().setOras("MODIFICAT");
                        System.out.println("Original: " + target);
                        System.out.println("Clona: " + clona);
                    }
                }
                case "DEEP" -> {
                    if (commandParts.length < 2) return;
                    String numeCautat = commandParts[1];
                    Student target = gasesteStudent(studenti, numeCautat);

                    if (target != null) {
                        Student clona = target.deepClone();
                        clona.getAdresa().setOras("MODIFICAT");
                        System.out.println("Original: " + target);
                        System.out.println("Clona: " + clona);
                    }
                }
            }
        } catch (CloneNotSupportedException e) {
            System.out.println("Eroare la clonare: " + e.getMessage());
        }
    }

    private static Student gasesteStudent(List<Student> studenti, String nume) {
        for (Student s : studenti) {
            if (s.getNume().equals(nume)) {
                return s;
            }
        }
        return null;
    }
}