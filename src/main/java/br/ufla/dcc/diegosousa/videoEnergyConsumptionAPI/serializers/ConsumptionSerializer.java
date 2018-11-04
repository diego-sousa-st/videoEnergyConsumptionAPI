package br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.serializers;

import flexjson.JSONSerializer;

public class ConsumptionSerializer {

    public static JSONSerializer findForCalculateConsumption;

    static {

        findForCalculateConsumption = new JSONSerializer()
            .include("consumptionAh")
            .exclude("*")
            .prettyPrint(true);

    }

}
