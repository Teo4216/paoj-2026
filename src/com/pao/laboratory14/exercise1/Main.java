package com.pao.laboratory14.exercise1;

import java.util.*;
import java.util.stream.Collector;

class Bilet {
    private final int id;
    private final String eveniment;
    private final TipBilet tip;
    private final double pret;

    public Bilet(int id, String eveniment, TipBilet tip, double pret) {
        this.id = id;
        this.eveniment = eveniment;
        this.tip = tip;
        this.pret = pret;
    }

    public int getId() { return id; }
    public String getEveniment() { return eveniment; }
    public TipBilet getTip() { return tip; }
    public double getPret() { return pret; }
}

class RaportVanzari {
    private final Map<TipBilet, Long> numarPerTip;
    private final Map<TipBilet, Double> incasariPerTip;
    private final double totalGlobal;
    private final double medieGlobala;
    private final TipBilet tipCelMaiPopular;

    public RaportVanzari(Map<TipBilet, Long> numarPerTip, Map<TipBilet, Double> incasariPerTip,
                         double totalGlobal, double medieGlobala, TipBilet tipCelMaiPopular) {
        this.numarPerTip = Collections.unmodifiableMap(numarPerTip);
        this.incasariPerTip = Collections.unmodifiableMap(incasariPerTip);
        this.totalGlobal = totalGlobal;
        this.medieGlobala = medieGlobala;
        this.tipCelMaiPopular = tipCelMaiPopular;
    }

    public Map<TipBilet, Long> getNumarPerTip() { return numarPerTip; }
    public Map<TipBilet, Double> getIncasariPerTip() { return incasariPerTip; }
    public double getTotalGlobal() { return totalGlobal; }
    public double getMedieGlobala() { return medieGlobala; }
    public TipBilet getTipCelMaiPopular() { return tipCelMaiPopular; }
}

class SalesAccumulator {
    private final Map<TipBilet, Long> counts = new HashMap<>();
    private final Map<TipBilet, Double> sums = new HashMap<>();

    public void accumulate(Bilet bilet) {
        counts.put(bilet.getTip(), counts.getOrDefault(bilet.getTip(), 0L) + 1);
        sums.put(bilet.getTip(), sums.getOrDefault(bilet.getTip(), 0.0) + bilet.getPret());
    }

    public SalesAccumulator combine(SalesAccumulator other) {
        other.counts.forEach((k, v) -> this.counts.put(k, this.counts.getOrDefault(k, 0L) + v));
        other.sums.forEach((k, v) -> this.sums.put(k, this.sums.getOrDefault(k, 0.0) + v));
        return this;
    }

    public RaportVanzari finish() {
        double total = 0.0;
        for (double val : sums.values()) {
            total += val;
        }

        long totalCount = 0;
        for (long c : counts.values()) {
            totalCount += c;
        }

        double medie = totalCount == 0 ? 0.0 : total / totalCount;

        TipBilet popular = null;
        long maxCount = -1;
        for (TipBilet t : TipBilet.values()) {
            long c = counts.getOrDefault(t, 0L);
            if (c > maxCount && c > 0) {
                maxCount = c;
                popular = t;
            }
        }

        return new RaportVanzari(counts, sums, total, medie, popular);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();

        List<Bilet> bilete = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            String eveniment = scanner.next();
            TipBilet tip = TipBilet.valueOf(scanner.next());
            double pret = scanner.nextDouble();
            bilete.add(new Bilet(id, eveniment, tip, pret));
        }

        String comanda = scanner.next();

        Collector<Bilet, ?, RaportVanzari> customCollector = Collector.of(
                SalesAccumulator::new,
                SalesAccumulator::accumulate,
                SalesAccumulator::combine,
                SalesAccumulator::finish
        );

        RaportVanzari raport = bilete.stream().collect(customCollector);

        for (TipBilet t : TipBilet.values()) {
            if (raport.getNumarPerTip().containsKey(t)) {
                long count = raport.getNumarPerTip().get(t);
                double incasari = raport.getIncasariPerTip().getOrDefault(t, 0.0);
                System.out.printf(Locale.US, "%s: count=%d incasari=%.2f RON\n", t, count, incasari);
            }
        }

        if ("RAPORT_COMPLET".equals(comanda)) {
            System.out.println("---");
            System.out.printf(Locale.US, "Total: %.2f RON\n", raport.getTotalGlobal());
            System.out.printf(Locale.US, "Medie: %.2f RON\n", raport.getMedieGlobala());
            System.out.println("Cel mai popular: " + raport.getTipCelMaiPopular());
        }

        scanner.close();
    }
}