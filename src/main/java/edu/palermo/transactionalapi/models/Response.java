package edu.palermo.transactionalapi.models;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private Map<String, Object> data;

    public Response() {
        data=new HashMap<>();
    }

    public void putItem(String idField, Object value){
        data.put(idField, value);
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
