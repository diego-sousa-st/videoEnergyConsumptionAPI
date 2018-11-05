package br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.enums;

public enum Width {

    _320(320), _640(640), _720(720), _800(800), _1024(1024), _1280(1280), _1366(1366), _1440(1440),
    _1600(1600), _1680(1680), _1920(1920), _2560(2560), _3840(3840);

    private Integer value;

    Width(Integer value) {

        this.value = value;

    }

    public Integer getValue() {

        return this.value;

    }

}
