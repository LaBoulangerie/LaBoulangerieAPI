package net.laboulangerie.api.models;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ServerModel implements Serializable {
    private String name = null;
    private String motd = null;
    private String version = null;
    private String bukkitVersion = null;
    private String tps = null;
    private int maxPlayers = 0;
    private List<NameIdModel> onlinePlayers = Collections.emptyList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMotd() {
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBukkitVersion() {
        return bukkitVersion;
    }

    public void setBukkitVersion(String bukkitVersion) {
        this.bukkitVersion = bukkitVersion;
    }

    public String getTps() {
        return tps;
    }

    public void setTps(String tps) {
        this.tps = tps;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public List<NameIdModel> getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(List<NameIdModel> onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }
}
