package net.laboulangerie.api.jwt;

public class JwtUser {
    private String name;
    private JwtLevel level;

    public JwtUser(String name, JwtLevel level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public JwtLevel getLevel() {
        return level;
    }
}
