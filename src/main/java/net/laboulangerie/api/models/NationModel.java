package net.laboulangerie.api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.angeschossen.lands.api.applicationframework.util.ULID;

public class NationModel implements Serializable {
    private String name = null;
    private ULID ulid = null;

    private NameIdModel<UUID> king = null;
    private NameIdModel<ULID> capital = null;
    private int balance = 0;
    private String color = null;
    private List<NameIdModel<ULID>> lands = new ArrayList<>();
    private List<NameIdModel<ULID>> enemies = new ArrayList<>();
    private List<NameIdModel<ULID>> allies = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ULID getUlid() {
        return ulid;
    }

    public void setUlid(ULID ulid) {
        this.ulid = ulid;
    }

    public NameIdModel<UUID> getKing() {
        return king;
    }

    public void setKing(NameIdModel<UUID> king) {
        this.king = king;
    }

    public NameIdModel<ULID> getCapital() {
        return capital;
    }

    public void setCapital(NameIdModel<ULID> capital) {
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

    public List<NameIdModel<ULID>> getLands() {
        return lands;
    }

    public void setLands(List<NameIdModel<ULID>> lands) {
        this.lands = lands;
    }

    public List<NameIdModel<ULID>> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<NameIdModel<ULID>> enemies) {
        this.enemies = enemies;
    }

    public List<NameIdModel<ULID>> getAllies() {
        return allies;
    }

    public void setAllies(List<NameIdModel<ULID>> allies) {
        this.allies = allies;
    }

}
