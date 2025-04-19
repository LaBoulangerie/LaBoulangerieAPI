package net.laboulangerie.api.models;

import java.io.Serializable;

import me.angeschossen.lands.api.applicationframework.util.ULID;

public class ResidentModel implements Serializable {
    private NameIdModel<ULID> land = null;
    private NameIdModel<ULID> nation = null;
    private Boolean isMayor = false;
    private Boolean isKing = false;
    private String roleName = null;
    private int balance = 0;

    public NameIdModel<ULID> getLand() {
        return land;
    }

    public void setLand(NameIdModel<ULID> land) {
        this.land = land;
    }

    public NameIdModel<ULID> getNation() {
        return nation;
    }

    public void setNation(NameIdModel<ULID> nation) {
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
