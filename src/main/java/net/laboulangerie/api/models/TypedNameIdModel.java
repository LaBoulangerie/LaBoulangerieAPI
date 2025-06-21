package net.laboulangerie.api.models;

import java.io.Serializable;

public class TypedNameIdModel implements Serializable {
    protected String name = null;
    protected String id = null;
    protected String type = null;

    public TypedNameIdModel(String name, String id, String type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
