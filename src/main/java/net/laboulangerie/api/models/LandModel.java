package net.laboulangerie.api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.angeschossen.lands.api.applicationframework.util.ULID;

public class LandModel implements Serializable {
    private String name = null;
    private ULID ulid = null;
    private NameIdModel nation = null;
    private NameIdModel mayor = null;
    private String titleMessage = null;
    private String levelName = null;
    private int balance = 0;
    private String color = null;
    private List<NameIdModel> residents = new ArrayList<>();
    private CoordinatesModel spawn = null;
    private List<CoordinatesModel> chunksCoordinates = new ArrayList<>();
    private List<NameIdModel> enemies = new ArrayList<>();
    private List<NameIdModel> allies = new ArrayList<>();

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

    public NameIdModel getNation() {
        return nation;
    }

    public void setNation(NameIdModel nation) {
        this.nation = nation;
    }

    public NameIdModel getMayor() {
        return mayor;
    }

    public void setMayor(NameIdModel mayor) {
        this.mayor = mayor;
    }

    public String getTitleMessage() {
        return titleMessage;
    }

    public void setTitleMessage(String titleMessage) {
        this.titleMessage = titleMessage;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
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

    public List<NameIdModel> getResidents() {
        return residents;
    }

    public void setResidents(List<NameIdModel> residents) {
        this.residents = residents;
    }

    public CoordinatesModel getSpawn() {
        return spawn;
    }

    public void setSpawn(CoordinatesModel spawn) {
        this.spawn = spawn;
    }

    public List<CoordinatesModel> getChunksCoordinates() {
        return chunksCoordinates;
    }

    public void setChunksCoordinates(List<CoordinatesModel> chunksCoordinates) {
        this.chunksCoordinates = chunksCoordinates;
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
