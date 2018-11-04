package br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.controllers;

import br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.results.Json;
import flexjson.JSONSerializer;
import org.json.JSONObject;

public abstract class BaseController {

    /**
     * Render a 200 OK application/json response.
     *
     * @param model
     *            The Java object to serialize
     *
     */
    protected static Json renderJSON(Object model) {

        return new Json(model);

    }

    /**
     * Render a 200 OK application/json response.
     *
     * @param model
     *            The Java object to serialize
     * @param jsonSerializer
     *            A Flexjson serializers to use
     *
     */
    protected static String renderJSON(Object model, JSONSerializer jsonSerializer) {

        return jsonSerializer.serialize(model);

    }

}

