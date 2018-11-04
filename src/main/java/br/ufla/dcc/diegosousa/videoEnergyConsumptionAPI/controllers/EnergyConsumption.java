package br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.controllers;

import br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.models.Consumption;
import br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.results.Json;
import br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.serializers.ConsumptionSerializer;
import br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.services.ConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnergyConsumption extends BaseController {

    @Autowired
    private ConsumptionService consumptionService;

    @PostMapping("consumption/Ah")
    public String calculateConsumption(@RequestBody Consumption consumption) {

        return renderJSON(this.consumptionService.calculateConsumption(consumption), ConsumptionSerializer.findForCalculateConsumption);

    }

}
