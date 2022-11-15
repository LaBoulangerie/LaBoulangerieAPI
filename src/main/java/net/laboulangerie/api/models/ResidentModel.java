package net.laboulangerie.api.models;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ResidentModel implements Serializable {
    private NameUuidModel town = null;
    private NameUuidModel nation = null;
    private List<NameUuidModel> friends = Collections.emptyList();
    private Boolean isMayor = false;
    private Boolean isKing = false;
    private List<String> townRanks = Collections.emptyList();
    private List<String> nationRanks = Collections.emptyList();
    private String surname = null;
    private String title = null;
    private String prefix = null;
    private String postfix = null;
    private String formattedName = null;

    public NameUuidModel getTown() {
        return town;
    }

    public void setTown(NameUuidModel town) {
        this.town = town;
    }

    public NameUuidModel getNation() {
        return nation;
    }

    public void setNation(NameUuidModel nation) {
        this.nation = nation;
    }

    public List<NameUuidModel> getFriends() {
        return friends;
    }

    public void setFriends(List<NameUuidModel> friends) {
        this.friends = friends;
    }

    public Boolean getIsMayor() {
        return isMayor;
    }

    public void setIsMayor(Boolean isMayor) {
        this.isMayor = isMayor;
    }

    public Boolean getIsKing() {
        return isKing;
    }

    public void setIsKing(Boolean isKing) {
        this.isKing = isKing;
    }

    public List<String> getTownRanks() {
        return townRanks;
    }

    public void setTownRanks(List<String> townRanks) {
        this.townRanks = townRanks;
    }

    public List<String> getNationRanks() {
        return nationRanks;
    }

    public void setNationRanks(List<String> nationRanks) {
        this.nationRanks = nationRanks;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public void setFormattedName(String formattedName) {
        this.formattedName = formattedName;
    }
}
