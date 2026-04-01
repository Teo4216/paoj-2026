package com.pao.laboratory06.exercise3;

public class Inginer extends Angajat implements PlataOnline, Comparable<Inginer> {

    public Inginer(String nume, String prenume, String telefon, double salariu) {
        super(nume, prenume, telefon, salariu);
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.trim().isEmpty() || parola == null || parola.trim().isEmpty()) {
            throw new IllegalArgumentException("User-ul și parola nu pot fi goale sau null!");
        }
        System.out.println("Inginer " + nume + " a fost autentificat cu succes.");
    }

    @Override
    public double consultareSold() {
        return salariu;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        if (suma <= 0 || suma > salariu) {
            return false;
        }
        salariu -= suma;
        return true;
    }
    @Override
    public int compareTo(Inginer altul) {
        return this.nume.compareTo(altul.nume);
    }

    @Override
    public String toString() {
        return "Inginer: " + nume + " " + prenume + ", Salariu: " + salariu;
    }
}