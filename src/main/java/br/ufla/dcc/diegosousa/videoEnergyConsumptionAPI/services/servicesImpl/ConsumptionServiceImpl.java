package br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.services.servicesImpl;

import br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.enums.Coeficients;
import br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.models.Consumption;
import br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.services.ConsumptionService;
import org.springframework.stereotype.Service;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {

    public Consumption calculateConsumption(Consumption consumption) {

        Double ah = (
                consumption.getDuration()*Coeficients.K.getValue()*Math.exp(
                        Coeficients.X1.getValue()*consumption.getSpacialInformation()+
                                Coeficients.X2.getValue()*consumption.getStandardDeviationSpacialInformation()+
                                Coeficients.X3.getValue()*consumption.getTemporalInformation()+
                                Coeficients.X4.getValue()*consumption.getStandardDeviationTemporalInformation()+
                                Coeficients.X5.getValue()*consumption.getMbps()+
                                Coeficients.X6.getValue()*consumption.getWidht()+
                                Coeficients.X7.getValue()*consumption.getHeight()+
                                Coeficients.X8.getValue()*consumption.getFps()
                )
        )/Coeficients.DURATION.getValue();

        return new Consumption(ah);

    }

    @Override
    public Consumption calculateRemainingTimeAndIfIsPossibleWatchFullVideo(Consumption consumption) {

        Double ahConsumed = this.calculateConsumption(consumption).getConsumptionAh();

        Consumption consumptionResult = new Consumption();

        if(ahConsumed > consumption.getBatteryAhActual()) {

            consumptionResult.setBatterySufficientForThisConfiguration(false);
            consumptionResult.setRemainingTime(this.calculateRemainingTime(consumption, ahConsumed));

        } else {

            consumptionResult.setBatterySufficientForThisConfiguration(true);
            consumptionResult.setRemainingTime(consumption.getDuration());

        }

        return consumptionResult;

    }

    private Double calculateRemainingTime(Consumption consumption, Double ahConsumed) {

        return (consumption.getDuration()*consumption.getBatteryAhActual())/ahConsumed;

    }

}
