package net.laboulangerie.api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NationModel implements Serializable {
    private String name = null;
    private String ulid = null;
    private NameIdModel king = null;
    private NameIdModel capital = null;
    private int balance = 0;
    private String color = null;
    private List<NameIdModel> lands = new ArrayList<>();
    private List<NameIdModel> enemies = new ArrayList<>();
    private List<NameIdModel> allies = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUlid() {
        return ulid;
    }

    public void setUlid(String ulid) {
        this.ulid = ulid;
    }

    public NameIdModel getKing() {
        return king;
    }

    public void setKing(NameIdModel king) {
        this.king = king;
    }

    public NameIdModel getCapital() {
        return capital;
    }

    public void setCapital(NameIdModel capital) {
        this.capital = capital;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<NameIdModel> getLands() {
        return lands;
    }

    public void setLands(List<NameIdModel> lands) {
        this.lands = lands;
    }

    public List<NameIdModel> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<NameIdModel> enemies) {
        this.enemies = enemies;
    }

    public List<NameIdModel> getAllies() {
        return allies;
    }

    public void setAllies(List<NameIdModel> allies) {
        this.allies = allies;
    }

}
