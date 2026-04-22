package com.pao.laboratory07.exercise2;

public final class ComandaStandard extends Comanda {
    public ComandaStandard(String nume, double pret) {
        super(nume, pret);
    }

    @Override
    public double pretFinal() {
        return pretInitial;
    }

    @Override
    public String descriere() {
        return String.format("STANDARD: %s, pret: %.2f lei [%s]",
                nume, pretFinal(), stare.name());
    }
}