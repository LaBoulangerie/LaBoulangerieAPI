package net.laboulangerie.api.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.openapi.HttpMethod;
import io.javalin.openapi.OpenApi;
import io.javalin.openapi.OpenApiResponse;
import me.angeschossen.lands.api.applicationframework.util.ULID;
import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.nation.Nation;
import me.angeschossen.lands.api.player.OfflinePlayer;
import io.javalin.openapi.OpenApiContent;
import io.javalin.openapi.OpenApiParam;
import net.laboulangerie.api.LaBoulangerieAPI;
import net.laboulangerie.api.models.CoordinatesModel;
import net.laboulangerie.api.models.LandModel;
import net.laboulangerie.api.models.NameIdModel;

public class LandController {

    public static List<NameIdModel<ULID>> getAllLands() {
        ArrayList<NameIdModel<ULID>> landModels = new ArrayList<>();
        Collection<Land> lands = LaBoulangerieAPI.LANDS_INTEGRATION.getLands();

        for (Land land : lands) {
            NameIdModel<ULID> landModel = new NameIdModel<ULID>(
                    land.getName(),
                    land.getULID());
            landModels.add(landModel);
        }

        return landModels;
    }

    @OpenApi(description = "Get all lands", operationId = "getLands", path = "/land", methods = HttpMethod.GET, tags = {
            "Land" }, responses = {
                    @OpenApiResponse(status = "200", description = "All lands", content = {
                            @OpenApiContent(from = NameIdModel[].class) })
            })

    public static void getLands(Context ctx) {
        ctx.json(getAllLands());
    }

    @OpenApi(description = "Get land with name or ULID", operationId = "getLand", path = "/land/{identifier}", pathParams = {
            @OpenApiParam(name = "identifier", description = "Name or ULID of the land")
    }, methods = HttpMethod.GET, tags = {
            "Land" }, responses = {
                    @OpenApiResponse(status = "404", description = "Land not found", content = {
                            @OpenApiContent(from = NotFoundResponse.class) }),
                    @OpenApiResponse(status = "200", description = "Land", content = {
                            @OpenApiContent(from = LandModel.class) })
            })

    public static void getLand(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Land land = null;

        try {
            ULID ulid = ULID.fromString(identifier);
            land = LaBoulangerieAPI.LANDS_INTEGRATION.getLandByULID(ulid);
        } catch (Exception ignored) {
            land = LaBoulangerieAPI.LANDS_INTEGRATION.getLandByName(identifier);
        }

        if (land == null) {
            throw new NotFoundResponse();
        }

        LandModel landModel = new LandModel();

        landModel.setName(land.getName());
        landModel.setUlid(land.getULID());

        Nation nation = land.getNation();

        if (nation != null) {
            NameIdModel<ULID> nationModel = new NameIdModel<ULID>(
                    nation.getName(),
                    nation.getULID());

            landModel.setNation(nationModel);
        }

        OfflinePlayer mayor;
        try {
            mayor = LaBoulangerieAPI.LANDS_INTEGRATION.getOfflineLandPlayer(land.getOwnerUID()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to retrieve the mayor information", e);
        }

        NameIdModel<UUID> mayorModel = new NameIdModel<UUID>(
                mayor.getName(),
                mayor.getUID());
        landModel.setMayor(mayorModel);

        landModel.setTitleMessage(land.getTitleMessage(null));
        landModel.setLevelName(land.getLevel().getName());
        landModel.setBalance((int) land.getBalance());
        landModel.setColor(land.getColorName());

        Collection<UUID> residentsUUIDs = land.getTrustedPlayers();
        List<NameIdModel<UUID>> residentModels = new ArrayList<>();

        for (UUID residentUUID : residentsUUIDs) {
            NameIdModel<UUID> residentModel = new NameIdModel<UUID>(
                    Bukkit.getOfflinePlayer(residentUUID).getName(),
                    residentUUID);

            residentModels.add(residentModel);
        }

        landModel.setResidents(residentModels);

        Location spawn;

        spawn = land.getSpawn();
        CoordinatesModel spawnModel = new CoordinatesModel();
        spawnModel.setX(spawn.getX());
        spawnModel.setY(spawn.getY());
        spawnModel.setZ(spawn.getZ());
        spawnModel.setWorld(spawn.getWorld().getName());
        spawnModel.setType("spawn");

        landModel.setSpawn(spawnModel);

        land.getContainers().forEach((container) -> {
            container.getChunks().forEach((chunk) -> {
                CoordinatesModel chunkModel = new CoordinatesModel();

                chunkModel.setX(chunk.getX());
                chunkModel.setZ(chunk.getZ());
                chunkModel.setType("chunk");
                chunkModel.setWorld(container.getWorld().getName());

                landModel.getChunksCoordinates().add(chunkModel);
            });
        });

        land.getAllies().forEach((ally) -> {
            NameIdModel<ULID> allyModel = new NameIdModel<ULID>(
                    ally.getName(),
                    ally.getULID());

            landModel.getAllies().add(allyModel);
        });

        land.getEnemies().forEach((enemy) -> {
            NameIdModel<ULID> enemyModel = new NameIdModel<ULID>(
                    enemy.getName(),
                    enemy.getULID());

            landModel.getEnemies().add(enemyModel);
        });

        ctx.json(landModel);
    }
}
