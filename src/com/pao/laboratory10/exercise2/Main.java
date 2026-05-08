package com.pao.laboratory10.exercise2;

import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();

        List<Tranzactie> lista = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            double suma = Double.parseDouble(scanner.next());
            String data = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next().toUpperCase());
            lista.add(new Tranzactie(id, suma, data, tip));
        }

        while (scanner.hasNext()) {
            String command = scanner.next();

            switch (command) {
                case "UNIQUE_IDS": {
                    LinkedHashSet<Integer> ids = new LinkedHashSet<>();
                    for (Tranzactie t : lista) {
                        ids.add(t.getId());
                    }
                    System.out.println("IDs unice (" + ids.size() + "): " + ids.toString());
                    break;
                }
                case "MONTHLY_REPORT": {
                    TreeMap<String, double[]> report = new TreeMap<>();

                    for (Tranzactie t : lista) {
                        String luna = t.getData().substring(0, 7);
                        report.putIfAbsent(luna, new double[]{0.0, 0.0});

                        if (t.getTip() == TipTranzactie.CREDIT) {
                            report.get(luna)[0] += t.getSuma();
                        } else {
                            report.get(luna)[1] += t.getSuma();
                        }
                    }

                    for (Map.Entry<String, double[]> entry : report.entrySet()) {
                        System.out.printf(Locale.US, "%s: CREDIT %.2f RON, DEBIT %.2f RON%n",
                                entry.getKey(), entry.getValue()[0], entry.getValue()[1]);
                    }
                    break;
                }
                case "TOP": {
                    int topN = scanner.nextInt();
                    List<Tranzactie> copie = new ArrayList<>(lista);
                    copie.sort((t1, t2) -> Double.compare(t2.getSuma(), t1.getSuma())); // Descrescător

                    System.out.println("Top " + topN + ":");
                    for (int i = 0; i < Math.min(topN, copie.size()); i++) {
                        System.out.println(copie.get(i));
                    }
                    break;
                }
                case "SORT_ASC": {
                    lista.sort((t1, t2) -> Double.compare(t1.getSuma(), t2.getSuma()));
                    for (Tranzactie t : lista) System.out.println(t);
                    break;
                }
                case "SORT_DESC": {
                    lista.sort((t1, t2) -> Double.compare(t2.getSuma(), t1.getSuma()));
                    for (Tranzactie t : lista) System.out.println(t);
                    break;
                }
                case "REVERSE": {
                    Collections.reverse(lista);
                    for (Tranzactie t : lista) System.out.println(t);
                    break;
                }
                case "MIN_MAX": {
                    Comparator<Tranzactie> comp = Comparator.comparingDouble(Tranzactie::getSuma);
                    Tranzactie min = Collections.min(lista, comp);
                    Tranzactie max = Collections.max(lista, comp);
                    System.out.println("MIN: " + min);
                    System.out.println("MAX: " + max);
                    break;
                }
                case "CME_DEMO": {
                    try {
                        for (Tranzactie t : lista) {
                            lista.remove(t);
                        }
                    } catch (ConcurrentModificationException e) {
                        System.out.println("ConcurrentModificationException prins: modificare in iteratie detectata.");
                    }
                    break;
                }
            }
        }
        scanner.close();
    }
}