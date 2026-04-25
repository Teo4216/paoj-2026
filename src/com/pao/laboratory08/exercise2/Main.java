package com.pao.laboratory08.exercise2;

import com.pao.laboratory08.exercise1.Adresa;
import com.pao.laboratory08.exercise1.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static final String INPUT_FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";
    private static final String OUTPUT_FILE_PATH = "rezultate.txt";

    public static void main(String[] args) {
        List<Student> studenti = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
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
        } catch (IOException e) {
            System.out.println("Eroare la citirea fișierului de intrare: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;
        int prag = scanner.nextInt();

        List<Student> studentiFiltrati = studenti.stream()
                .filter(s -> s.getVarsta() >= prag)
                .collect(Collectors.toList());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_PATH))) {
            System.out.println("Filtru: varsta >= " + prag);
            System.out.println("Rezultate: " + studentiFiltrati.size() + " studenti");
            System.out.println();

            for (Student s : studentiFiltrati) {
                String textStudent = s.toString();

                System.out.println(textStudent);

                bw.write(textStudent);
                bw.newLine();
            }

            System.out.println();
            System.out.println("Scris in: " + OUTPUT_FILE_PATH);

        } catch (IOException e) {
            System.out.println("Eroare la scrierea fișierului: " + e.getMessage());
        }
    }
}