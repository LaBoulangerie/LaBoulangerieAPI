package net.laboulangerie.api.jwt;

import java.util.HashMap;
import java.util.Map;

import io.javalin.security.RouteRole;

public enum JwtLevel implements RouteRole {
    ANYONE,
    USER,
    ADMIN;

    public static Map<String, RouteRole> rolesMapping = new HashMap<>() {
        {
            put("USER", JwtLevel.USER);
            put("ADMIN", JwtLevel.ADMIN);
        }
    };
}