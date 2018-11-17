package br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.services.servicesImpl;

import br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.enums.*;
import br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.models.Consumption;
import br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.services.ConsumptionService;
import org.springframework.stereotype.Service;
import gurobi.*;

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
                                Coeficients.X6.getValue()*consumption.getWidth()+
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
            this.calculateBestConfiguration(consumption, consumptionResult);

        } else {

            consumptionResult.setBatterySufficientForThisConfiguration(true);
            consumptionResult.setRemainingTime(consumption.getDuration());

        }

        return consumptionResult;

    }

    private void calculateBestConfiguration(Consumption consumption, Consumption consumptionResult) {

        try {

            /**
             * this variables must not be null or exceptions might be throw
             */
            System.out.println(System.getProperty("java.library.path"));
            System.out.println(System.getenv("GUROBI_HOME"));
            System.out.println(System.getenv("PATH"));
            System.out.println(System.getenv("LD_LIBRARY_PATH"));

            GRBEnv env = new GRBEnv("consumptionEnergy.log");
            GRBModel model = new GRBModel(env);

            // Create variables

            GRBVar mbps = model.addVar(0.0, GRB.INFINITY, 0.0, GRB.INTEGER, "mbps");
            GRBVar width = model.addVar(0.0, GRB.INFINITY, 0.0, GRB.INTEGER, "width");
            GRBVar height = model.addVar(0.0, GRB.INFINITY, 0.0, GRB.INTEGER, "height");
            GRBVar fps = model.addVar(0.0, GRB.INFINITY, 0.0, GRB.INTEGER, "fps");

            int sizeY = Width.values().length;
            GRBVar y[] = new GRBVar[sizeY];

            for (int i = 0; i < sizeY; i++) {

                y[i] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "y"+i+1);

            }

            int sizeZ = Height.values().length;
            GRBVar z[] = new GRBVar[sizeZ];

            for (int i = 0; i < sizeZ; i++) {

                z[i] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "z"+i+1);

            }

            int sizeW = Mbps.values().length;
            GRBVar w[] = new GRBVar[sizeW];

            for (int i = 0; i < sizeW; i++) {

                w[i] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "w"+i+1);

            }

            int sizeV = Fps.values().length;
            GRBVar v[] = new GRBVar[sizeV];

            for (int i = 0; i < sizeV; i++) {

                v[i] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "v"+i+1);

            }

            // Set objective: maximize ln(duration) + u - ln(4)
            // u = x5mbps + x6widht + x7height + x8fps

            GRBLinExpr expr = new GRBLinExpr();

            expr.addConstant(Math.log(consumption.getDuration())-Math.log(Coeficients.DURATION.getValue()));

            expr.addTerm(Coeficients.X5.getValue(), mbps);
            expr.addTerm(Coeficients.X6.getValue(), width);
            expr.addTerm(Coeficients.X7.getValue(), height);
            expr.addTerm(Coeficients.X8.getValue(), fps);

            model.setObjective(expr, GRB.MAXIMIZE);

            // Add constraint: 640y1 + ... + 3840y13 = width

            expr = new GRBLinExpr();

            Width[] widths = Width.values();

            for (int i = 0; i < y.length; i++) {

                Width widthAtual = widths[i];

                expr.addTerm(widthAtual.getValue(), y[i]);

            }

            model.addConstr(expr, GRB.EQUAL, width, "widthRestriction");

            // Add constraint y1 + ... + y13 = 1

            expr = new GRBLinExpr();

            for (GRBVar aY : y) {

                expr.addTerm(1.0, aY);

            }

            model.addConstr(expr, GRB.EQUAL, 1, "c0");

            // Add constraint: 240z1 + ... + 2160z10 = height

            expr = new GRBLinExpr();

            Height[] heights = Height.values();

            for (int i = 0; i < z.length; i++) {

                Height heightAtual = heights[i];

                expr.addTerm(heightAtual.getValue(), z[i]);

            }

            model.addConstr(expr, GRB.EQUAL, height, "heightRestriction");

            // Add constraint z1 + ... + z10 = 1

            expr = new GRBLinExpr();

            for (GRBVar aZ : z) {

                expr.addTerm(1.0, aZ);

            }

            model.addConstr(expr, GRB.EQUAL, 1, "c1");

            // Add constraint: 4w1 + ... + 20w5 = mbps

            expr = new GRBLinExpr();

            Mbps[] mbpsArray = Mbps.values();

            for (int i = 0; i < w.length; i++) {

                Mbps mbpsAtual = mbpsArray[i];

                expr.addTerm(mbpsAtual.getValue(), w[i]);

            }

            model.addConstr(expr, GRB.EQUAL, mbps, "mbpsRestriction");

            // Add constraint w1 + ... + w5 = 1

            expr = new GRBLinExpr();

            for (GRBVar aW : w) {

                expr.addTerm(1.0, aW);

            }

            model.addConstr(expr, GRB.EQUAL, 1, "c2");

            // Add constraint: 4w1 + ... + 20w5 = mbps

            expr = new GRBLinExpr();

            Fps[] fpsArray = Fps.values();

            for (int i = 0; i < v.length; i++) {

                Fps fpsAtual = fpsArray[i];

                expr.addTerm(fpsAtual.getValue(), v[i]);

            }

            model.addConstr(expr, GRB.EQUAL, fps, "fpsRestriction");

            // Add constraint v1 + ... + v5 = 1

            expr = new GRBLinExpr();

            for (GRBVar aV : v) {

                expr.addTerm(1.0, aV);

            }

            model.addConstr(expr, GRB.EQUAL, 1, "c3");

            // Add constraint objective function < Ah

            expr = new GRBLinExpr();

            expr.addConstant(Math.log(consumption.getDuration())-Math.log(Coeficients.DURATION.getValue()));

            expr.addTerm(Coeficients.X5.getValue(), mbps);
            expr.addTerm(Coeficients.X6.getValue(), width);
            expr.addTerm(Coeficients.X7.getValue(), height);
            expr.addTerm(Coeficients.X8.getValue(), fps);

            model.addConstr(expr, GRB.LESS_EQUAL, Math.log(consumption.getBatteryAhActual()), "c4");


            // Add constraint width <= 2 * height

            expr = new GRBLinExpr();

            expr.addTerm(0.5, width);
            model.addConstr(expr, GRB.LESS_EQUAL, height, "c5");

            // Add constraint fps >= fps/2

            expr = new GRBLinExpr();

            expr.addTerm(1, fps);

            // Optimize model

            System.out.println(model.toString());

            model.write("resultado.lp");

            model.optimize();

            System.out.println("width: "+width.get(GRB.DoubleAttr.X));
            System.out.println("height: "+height.get(GRB.DoubleAttr.X));
            System.out.println("fps: "+fps.get(GRB.DoubleAttr.X));
            System.out.println("mbps: "+mbps.get(GRB.DoubleAttr.X));


            System.out.println("Obj: " + model.get(GRB.DoubleAttr.ObjVal));

            Consumption recommendedConfiguration = new Consumption(width.get(GRB.DoubleAttr.X),
                    height.get(GRB.DoubleAttr.X), mbps.get(GRB.DoubleAttr.X), fps.get(GRB.DoubleAttr.X));

            consumptionResult.setRecommendedConfiguration(recommendedConfiguration);

            // Dispose of model and environment

            model.dispose();
            env.dispose();

        } catch (GRBException e) {

            System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());

        }

    }

    private Double calculateRemainingTime(Consumption consumption, Double ahConsumed) {

        return (consumption.getDuration()*consumption.getBatteryAhActual())/ahConsumed;

    }

}
