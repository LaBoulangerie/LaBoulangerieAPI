package net.laboulangerie.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Location;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.openapi.HttpMethod;
import io.javalin.openapi.OpenApi;
import io.javalin.openapi.OpenApiResponse;
import io.javalin.openapi.OpenApiContent;
import io.javalin.openapi.OpenApiParam;
import net.laboulangerie.api.models.CoordinatesModel;
import net.laboulangerie.api.models.NameUuidModel;
import net.laboulangerie.api.models.NationModel;

public class NationController {

    public static List<NameUuidModel> getAllNations() {
        ArrayList<NameUuidModel> nationModels = new ArrayList<>();
        List<Nation> nations = TownyUniverse.getInstance().getNations().stream().collect(Collectors.toList());

        for (Nation nation : nations) {
            NameUuidModel model = new NameUuidModel();

            model.setName(nation.getName());
            model.setUuid(nation.getUUID());

            nationModels.add(model);
        }

        return nationModels;
    }

    @OpenApi(summary = "Get all nations", operationId = "getNations", path = "/nation", methods = HttpMethod.GET, tags = {
            "Nation" }, responses = {
                    @OpenApiResponse(status = "200", content = {
                            @OpenApiContent(from = NameUuidModel[].class) })
            })
    public static void getNations(Context ctx) {
        ctx.json(getAllNations());
    }

    @OpenApi(summary = "Get nation with UUID", operationId = "getNation", path = "/nation/{uuid}", pathParams = {
            @OpenApiParam(name = "uuid", description = "UUID of the nation")
    }, methods = HttpMethod.GET, tags = {
            "Nation" }, responses = {
                    @OpenApiResponse(status = "400", content = { @OpenApiContent(from = BadRequestResponse.class) }),
                    @OpenApiResponse(status = "404", content = { @OpenApiContent(from = NotFoundResponse.class) }),
                    @OpenApiResponse(status = "200", content = {
                            @OpenApiContent(from = NationModel.class) })
            })

    public static void getNation(Context ctx) {
        UUID uuid;

        try {
            uuid = UUID.fromString(ctx.pathParam("uuid"));
        } catch (Exception e) {
            throw new BadRequestResponse();
        }

        Nation nation = TownyUniverse.getInstance().getNation(uuid);

        if (nation == null) {
            throw new NotFoundResponse();
        }

        NationModel nationModel = new NationModel();

        nationModel.setName(nation.getName());
        nationModel.setUuid(nation.getUUID());

        Resident king = nation.getKing();
        NameUuidModel kingModel = new NameUuidModel();

        kingModel.setName(king.getName());
        kingModel.setUuid(king.getUUID());
        nationModel.setKing(kingModel);

        Town capital = nation.getCapital();
        NameUuidModel capitalModel = new NameUuidModel();

        capitalModel.setName(capital.getName());
        capitalModel.setUuid(capital.getUUID());

        nationModel.setCapital(capitalModel);

        nationModel.setBoard(nation.getBoard());
        nationModel.setTag(nation.getTag());
        nationModel.setFormattedName(nation.getFormattedName());
        nationModel.setRegistered(nation.getRegistered());
        nationModel.setBalance((int) nation.getAccount().getHoldingBalance());
        nationModel.setMapColor(nation.getMapColorHexCode());

        List<Town> towns = nation.getTowns();
        List<NameUuidModel> townModels = new ArrayList<>();

        for (Town town : towns) {
            NameUuidModel townModel = new NameUuidModel();

            townModel.setName(town.getName());
            townModel.setUuid(town.getUUID());

            townModels.add(townModel);
        }

        nationModel.setTowns(townModels);

        List<Resident> residents = nation.getResidents();
        List<NameUuidModel> residentModels = new ArrayList<>();

        for (Resident resident : residents) {
            NameUuidModel residentModel = new NameUuidModel();

            residentModel.setName(resident.getName());
            residentModel.setUuid(resident.getUUID());

            residentModels.add(residentModel);
        }

        nationModel.setResidents(residentModels);

        List<Nation> enemies = nation.getEnemies();
        List<NameUuidModel> enemyModels = new ArrayList<>();

        for (Nation enemy : enemies) {
            NameUuidModel enemyModel = new NameUuidModel();

            enemyModel.setName(enemy.getName());
            enemyModel.setUuid(enemy.getUUID());

            enemyModels.add(enemyModel);
        }

        nationModel.setEnemies(enemyModels);

        List<Nation> allies = nation.getAllies();
        List<NameUuidModel> allyModels = new ArrayList<>();

        for (Nation ally : allies) {
            NameUuidModel allyModel = new NameUuidModel();

            allyModel.setName(ally.getName());
            allyModel.setUuid(ally.getUUID());

            allyModels.add(allyModel);
        }

        nationModel.setAllies(allyModels);

        Location spawn;

        try {
            spawn = nation.getSpawn();
            CoordinatesModel spawnModel = new CoordinatesModel();
            spawnModel.setX(spawn.getX());
            spawnModel.setY(spawn.getY());
            spawnModel.setZ(spawn.getZ());
            spawnModel.setWorld(spawn.getWorld().getName());
            spawnModel.setType("spawn");

            nationModel.setSpawn(spawnModel);
        } catch (TownyException ignored) {
        }

        nationModel.setIsNeutral(nation.isNeutral());
        nationModel.setIsOpen(nation.isOpen());
        nationModel.setIsPublic(nation.isPublic());

        ctx.json(nationModel);
    }
}
