package br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Consumption {

    /**
     * Duration of the video, given in minutes (d)
     */
    private Double duration;
    /**
     * Mean of spacial information of the video (SI)
     */
    private Double spacialInformation;
    /**
     * Standard deviation of spacial information of the video (SDSI)
     */
    private Double standardDeviationSpacialInformation;
    /**
     * Mean of temporal information of the video (TI)
     */
    private Double temporalInformation;
    /**
     * Standard deviation of temporal information of the video (SDTI)
     */
    private Double standardDeviationTemporalInformation;
    /**
     * Mpbs of the video
     */
    private Double mbps;
    /**
     * Width of the video frame
     */
    private Double widht;
    /**
     * Heigth of the video frame
     */
    private Double height;
    /**
     * Fps of the video
     */
    private Double fps;


    /**
     * Remaining life time of the battery
     */
    private Double remainingTime;

    /**
     * True if battery is suficiente to watch all the video with the specified parameters
     */
    private Boolean batterySufficientForThisConfiguration;
    /**
     * Value of the consumption of the video with the specified parameters
     */
    private Double consumptionAh;


    /**
     * Battery Ah value on the moment that the webservie was called
     */
    private Double batteryAhActual;

    public Consumption(Double duration, Double spacialInformation, Double standardDeviationSpacialInformation,
                       Double temporalInformation, Double standardDeviationTemporalInformation, Double mbps,
                       Double widht, Double height, Double fps) {

        this.duration = duration;
        this.spacialInformation = spacialInformation;
        this.standardDeviationSpacialInformation = standardDeviationSpacialInformation;
        this.temporalInformation = temporalInformation;
        this.standardDeviationTemporalInformation = standardDeviationTemporalInformation;
        this.mbps = mbps;
        this.widht = widht;
        this.height = height;
        this.fps = fps;

    }

    public Consumption(Double consumptionAh) {

        this.consumptionAh = consumptionAh;

    }

    public Consumption() {

        super();

    }

}
