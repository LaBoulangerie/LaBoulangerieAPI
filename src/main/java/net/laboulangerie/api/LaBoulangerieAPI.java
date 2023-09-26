package net.laboulangerie.api;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.json.JsonMapper;
import io.javalin.openapi.plugin.OpenApiConfiguration;
import io.javalin.openapi.plugin.OpenApiPlugin;
import io.javalin.openapi.plugin.swagger.SwaggerConfiguration;
import io.javalin.openapi.plugin.swagger.SwaggerPlugin;
import io.javalin.websocket.WsContext;
import javalinjwt.JavalinJWT;
import net.laboulangerie.api.commands.GenerateCommand;
import net.laboulangerie.api.controllers.DonorsController;
import net.laboulangerie.api.controllers.NationController;
import net.laboulangerie.api.controllers.PlayerController;
import net.laboulangerie.api.controllers.SearchController;
import net.laboulangerie.api.controllers.ServerController;
import net.laboulangerie.api.controllers.StaffController;
import net.laboulangerie.api.controllers.TownController;
import net.laboulangerie.api.jwt.JwtLevel;
import net.laboulangerie.api.jwt.JwtManager;
import net.laboulangerie.api.listeners.TownyListener;

public class LaBoulangerieAPI extends JavaPlugin {
    public static LaBoulangerieAPI PLUGIN;
    public static JwtManager JWT_MANAGER;
    private static Javalin app = null;
    private static final List<WsContext> wsList = new ArrayList<>();

    @Override
    public void onEnable() {
        LaBoulangerieAPI.PLUGIN = this;
        LaBoulangerieAPI.JWT_MANAGER = new JwtManager();

        this.saveDefaultConfig();

        getCommand("generate").setExecutor(new GenerateCommand());
        registerListeners();

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

                config.accessManager(JWT_MANAGER.getAccessManager());
            });
        }

        Handler decodeHandler = JavalinJWT.createHeaderDecodeHandler(JWT_MANAGER.getProvider());
        app.before(decodeHandler);

        app.start(PLUGIN.getConfig().getInt("port"));
        app.get("/", ctx -> ctx.redirect("/swagger"));

        app.routes(() -> {
            path("server", () -> {
                get(ServerController::getServer, JwtLevel.ANYONE);
            });
            path("staff", () -> {
                get(StaffController::getStaff, JwtLevel.ANYONE);
                post(StaffController::addStaff, JwtLevel.ADMIN);
                delete(StaffController::deleteStaff, JwtLevel.ADMIN);
            });
            path("donors", () -> {
                get(DonorsController::getDonors, JwtLevel.ANYONE);
                post(DonorsController::addDonor, JwtLevel.ADMIN);
                delete(DonorsController::deleteDonor, JwtLevel.ADMIN);
            });
            path("player", () -> {
                get(PlayerController::getPlayers, JwtLevel.ANYONE);
                path("{identifier}", () -> {
                    get(PlayerController::getPlayer, JwtLevel.ANYONE);
                });
            });
            path("nation", () -> {
                get(NationController::getNations, JwtLevel.ANYONE);
                path("{identifier}", () -> {
                    get(NationController::getNation, JwtLevel.ANYONE);
                });
            });
            path("town", () -> {
                get(TownController::getTowns, JwtLevel.ANYONE);
                path("{identifier}", () -> {
                    get(TownController::getTown, JwtLevel.ANYONE);
                });
            });
            path("search", () -> {
                path("{query}", () -> {
                    get(SearchController::search, JwtLevel.ANYONE);
                });
            });
        });

        app.ws("/ws/towny", ws -> {
            ws.onConnect(ctx -> {
                wsList.add(ctx);
            });

            ws.onClose(ctx -> {
                wsList.remove(ctx);
            });
        });

        Thread.currentThread().setContextClassLoader(classLoader);
    }

    private void registerListeners() {
        List<Listener> listeners = Arrays.asList(
                new TownyListener());

        listeners.forEach(l -> getServer().getPluginManager().registerEvents(l, this));
    }

    static public void broadcast(Serializable serializable) {
        for (WsContext ctx : wsList) {
            if (ctx.session.isOpen()) {
                ctx.send(serializable);
            }
        }
    }
}