package net.laboulangerie.api.models;

import java.util.UUID;

public class TypedNameUuidModel {
    private String name = null;
    private UUID uuid = null;
    private String type = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
