package br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.enums;

public enum Height {

    _240(240), _480(480), _600(600), _730(730), _768(768), _900(900), _1050(1050), _1080(1080),
    _1440(1440), _2160(2160);

    private Integer value;

    Height(Integer value) {

        this.value = value;

    }

    public Integer getValue() {

        return this.value;

    }

}
