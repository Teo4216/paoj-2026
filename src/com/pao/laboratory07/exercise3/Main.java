
package com.pao.laboratory07.exercise3;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = Integer.parseInt(scanner.nextLine().trim());
        List<Comanda> comenzi = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String[] tokens = scanner.nextLine().trim().split(" ");
            String tip = tokens[0];
            String nume = tokens[1];

            if (tip.equals("STANDARD")) {
                double pret = Double.parseDouble(tokens[2]);
                String client = tokens[3];
                comenzi.add(new ComandaStandard(nume, pret, client));
            } else if (tip.equals("DISCOUNTED")) {
                double pret = Double.parseDouble(tokens[2]);
                int discount = Integer.parseInt(tokens[3]);
                String client = tokens[4];
                comenzi.add(new ComandaRedusa(nume, pret, discount, client));
            } else if (tip.equals("GIFT")) {
                String client = tokens[2];
                comenzi.add(new ComandaGratuita(nume, client));
            }
        }

        comenzi.forEach(c -> System.out.println(c.descriere()));

        while (scanner.hasNext()) {
            String comanda = scanner.next();

            switch (comanda) {
                case "STATS" -> {
                    System.out.println("--- STATS ---");
                    Function<Comanda, String> tipComanda = c -> {
                        if (c instanceof ComandaStandard) return "STANDARD";
                        if (c instanceof ComandaRedusa) return "DISCOUNTED";
                        return "GIFT";
                    };
                    Map<String, Double> medii = comenzi.stream()
                            .collect(Collectors.groupingBy(tipComanda, Collectors.averagingDouble(Comanda::pretFinal)));

                    System.out.printf("STANDARD: medie = %.2f lei\n", medii.getOrDefault("STANDARD", 0.0));
                    System.out.printf("DISCOUNTED: medie = %.2f lei\n", medii.getOrDefault("DISCOUNTED", 0.0));
                    System.out.printf("GIFT: medie = %.2f lei\n", medii.getOrDefault("GIFT", 0.0));
                }
                case "FILTER" -> {
                    String threshStr = scanner.next();
                    double threshold = Double.parseDouble(threshStr);
                    System.out.println("--- FILTER (>= " + threshStr + ") ---");

                    List<Comanda> filtrate = comenzi.stream()
                            .filter(c -> c.pretFinal() >= threshold)
                            .toList();
                    filtrate.forEach(c -> System.out.println(c.descriere().replace(" [PLACED]", "")));
                }
                case "SORT" -> {
                    System.out.println("--- SORT (by client, then by pret) ---");
                    comenzi.stream()
                            .sorted(Comparator.comparing(Comanda::getClient).thenComparingDouble(Comanda::pretFinal))
                            .forEach(c -> System.out.println(c.descriere().replace(" [PLACED]", "")));
                }
                case "SPECIAL" -> {
                    System.out.println("--- SPECIAL (discount > 15%) ---");

                    comenzi.stream()
                            .filter(c -> c instanceof ComandaRedusa cr && cr.getDiscountProcent() > 15)
                            .forEach(c -> System.out.println(c.descriere().replace(" [PLACED]", "")));
                }
                case "QUIT" -> {
                    return;
                }
                default -> System.out.println("Comandă necunoscută.");
            }
        }
    }
}