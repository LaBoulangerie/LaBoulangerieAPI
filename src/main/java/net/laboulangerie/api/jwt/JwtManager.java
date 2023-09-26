package net.laboulangerie.api.jwt;

import java.util.Optional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.javalin.http.Context;
import javalinjwt.JWTAccessManager;
import javalinjwt.JWTGenerator;
import javalinjwt.JWTProvider;
import javalinjwt.JavalinJWT;
import net.laboulangerie.api.LaBoulangerieAPI;

public class JwtManager {
    private Algorithm algorithm;

    private JWTGenerator<JwtUser> generator;
    private JWTVerifier verifier;
    private JWTProvider<JwtUser> provider;
    private JWTAccessManager accessManager;

    public JwtManager() {
        String secret = LaBoulangerieAPI.PLUGIN.getConfig().getString("secret");
        this.algorithm = Algorithm.HMAC256(secret);
        this.generator = (user, algorithm) -> {
            JWTCreator.Builder token = JWT.create()
                    .withClaim("name", user.getName())
                    .withClaim("level", user.getLevel().name());
            return token.sign(algorithm);
        };

        this.verifier = JWT.require(algorithm).build();
        this.provider = new JWTProvider<JwtUser>(algorithm, generator, verifier);
        this.accessManager = new JWTAccessManager("level", JwtLevel.rolesMapping, JwtLevel.ANYONE);
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public JWTGenerator<JwtUser> getGenerator() {
        return generator;
    }

    public JWTVerifier getVerifier() {
        return verifier;
    }

    public JWTProvider<JwtUser> getProvider() {
        return provider;
    }

    public JWTAccessManager getAccessManager() {
        return accessManager;
    }

    public DecodedJWT getJwtFromContext(Context ctx) {
        Optional<DecodedJWT> decodedJwt = JavalinJWT.getTokenFromHeader(ctx).flatMap(provider::validateToken);

        if (!decodedJwt.isPresent()) {
            ctx.status(418).result("Missing or invalid token");
            return null;
        }

        return decodedJwt.get();
    }
}
