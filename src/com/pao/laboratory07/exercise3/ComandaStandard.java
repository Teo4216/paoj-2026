package com.pao.laboratory07.exercise3;

public final class ComandaStandard extends Comanda {
    public ComandaStandard(String nume, double pret, String client) {
        super(nume, pret, client);
    }

    @Override
    public double pretFinal() { return pretInitial; }

    @Override
    public String descriere() {
        return String.format("STANDARD: %s, pret: %.2f lei [%s] - client: %s",
                nume, pretFinal(), stare.name(), client);
    }
}