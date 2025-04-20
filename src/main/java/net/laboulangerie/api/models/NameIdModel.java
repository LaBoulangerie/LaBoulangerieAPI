package net.laboulangerie.api.models;

import java.io.Serializable;

public class NameIdModel<T> implements Serializable {
    private String name = null;
    private T id = null;

    public NameIdModel(String name, T id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public T getId() {
        return id;
    }
}
