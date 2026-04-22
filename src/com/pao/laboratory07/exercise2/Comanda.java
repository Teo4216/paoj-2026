package com.pao.laboratory07.exercise2;

import com.pao.laboratory07.exercise1.OrderState;

public abstract sealed class Comanda permits ComandaStandard, ComandaRedusa, ComandaGratuita {
    protected String nume;
    protected double pretInitial;
    protected OrderState stare;

    public Comanda(String nume, double pretInitial) {
        this.nume = nume;
        this.pretInitial = pretInitial;
        this.stare = OrderState.PLACED;
    }

    public abstract double pretFinal();
    public abstract String descriere();
}