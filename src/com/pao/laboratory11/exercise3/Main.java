package com.pao.laboratory11.exercise3;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

final class Transaction {
    private final int id;
    private final double amount;
    private final String country;
    private final String channel;

    public Transaction(int id, double amount, String country, String channel) {
        this.id = id;
        this.amount = amount;
        this.country = country;
        this.channel = channel;
    }

    public int getId() { return id; }
    public double getAmount() { return amount; }
    public String getCountry() { return country; }
    public String getChannel() { return channel; }

    @Override
    public String toString() {
        return String.format(Locale.US, "[%d] %.2f RON (Tara: %s, Canal: %s)", id, amount, country, channel);
    }
}
final class Snapshot {
    private final Map<String, Long> countByCountry;
    private final Map<String, Long> countByChannel;
    private final double totalAmount;
    private final List<Transaction> topTransactions;

    public Snapshot(Map<String, Long> byCountry, Map<String, Long> byChannel, double total, List<Transaction> top) {
        this.countByCountry = Collections.unmodifiableMap(new HashMap<>(byCountry));
        this.countByChannel = Collections.unmodifiableMap(new HashMap<>(byChannel));
        this.totalAmount = total;
        this.topTransactions = Collections.unmodifiableList(new ArrayList<>(top));
    }

    public Map<String, Long> getCountByCountry() { return countByCountry; }
    public Map<String, Long> getCountByChannel() { return countByChannel; }
    public double getTotalAmount() { return totalAmount; }
    public List<Transaction> getTopTransactions() { return topTransactions; }
}

class CustomCollectors {
    private static class Aggregator {
        Map<String, Long> byCountry = new HashMap<>();
        Map<String, Long> byChannel = new HashMap<>();
        double totalAmount = 0.0;
        List<Transaction> allTxs = new ArrayList<>();
    }

    public static Collector<Transaction, ?, Snapshot> toSnapshot(int topN) {
        return Collector.of(
                Aggregator::new,

                (agg, tx) -> {
                    agg.byCountry.put(tx.getCountry(), agg.byCountry.getOrDefault(tx.getCountry(), 0L) + 1);
                    agg.byChannel.put(tx.getChannel(), agg.byChannel.getOrDefault(tx.getChannel(), 0L) + 1);
                    agg.totalAmount += tx.getAmount();
                    agg.allTxs.add(tx);
                },

                (agg1, agg2) -> {
                    agg2.byCountry.forEach((k, v) -> agg1.byCountry.put(k, agg1.byCountry.getOrDefault(k, 0L) + v));
                    agg2.byChannel.forEach((k, v) -> agg1.byChannel.put(k, agg1.byChannel.getOrDefault(k, 0L) + v));
                    agg1.totalAmount += agg2.totalAmount;
                    agg1.allTxs.addAll(agg2.allTxs);
                    return agg1;
                },

                agg -> {
                    List<Transaction> topTxs = agg.allTxs.stream()
                            .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed()
                                    .thenComparingInt(Transaction::getId))
                            .limit(topN)
                            .collect(Collectors.toList());
                    return new Snapshot(agg.byCountry, agg.byChannel, agg.totalAmount, topTxs);
                }
        );
    }
}

public class Main {
    public static void main(String[] args) {
        List<Transaction> data = Arrays.asList(
                new Transaction(1, 1500.00, "RO", "WEB"),
                new Transaction(2, 200.50, "RO", "ATM"),
                new Transaction(3, 4500.00, "NG", "APP"),
                new Transaction(4, 300.00, "RO", "WEB"),
                new Transaction(5, 7500.00, "RU", "CRYPTO"),
                new Transaction(6, 150.00, "RO", "ATM"),
                new Transaction(7, 4500.00, "RU", "APP") // Tie-breaker la sumă cu id 3
        );

        Snapshot snap = data.stream().collect(CustomCollectors.toSnapshot(3));

        System.out.println("=== ANALIZA SNAPSHOT ===");
        System.out.printf(Locale.US, "TOTAL PROCESAT: %.2f RON%n%n", snap.getTotalAmount());

        System.out.println("--- 1. TOP 3 Tranzactii (dupa suma desc, apoi ID asc) ---");
        snap.getTopTransactions().forEach(System.out::println);
        System.out.println();
        System.out.println("--- 2. Distributie pe Tari ---");
        snap.getCountByCountry().entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue() + " tranzactii"));
        System.out.println();

        System.out.println("--- 3. Cele mai folosite Canale ---");
        snap.getCountByChannel().entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue() + " tranzactii"));

        System.out.println("\n(Demo Bonus Finalizat cu Succes!)");
    }
}