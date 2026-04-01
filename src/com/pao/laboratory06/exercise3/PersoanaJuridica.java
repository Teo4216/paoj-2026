package com.pao.laboratory06.exercise3;

import java.util.ArrayList;
import java.util.List;

public class PersoanaJuridica extends Persoana implements PlataOnlineSMS {
    private List<String> smsTrimise;
    private double soldFirma;

    public PersoanaJuridica(String nume, String prenume, String telefon, double soldInitial) {
        super(nume, prenume, telefon);
        this.smsTrimise = new ArrayList<>();
        this.soldFirma = soldInitial;
    }
    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.trim().isEmpty() || parola == null || parola.trim().isEmpty()) {
            throw new IllegalArgumentException("Datele de autentificare sunt invalide!");
        }
        System.out.println("Firma " + nume + " s-a autentificat în platformă.");
    }

    @Override
    public double consultareSold() {
        return soldFirma;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        if (suma <= 0 || suma > soldFirma) return false;
        soldFirma -= suma;
        return true;
    }
    @Override
    public boolean trimiteSMS(String mesaj) {
        if (telefon == null || telefon.trim().isEmpty()) {
            return false;
        }
        if (mesaj == null || mesaj.trim().isEmpty()) {
            return false;
        }

        smsTrimise.add(mesaj);
        return true;
    }

    public List<String> getSmsTrimise() {
        return smsTrimise;
    }
}