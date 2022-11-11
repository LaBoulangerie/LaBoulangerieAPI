package net.laboulangerie.api.models;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class NationModel implements Serializable {
    private String name = null;
    private UUID uuid = null;
    private NameUuidModel king = null;
    private NameUuidModel capital = null;
    private String board = null;
    private String tag = null;
    private String formattedName = null;
    private long registered = 0;
    private String mapColor = null;
    private List<NameUuidModel> towns = Collections.emptyList();
    private List<NameUuidModel> residents = Collections.emptyList();
    private List<NameUuidModel> enemies = Collections.emptyList();
    private List<NameUuidModel> allies = Collections.emptyList();
    private Boolean isNeutral = null;
    private Boolean isOpen = null;
    private Boolean isPublic = null;

    public NationModel() {
    }

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

    public NameUuidModel getKing() {
        return king;
    }

    public void setKing(NameUuidModel king) {
        this.king = king;
    }

    public NameUuidModel getCapital() {
        return capital;
    }

    public void setCapital(NameUuidModel capital) {
        this.capital = capital;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public void setFormattedName(String formattedName) {
        this.formattedName = formattedName;
    }

    public long getRegistered() {
        return registered;
    }

    public void setRegistered(long registered) {
        this.registered = registered;
    }

    public String getMapColor() {
        return mapColor;
    }

    public void setMapColor(String mapColor) {
        this.mapColor = mapColor;
    }

    public List<NameUuidModel> getTowns() {
        return towns;
    }

    public void setTowns(List<NameUuidModel> towns) {
        this.towns = towns;
    }

    public List<NameUuidModel> getResidents() {
        return residents;
    }

    public void setResidents(List<NameUuidModel> residents) {
        this.residents = residents;
    }

    public List<NameUuidModel> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<NameUuidModel> enemies) {
        this.enemies = enemies;
    }

    public List<NameUuidModel> getAllies() {
        return allies;
    }

    public void setAllies(List<NameUuidModel> allies) {
        this.allies = allies;
    }

    public Boolean getIsNeutral() {
        return isNeutral;
    }

    public void setIsNeutral(Boolean isNeutral) {
        this.isNeutral = isNeutral;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
}
