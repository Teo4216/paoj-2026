package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

enum TipTranzactie {
    CREDIT, DEBIT
}

class Tranzactie implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private double suma;
    private String data;
    private String contSursa;
    private String contDestinatie;
    private TipTranzactie tip;

    private transient String note;

    public Tranzactie(int id, double suma, String data, String contSursa, String contDestinatie, TipTranzactie tip) {
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.contSursa = contSursa;
        this.contDestinatie = contDestinatie;
        this.tip = tip;
    }

    public int getId() { return id; }
    public String getData() { return data; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    @Override
    public String toString() {
        return String.format(Locale.US, "[%d] %s %s: %.2f RON | %s -> %s",
                id, data, tip, suma, contSursa, contDestinatie);
    }
}

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextLine()) return;
        String lineN = scanner.nextLine().trim();
        if (lineN.isEmpty()) return;
        int n = Integer.parseInt(lineN);

        List<Tranzactie> listaOriginala = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String[] p = scanner.nextLine().trim().split("\\s+");
            int id = Integer.parseInt(p[0]);
            double suma = Double.parseDouble(p[1]);
            String data = p[2];
            String contSursa = p[3];
            String contDestinatie = p[4];
            TipTranzactie tip = TipTranzactie.valueOf(p[5].toUpperCase());

            Tranzactie tx = new Tranzactie(id, suma, data, contSursa, contDestinatie, tip);
            tx.setNote("procesat");
            listaOriginala.add(tx);
        }

        File outFile = new File(OUTPUT_FILE);
        outFile.getParentFile().mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outFile))) {
            oos.writeObject(listaOriginala);
        }

        List<Tranzactie> listaDeserializata;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(outFile))) {
            listaDeserializata = (List<Tranzactie>) ois.readObject();
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] cmd = line.split("\\s+");
            String op = cmd[0].toUpperCase();

            switch (op) {
                case "LIST":
                    for (Tranzactie tx : listaDeserializata) {
                        System.out.println(tx);
                    }
                    break;

                case "FILTER":
                    String luna = cmd[1];
                    boolean found = false;
                    for (Tranzactie tx : listaDeserializata) {
                        if (tx.getData().startsWith(luna)) {
                            System.out.println(tx);
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("Niciun rezultat.");
                    }
                    break;

                case "NOTE":
                    int idCautat = Integer.parseInt(cmd[1]);
                    Tranzactie txGasit = null;
                    for (Tranzactie tx : listaDeserializata) {
                        if (tx.getId() == idCautat) {
                            txGasit = tx;
                            break;
                        }
                    }
                    if (txGasit != null) {
                        System.out.println("NOTE[" + idCautat + "]: " + txGasit.getNote());
                    } else {
                        System.out.println("NOTE[" + idCautat + "]: not found");
                    }
                    break;
            }
        }
        scanner.close();
    }
}