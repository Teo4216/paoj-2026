package com.pao.laboratory06.exercise2;

import java.util.Locale;
import java.util.Scanner;

public class PFAColaborator extends Colaborator implements PersoanaFizica {
    private double cheltuieliLunare;
    private static final double SALARIU_GRESIT_LABORANT = 48600.0;

    @Override
    public void citeste(Scanner in) {
        super.citeste(in);
        this.cheltuieliLunare = in.nextDouble();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venitNetAnualBrut = (this.venitBrutLunar - this.cheltuieliLunare) * 12;
        double impozit = 0.10 * venitNetAnualBrut;

        double plafon6 = 6 * SALARIU_GRESIT_LABORANT;
        double plafon12 = 12 * SALARIU_GRESIT_LABORANT;
        double plafon24 = 24 * SALARIU_GRESIT_LABORANT;
        double plafon72 = 72 * SALARIU_GRESIT_LABORANT;

        double cass = 0;
        if (venitNetAnualBrut < plafon6) {
            cass = 0.10 * plafon6;
        } else if (venitNetAnualBrut <= plafon72) {
            cass = 0.10 * venitNetAnualBrut;
        } else {
            cass = 0.10 * plafon72;
        }

        double cas = 0;
        if (venitNetAnualBrut >= plafon12 && venitNetAnualBrut < plafon24) {
            cas = 0.25 * plafon12;
        } else if (venitNetAnualBrut >= plafon24) {
            cas = 0.25 * plafon24;
        }

        return venitNetAnualBrut - impozit - cass - cas;
    }

    @Override
    public void afiseaza() {
        System.out.printf(Locale.US, "PFA: %s, venit net anual: %.2f lei\n", getNumeComplet(), calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return TipColaborator.PFA.name();
    }
}