package net.laboulangerie.api.models;

import java.io.Serializable;

public class NameIdModel implements Serializable {
    protected String name = null;
    protected String id = null;

    public NameIdModel(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
