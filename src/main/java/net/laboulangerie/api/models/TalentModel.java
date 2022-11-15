package net.laboulangerie.api.models;

import java.io.Serializable;

public class TalentModel implements Serializable {
    private String name = null;
    private int level = 0;
    private double xp = 0;
    private double xpToNextLevel = 0;
    private double minLevelXp = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getXp() {
        return xp;
    }

    public void setXp(double xp) {
        this.xp = xp;
    }

    public double getXpToNextLevel() {
        return xpToNextLevel;
    }

    public void setXpToNextLevel(double xpToNextLevel) {
        this.xpToNextLevel = xpToNextLevel;
    }

    public double getMinLevelXp() {
        return minLevelXp;
    }

    public void setMinLevelXp(double minLevelXp) {
        this.minLevelXp = minLevelXp;
    }
}
