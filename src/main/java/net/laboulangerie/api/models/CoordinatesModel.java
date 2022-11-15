package net.laboulangerie.api.models;

import java.io.Serializable;

public class CoordinatesModel implements Serializable {
    private double x = 0;
    private double y = 0;
    private double z = 0;
    private String type = null;
    private String world = null;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
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
