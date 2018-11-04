package br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.serializers;

import flexjson.JSONSerializer;

public class ConsumptionSerializer {

    public static JSONSerializer findForCalculateConsumption, findForRemainingTime;

    static {

        findForCalculateConsumption = new JSONSerializer()
            .include("consumptionAh")
            .exclude("*")
            .prettyPrint(true);

        findForRemainingTime = new JSONSerializer()
                .include(
                        "remainingTime",
                        "batterySufficientForThisConfiguration"
                )
                .exclude("*")
                .prettyPrint(true);

    }

}
