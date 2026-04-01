package com.pao.laboratory06.exercise2;

import java.util.Locale;
import java.util.Scanner;

public class SRLColaborator extends Colaborator implements PersoanaJuridica {
    private double cheltuieliLunare;

    @Override
    public void citeste(Scanner in) {
        super.citeste(in);
        this.cheltuieliLunare = in.nextDouble();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        return (this.venitBrutLunar - this.cheltuieliLunare) * 12 * 0.84;
    }

    @Override
    public void afiseaza() {
        System.out.printf(Locale.US, "SRL: %s, venit net anual: %.2f lei\n", getNumeComplet(), calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return TipColaborator.SRL.name();
    }
}