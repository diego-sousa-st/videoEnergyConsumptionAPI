package br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.enums;

public enum Mbps {

    _4(4), _8(12), _12(12), _16(16), _20(20);

    private Integer value;

    Mbps(Integer value) {

        this.value = value;

    }

    public Integer getValue() {

        return this.value;

    }

}
