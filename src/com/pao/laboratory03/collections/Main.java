package com.pao.laboratory03.collections;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== PARTEA A: HashMap — frecvența cuvintelor ===");

        String[] words = {"java", "python", "java", "c++", "python", "java", "rust", "c++", "go"};

        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        System.out.println("Frecvență: " + wordCount);
        System.out.println("Conține 'rust'? " + wordCount.containsKey("rust"));

        System.out.println("Chei: " + wordCount.keySet());
        System.out.println("Valori: " + wordCount.values());

        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        System.out.println("\n=== PARTEA B: TreeMap — sortare automată ===");

        TreeMap<String, Integer> sortedWordCount = new TreeMap<>(wordCount);

        System.out.println("Sortat: " + sortedWordCount);
        System.out.println("Prima cheie: " + sortedWordCount.firstKey());
        System.out.println("Ultima cheie: " + sortedWordCount.lastKey());

        System.out.println("\n=== PARTEA C: Map cu obiecte ===");

        Map<String, List<String>> subjects = new HashMap<>();
        subjects.put("PAOJ", new ArrayList<>(Arrays.asList("Ana", "Mihai", "Ion")));
        subjects.put("BD", new ArrayList<>(Arrays.asList("Ana", "Elena")));


        System.out.println("Studenți la PAOJ: " + subjects.get("PAOJ"));

        subjects.get("BD").add("George");
        System.out.println("Studenți la BD (actualizat): " + subjects.get("BD"));
    }
}