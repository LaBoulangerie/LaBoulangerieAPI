package net.laboulangerie.api.models;

import java.io.Serializable;

public class TownBlockModel implements Serializable {
    private int x = 0;
    private int z = 0;
    private String type = null;
    private String world = null;

    public TownBlockModel() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }
}
