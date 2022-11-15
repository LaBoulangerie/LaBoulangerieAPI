package net.laboulangerie.api.models;

import java.io.Serializable;
import java.util.UUID;

public class NameUuidModel implements Serializable {
    private String name = null;
    private UUID uuid = null;

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
}
