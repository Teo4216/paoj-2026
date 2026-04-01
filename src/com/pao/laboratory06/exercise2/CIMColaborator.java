package com.pao.laboratory06.exercise2;

import java.util.Locale;
import java.util.Scanner;
public class CIMColaborator extends Colaborator implements PersoanaFizica {
    private boolean bonus = false;

    @Override
    public void citeste(Scanner in) {
        super.citeste(in);
        String restLinie = in.nextLine().trim();
        this.bonus = restLinie.equals("DA");
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double netAnual = this.venitBrutLunar * 12 * 0.55;
        if (this.bonus) {
            netAnual += netAnual * 0.10;
        }
        return netAnual;
    }

    @Override
    public void afiseaza() {
        System.out.printf(Locale.US, "CIM: %s, venit net anual: %.2f lei\n", getNumeComplet(), calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return TipColaborator.CIM.name();
    }
    @Override
    public boolean areBonus() {
        return this.bonus;
    }
}