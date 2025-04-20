package net.laboulangerie.api.models;

import java.io.Serializable;
import java.util.HashMap;

public class EventModel implements Serializable {

    private String event = null;
    private HashMap<String, NameIdModel<?>> data = new HashMap<>();

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public HashMap<String, NameIdModel<?>> getData() {
        return data;
    }

    public void put(String string, NameIdModel<?> model) {
        this.data.put(string, model);
    }

    public void setData(HashMap<String, NameIdModel<?>> data) {
        this.data = data;
    }
}
