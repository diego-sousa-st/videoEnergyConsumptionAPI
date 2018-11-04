package br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.services;

import br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.models.Consumption;

public interface ConsumptionService {

    Consumption calculateConsumption(Consumption consumption);

    Consumption calculateRemainingTimeAndIfIsPossibleWatchFullVideo(Consumption consumption);

}
