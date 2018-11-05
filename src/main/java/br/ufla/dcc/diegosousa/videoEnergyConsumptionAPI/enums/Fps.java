package br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.enums;

public enum Fps {

    _15(15), _24(24), _30(30), _45(45), _60(60);

    private Integer value;

    Fps(Integer value) {

        this.value = value;

    }

    public Integer getValue() {

        return this.value;

    }

}
