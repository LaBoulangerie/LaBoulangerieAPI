package net.laboulangerie.api.models;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TownModel implements Serializable {
    private String name = null;
    private UUID uuid = null;
    private NameUuidModel nation = null;
    private long joinedNationAt = 0;
    private NameUuidModel mayor = null;
    private String board = null;
    private String tag = null;
    private String formattedName = null;
    private long registered = 0;
    private int balance = 0;
    private String mapColor = null;
    private List<NameUuidModel> residents = Collections.emptyList();
    private CoordinatesModel spawn = null;
    private List<CoordinatesModel> townBlocks = Collections.emptyList();
    private Boolean isNeutral = null;
    private Boolean isOpen = null;
    private Boolean isPublic = null;

    public TownModel() {
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

    public NameUuidModel getNation() {
        return nation;
    }

    public void setNation(NameUuidModel nation) {
        this.nation = nation;
    }

    public long getJoinedNationAt() {
        return joinedNationAt;
    }

    public void setJoinedNationAt(long joinedNationAt) {
        this.joinedNationAt = joinedNationAt;
    }

    public NameUuidModel getMayor() {
        return mayor;
    }

    public void setMayor(NameUuidModel mayor) {
        this.mayor = mayor;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getMapColor() {
        return mapColor;
    }

    public void setMapColor(String mapColor) {
        this.mapColor = mapColor;
    }

    public List<NameUuidModel> getResidents() {
        return residents;
    }

    public void setResidents(List<NameUuidModel> residents) {
        this.residents = residents;
    }

    public CoordinatesModel getSpawn() {
        return spawn;
    }

    public void setSpawn(CoordinatesModel spawn) {
        this.spawn = spawn;
    }

    public List<CoordinatesModel> getTownBlocks() {
        return townBlocks;
    }

    public void setTownBlocks(List<CoordinatesModel> townBlocks) {
        this.townBlocks = townBlocks;
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
