package br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.enums;

/**
 * Coeficients of the found model
 */
public enum Coeficients {

    X1(-8.7347e-04), X2(-5.0349e-04), X3(-9.3939e-04), X4(0.0076), X5(0.2003), X6(0.0194), X7(-0.0404), X8(0.0063),
    K(0.607380397), DURATION(4.0);

    private Double value;

    Coeficients(Double value) {

        this.value = value;

    }

    public Double getValue() {

        return this.value;

    }
}