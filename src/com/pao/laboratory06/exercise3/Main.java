package com.pao.laboratory06.exercise3;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Demonstratie platforma de plati\n");

        System.out.println("--- 1.Constante financiare ---");
        System.out.println("TVA-ul curent este: " + (ConstanteFinanciare.TVA.getValoare() * 100) + "%");
        System.out.println("Salariul minim pe economie: " + ConstanteFinanciare.SALARIU_MINIM.getValoare() + " lei\n");

        System.out.println("--- 2.Sortare Ingineri ---");
        Inginer ing1 = new Inginer("Popescu", "Ion", "0722111222", 5000);
        Inginer ing2 = new Inginer("Avram", "Gelu", "0744111222", 6000);
        Inginer ing3 = new Inginer("Zaharia", "Vasile", "0733111222", 4500);
        Inginer[] ingineri = {ing1, ing2, ing3};

        System.out.println("a) Sortare Naturala (Alfabetic dupa nume):");
        Arrays.sort(ingineri);
        for (Inginer i : ingineri) System.out.println(i);

        System.out.println("\nb) Sortare Alternativa(Descrescator dupa Salariu):");
        Arrays.sort(ingineri, new ComparatorInginerSalariu());
        for (Inginer i : ingineri) System.out.println(i);

        System.out.println("\n--- 3. Acces prin referință PlataOnline ---");
        PlataOnline plataInginer = ing1;
        plataInginer.autentificare("ion.popescu", "parola123");
        System.out.println("Sold inițial: " + plataInginer.consultareSold());
        plataInginer.efectuarePlata(1500);
        System.out.println("Sold după plata de 1500: " + plataInginer.consultareSold());
        System.out.println("\n--- 4. Persoana Juridica si SMS ---");
        PlataOnlineSMS firma = new PersoanaJuridica("Tech SRL", "", "0799888777", 10000);
        firma.autentificare("admin", "1234");
        firma.trimiteSMS("Plata facturii #123 a fost procesată.");
        firma.trimiteSMS("Abonament lunar reînnoit.");

        if (firma instanceof PersoanaJuridica) {
            List<String> istoric = ((PersoanaJuridica) firma).getSmsTrimise();
            System.out.println("Istoric SMS firmă: " + istoric);
        }

        System.out.println("\n--- 5. Edge Cases & Excepții ---");

        System.out.println("Cazul A: Trimitere SMS entitate fara telefon valid:");
        PlataOnlineSMS firmaFaraTelefon = new PersoanaJuridica("Ghost SRL", "", null, 5000);
        boolean trimis = firmaFaraTelefon.trimiteSMS("Test SMS");
        System.out.println("SMS trimis cu succes? " + trimis + " (așteptat: false)");

        System.out.println("\nCazul B: Autentificare cu user null/gol:");
        try {
            firma.autentificare("", "parola");
        } catch (IllegalArgumentException e) {
            System.out.println("Eroare : " + e.getMessage());
        }

        System.out.println("\nCazul C: Apelare SMS pe entitate fără capabilitate (Inginer):");
        try {
            proceseazaPlataCuNotificare(plataInginer, "Notificare importantă");
        } catch (UnsupportedOperationException e) {
            System.out.println("Eroare prinsă corect: " + e.getMessage());
        }
    }
    public static void proceseazaPlataCuNotificare(PlataOnline entitate, String mesaj) {
        if (entitate instanceof PlataOnlineSMS) {
            ((PlataOnlineSMS) entitate).trimiteSMS(mesaj);
        } else {
            throw new UnsupportedOperationException("Entitatea nu are capabilitate SMS!");
        }
    }
}