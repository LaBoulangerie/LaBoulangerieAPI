package net.laboulangerie.api;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.lang.reflect.Type;
import java.util.Random;

import io.javalin.Javalin;
import io.javalin.json.JsonMapper;
import io.javalin.openapi.plugin.OpenApiConfiguration;
import io.javalin.openapi.plugin.OpenApiPlugin;
import io.javalin.openapi.plugin.swagger.SwaggerConfiguration;
import io.javalin.openapi.plugin.swagger.SwaggerPlugin;
import net.laboulangerie.api.controllers.NationController;
import net.laboulangerie.api.controllers.PlayerController;
import net.laboulangerie.api.controllers.ServerController;
import net.laboulangerie.api.controllers.TownController;

public class LaBoulangerieAPI extends JavaPlugin {
    public static LaBoulangerieAPI PLUGIN;
    private static Javalin app = null;

    @Override
    public void onEnable() {
        LaBoulangerieAPI.PLUGIN = this;
        setupJavalin();
        getLogger().info("Enabled Successfully");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled");
        if (app != null) {
            app.stop();
        }
    }

    private void setupJavalin() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(LaBoulangerieAPI.class.getClassLoader());

        if (app == null) {
            app = Javalin.create(config -> {
                config.showJavalinBanner = false;

                Gson gson = new GsonBuilder().create();
                JsonMapper gsonMapper = new JsonMapper() {
                    @Override
                    public String toJsonString(@NotNull Object obj, @NotNull Type type) {
                        return gson.toJson(obj, type);
                    }

                    @Override
                    public <T> T fromJsonString(@NotNull String json, @NotNull Type targetType) {
                        return gson.fromJson(json, targetType);
                    }
                };

                config.jsonMapper(gsonMapper);

                OpenApiConfiguration openApiConfiguration = new OpenApiConfiguration();
                openApiConfiguration.getInfo().setTitle("La Boulangerie API");
                config.plugins.register(new OpenApiPlugin(openApiConfiguration));

                SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration();
                swaggerConfiguration.setUiPath("/swagger");
                config.plugins.register(new SwaggerPlugin(swaggerConfiguration));
            });
        }

        app.start(8888);
        app.get("/", ctx -> ctx.redirect("/swagger"));

        app.routes(() -> {
            path("server", () -> {
                get(ServerController::getServer);
            });
            path("player", () -> {
                get(PlayerController::getPlayers);
                path("{uuid}", () -> {
                    get(PlayerController::getPlayer);
                });
            });
            path("nation", () -> {
                get(NationController::getNations);
                path("{uuid}", () -> {
                    get(NationController::getNation);
                });
            });
            path("town", () -> {
                get(TownController::getTowns);
                path("{uuid}", () -> {
                    get(TownController::getTown);
                });
            });
        });

        app.error(404, ctx -> {
            String[] pastries = { "🥖", "🥐", "🍞", "🥨", "🧇", "🥯", "🥞" };
            ctx.result("You seem lost, here get a pastry! " + pastries[new Random().nextInt(pastries.length)]);
        });

        Thread.currentThread().setContextClassLoader(classLoader);
    }
}