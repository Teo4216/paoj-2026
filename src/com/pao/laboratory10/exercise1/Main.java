package com.pao.laboratory10.exercise1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LinkedList<Tranzactie> coada = new LinkedList<>();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String command = scanner.next();

            switch (command) {
                case "ENQUEUE": {
                    int id = scanner.nextInt();
                    double suma = Double.parseDouble(scanner.next());
                    String data = scanner.next();
                    TipTranzactie tip = TipTranzactie.valueOf(scanner.next().toUpperCase());
                    coada.addLast(new Tranzactie(id, suma, data, tip));
                    break;
                }
                case "PUSH": {
                    int id = scanner.nextInt();
                    double suma = Double.parseDouble(scanner.next());
                    String data = scanner.next();
                    TipTranzactie tip = TipTranzactie.valueOf(scanner.next().toUpperCase());
                    coada.addFirst(new Tranzactie(id, suma, data, tip));
                    break;
                }
                case "DEQUEUE": {
                    if (coada.isEmpty()) {
                        System.out.println("Coada goala.");
                    } else {
                        Tranzactie t = coada.removeFirst();
                        System.out.println("Procesat: " + t.toString());
                    }
                    break;
                }
                case "POP": {
                    if (coada.isEmpty()) {
                        System.out.println("Coada goala.");
                    } else {
                        Tranzactie t = coada.removeFirst();
                        System.out.println("Extras: " + t.toString());
                    }
                    break;
                }
                case "REMOVE_DEBIT": {
                    int count = 0;
                    Iterator<Tranzactie> it = coada.iterator();
                    while (it.hasNext()) {
                        Tranzactie t = it.next();
                        if (t.getTip() == TipTranzactie.DEBIT) {
                            it.remove();
                            count++;
                        }
                    }
                    System.out.println("Eliminat " + count + " tranzactii DEBIT.");
                    break;
                }
                case "REMOVE_BELOW": {
                    double threshold = Double.parseDouble(scanner.next());
                    int count = 0;
                    Iterator<Tranzactie> it = coada.iterator();
                    while (it.hasNext()) {
                        Tranzactie t = it.next();
                        if (t.getSuma() < threshold) {
                            it.remove();
                            count++;
                        }
                    }
                    System.out.printf(Locale.US, "Eliminat %d tranzactii sub %.2f RON.%n", count, threshold);
                    break;
                }
                case "PRINT": {
                    for (Tranzactie t : coada) {
                        System.out.println(t.toString());
                    }
                    break;
                }
                case "SIZE": {
                    System.out.println("Dimensiune coada: " + coada.size());
                    break;
                }
            }
        }
        scanner.close();
    }
}