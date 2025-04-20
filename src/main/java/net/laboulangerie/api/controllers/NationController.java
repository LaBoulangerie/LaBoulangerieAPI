package net.laboulangerie.api.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
import net.laboulangerie.api.models.NameIdModel;
import net.laboulangerie.api.models.NationModel;

public class NationController {

    public static List<NameIdModel> getAllNations() {
        ArrayList<NameIdModel> nationModels = new ArrayList<>();
        Collection<Nation> nations = LaBoulangerieAPI.LANDS_INTEGRATION.getNations();

        for (Nation nation : nations) {
            NameIdModel model = new NameIdModel(
                    nation.getName(),
                    nation.getULID().toString());

            nationModels.add(model);
        }

        return nationModels;
    }

    @OpenApi(description = "Get all nations", operationId = "getNations", path = "/nation", methods = HttpMethod.GET, tags = {
            "Nation" }, responses = {
                    @OpenApiResponse(status = "200", description = "All nations", content = {
                            @OpenApiContent(from = NameIdModel[].class) })
            })

    public static void getNations(Context ctx) {
        ctx.json(getAllNations());
    }

    @OpenApi(description = "Get nation with name or UUID", operationId = "getNation", path = "/nation/{identifier}", pathParams = {
            @OpenApiParam(name = "identifier", description = "Name or UUID of the nation")
    }, methods = HttpMethod.GET, tags = {
            "Nation" }, responses = {
                    @OpenApiResponse(status = "404", description = "Nation not found", content = {
                            @OpenApiContent(from = NotFoundResponse.class) }),
                    @OpenApiResponse(status = "200", description = "Nation", content = {
                            @OpenApiContent(from = NationModel.class) })
            })

    public static void getNation(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        Nation nation;

        try {
            ULID ulid = ULID.fromString(identifier);
            nation = LaBoulangerieAPI.LANDS_INTEGRATION.getNationByULID(ulid);
        } catch (Exception ignored) {
            nation = LaBoulangerieAPI.LANDS_INTEGRATION.getNationByName(identifier);
        }

        if (nation == null) {
            throw new NotFoundResponse();
        }

        NationModel nationModel = new NationModel();

        nationModel.setName(nation.getName());
        nationModel.setUlid(nation.getULID().toString());

        OfflinePlayer king;
        try {
            king = LaBoulangerieAPI.LANDS_INTEGRATION.getOfflineLandPlayer(nation.getOwnerUID()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to retrieve the mayor information", e);
        }

        NameIdModel kingModel = new NameIdModel(
                king.getName(),
                king.getUID().toString());
        nationModel.setKing(kingModel);

        Land capital = nation.getCapital();
        NameIdModel capitalModel = new NameIdModel(
                capital.getName(),
                capital.getULID().toString());
        nationModel.setCapital(capitalModel);

        nationModel.setBalance((int) nation.getBalance());
        nationModel.setColor(nation.getColorName());

        nation.getLands().forEach(land -> {
            NameIdModel landModel = new NameIdModel(
                    land.getName(),
                    land.getULID().toString());
            nationModel.getLands().add(landModel);
        });

        nation.getAllies().forEach(ally -> {
            NameIdModel allyModel = new NameIdModel(
                    ally.getName(),
                    ally.getULID().toString());
            nationModel.getAllies().add(allyModel);
        });

        nation.getEnemies().forEach(enemy -> {
            NameIdModel enemyModel = new NameIdModel(
                    enemy.getName(),
                    enemy.getULID().toString());
            nationModel.getEnemies().add(enemyModel);
        });

        ctx.json(nationModel);
    }
}
