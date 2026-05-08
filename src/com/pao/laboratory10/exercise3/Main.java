package com.pao.laboratory10.exercise3;

import java.util.*;
import java.util.stream.Collectors;

enum TipTranzactie {
    CREDIT, DEBIT
}

class Tranzactie {
    private int id;
    private double suma;
    private String data;
    private TipTranzactie tip;
    private String contSursa;

    public Tranzactie(int id, double suma, String data, TipTranzactie tip, String contSursa) {
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.tip = tip;
        this.contSursa = contSursa;
    }

    public double getSuma() { return suma; }
    public String getData() { return data; }
    public TipTranzactie getTip() { return tip; }
    public String getContSursa() { return contSursa; }

    @Override
    public String toString() {
        return String.format(Locale.US, "[%d] %s %s: %.2f RON (Cont: %s)", id, data, tip, suma, contSursa);
    }
}

public class Main {
    public static void main(String[] args) {
        List<Tranzactie> tranzactii = Arrays.asList(
                new Tranzactie(1, 1500.00, "2024-01-10", TipTranzactie.CREDIT, "RO_ING_01"),
                new Tranzactie(2, 200.50,  "2024-01-15", TipTranzactie.DEBIT,  "RO_BT_02"),
                new Tranzactie(3, 300.00,  "2024-01-20", TipTranzactie.DEBIT,  "RO_ING_01"),
                new Tranzactie(4, 4500.00, "2024-02-05", TipTranzactie.CREDIT, "RO_BCR_03"),
                new Tranzactie(5, 120.00,  "2024-02-14", TipTranzactie.DEBIT,  "RO_BT_02"),
                new Tranzactie(6, 800.00,  "2024-02-20", TipTranzactie.CREDIT, "RO_ING_01"),
                new Tranzactie(7, 2500.00, "2024-03-01", TipTranzactie.CREDIT, "RO_REVOLUT_04"),
                new Tranzactie(8, 50.00,   "2024-03-08", TipTranzactie.DEBIT,  "RO_BCR_03"),
                new Tranzactie(9, 150.00,  "2024-03-15", TipTranzactie.DEBIT,  "RO_REVOLUT_04"),
                new Tranzactie(10, 600.00, "2024-03-25", TipTranzactie.CREDIT, "RO_BT_02")
        );

        System.out.println("=== 1. Tranzactii de tip CREDIT ===");
        tranzactii.stream()
                .filter(t -> t.getTip() == TipTranzactie.CREDIT)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("=== 2. Suma totala procesata ===");
        double totalProcesat = tranzactii.stream()
                .mapToDouble(Tranzactie::getSuma)
                .sum();
        System.out.printf(Locale.US, "Total procesat: %.2f RON%n%n", totalProcesat);

        System.out.println("=== 3. Suma totala per luna ===");
        Map<String, Double> sumaPerLuna = tranzactii.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getData().substring(0, 7),
                        TreeMap::new,
                        Collectors.summingDouble(Tranzactie::getSuma)
                ));
        sumaPerLuna.forEach((luna, suma) ->
                System.out.printf(Locale.US, "%s: %.2f RON%n", luna, suma));
        System.out.println();

        System.out.println("=== 4. Top 3 tranzactii (dupa suma) ===");
        tranzactii.stream()
                .sorted(Comparator.comparingDouble(Tranzactie::getSuma).reversed())
                .limit(3)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("=== 5. Conturi sursa unice ===");
        List<String> conturiUnice = tranzactii.stream()
                .map(Tranzactie::getContSursa)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Conturi sursa unice: " + conturiUnice);
        System.out.println();

        System.out.println("=== 6. Suma medie per tranzactie ===");
        double media = tranzactii.stream()
                .mapToDouble(Tranzactie::getSuma)
                .average()
                .orElse(0.0);
        System.out.printf(Locale.US, "Suma medie: %.2f RON%n%n", media);

        System.out.println("=== 7. Extrase de cont detaliate per luna ===");
        Map<String, List<Tranzactie>> extrase = tranzactii.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getData().substring(0, 7),
                        TreeMap::new,
                        Collectors.toList()
                ));

        extrase.forEach((luna, listaTranzactii) -> {
            double totalLuna = listaTranzactii.stream().mapToDouble(Tranzactie::getSuma).sum();
            System.out.printf(Locale.US, "EXTRAS DE CONT - %s: %d tranzactii, total: %.2f RON%n",
                    luna, listaTranzactii.size(), totalLuna);
        });
    }
}