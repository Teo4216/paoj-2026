package com.pao.laboratory14.exercise2;

import com.pao.laboratory14.exercise1.TipBilet;
import com.pao.laboratory14.exercise2.model.Eveniment;
import com.pao.laboratory14.exercise2.repository.EvenimentRepository;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EvenimentRepository repo = new EvenimentRepository();

        try {
            repo.initSchema();
        } catch (Exception e) {
            System.err.println("Eroare initializare schema: " + e.getMessage());
            return;
        }

        while (scanner.hasNext()) {
            String comanda = scanner.next();

            try {
                switch (comanda) {
                    case "ADD":
                        String nume = scanner.next();
                        String data = scanner.next();
                        int capacitate = scanner.nextInt();
                        TipBilet tip = TipBilet.valueOf(scanner.next());

                        Eveniment ev = new Eveniment(nume, data, capacitate, tip);
                        repo.save(ev);
                        System.out.println("Adaugat: [" + ev.getId() + "] " + ev.getNume());
                        break;

                    case "LIST":
                        List<Eveniment> evenimente = repo.findAll();
                        for (Eveniment e : evenimente) {
                            System.out.println("[" + e.getId() + "] " + e.getNume() + " | " + e.getData() +
                                    " | cap=" + e.getCapacitate() + " | " + e.getTip());
                        }
                        break;

                    case "DELETE":
                        int idStergere = scanner.nextInt();
                        int randuriAfectate = repo.deleteImpl(idStergere);
                        if (randuriAfectate > 0) {
                            System.out.println("Sters: " + idStergere);
                        } else {
                            System.out.println("Nu exista: " + idStergere);
                        }
                        break;

                    case "COUNT":
                        int total = repo.count();
                        System.out.println("Total: " + total);
                        break;
                }
            } catch (Exception ex) {
                System.err.println("Eroare la procesarea comenzii " + comanda + ": " + ex.getMessage());
            }
        }
        scanner.close();
    }
}