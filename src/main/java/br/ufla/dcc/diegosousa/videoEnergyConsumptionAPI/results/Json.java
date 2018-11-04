package br.ufla.dcc.diegosousa.videoEnergyConsumptionAPI.results;

import flexjson.JSONSerializer;

public class Json {

    private Object data;
    private JSONSerializer jsonSerializer;

    public Json() {

    }

    public Json(Object data) {

        this.data = data;

    }

    public Json(Object data, JSONSerializer jsonSerializer) {

        this.data = data;
        this.jsonSerializer = jsonSerializer;

    }

    public Object getData() {

        return data;

    }

    public void setData(Object data) {

        this.data = data;

    }

    public JSONSerializer getJsonSerializer() {

        return jsonSerializer;

    }

    public void setJsonSerializer(JSONSerializer jsonSerializer) {

        this.jsonSerializer = jsonSerializer;

    }

}
