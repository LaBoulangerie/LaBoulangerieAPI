package net.laboulangerie.api.models;

import java.io.Serializable;

public class ResidentModel implements Serializable {
    private NameIdModel land = null;
    private NameIdModel nation = null;
    private Boolean isMayor = false;
    private Boolean isKing = false;
    private String roleName = null;
    private int balance = 0;

    public NameIdModel getLand() {
        return land;
    }

    public void setLand(NameIdModel land) {
        this.land = land;
    }

    public NameIdModel getNation() {
        return nation;
    }

    public void setNation(NameIdModel nation) {
        this.nation = nation;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
