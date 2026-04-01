package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public abstract class Colaborator implements IOperatiiCitireScriere, Comparable<Colaborator> {
    protected String nume;
    protected String prenume;
    protected double venitBrutLunar;

    public Colaborator() {}

    public abstract double calculeazaVenitNetAnual();

    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
    }

    @Override
    public int compareTo(Colaborator altul) {
        return Double.compare(altul.calculeazaVenitNetAnual(), this.calculeazaVenitNetAnual());
    }

    public String getNumeComplet() {
        return nume + " " + prenume;
    }
    public TipColaborator getTip() {
        return TipColaborator.valueOf(this.tipContract());
    }
}