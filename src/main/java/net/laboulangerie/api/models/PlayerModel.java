package net.laboulangerie.api.models;

import java.io.Serializable;
import java.util.UUID;

public class PlayerModel implements Serializable {
    private String name = null;
    private UUID uuid = null;
    private long firstPlayed = 0;
    private long lastSeen = 0;
    private Boolean isOnline = false;
    private ResidentModel resident = null;
    private MmoModel mmo = null;

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

    public long getFirstPlayed() {
        return firstPlayed;
    }

    public void setFirstPlayed(long firstPlayed) {
        this.firstPlayed = firstPlayed;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public ResidentModel getResident() {
        return resident;
    }

    public void setResident(ResidentModel resident) {
        this.resident = resident;
    }

    public MmoModel getMmo() {
        return mmo;
    }

    public void setMmo(MmoModel mmo) {
        this.mmo = mmo;
    }

}
