package net.laboulangerie.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Location;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.openapi.HttpMethod;
import io.javalin.openapi.OpenApi;
import io.javalin.openapi.OpenApiResponse;
import io.javalin.openapi.OpenApiContent;
import io.javalin.openapi.OpenApiParam;
import net.laboulangerie.api.models.NameUuidModel;
import net.laboulangerie.api.models.CoordinatesModel;
import net.laboulangerie.api.models.TownModel;

public class TownController {

    @OpenApi(summary = "Get all towns", operationId = "getTowns", path = "/town", methods = HttpMethod.GET, tags = {
            "Town" }, responses = {
                    @OpenApiResponse(status = "200", content = {
                            @OpenApiContent(from = NameUuidModel[].class) })
            })

    public static void getTowns(Context ctx) {
        ArrayList<NameUuidModel> townModels = new ArrayList<>();
        List<Town> towns = TownyAPI.getInstance().getTowns();

        for (Town town : towns) {
            NameUuidModel model = new NameUuidModel();

            model.setName(town.getName());
            model.setUuid(town.getUUID());

            townModels.add(model);
        }

        ctx.json(townModels);
    }

    @OpenApi(summary = "Get town with UUID", operationId = "getTown", path = "/town/{uuid}", pathParams = {
            @OpenApiParam(name = "uuid", description = "UUID of the town")
    }, methods = HttpMethod.GET, tags = {
            "Town" }, responses = {
                    @OpenApiResponse(status = "400", content = { @OpenApiContent(from = BadRequestResponse.class) }),
                    @OpenApiResponse(status = "404", content = { @OpenApiContent(from = NotFoundResponse.class) }),
                    @OpenApiResponse(status = "200", content = {
                            @OpenApiContent(from = TownModel.class) })
            })

    public static void getTown(Context ctx) {
        UUID uuid;

        try {
            uuid = UUID.fromString(ctx.pathParam("uuid"));
        } catch (Exception e) {
            throw new BadRequestResponse();
        }

        Town town = TownyAPI.getInstance().getTown(uuid);

        if (town == null) {
            throw new NotFoundResponse();
        }

        TownModel townModel = new TownModel();

        townModel.setName(town.getName());
        townModel.setUuid(town.getUUID());

        Nation nation = town.getNationOrNull();

        if (nation != null) {
            NameUuidModel nationModel = new NameUuidModel();

            nationModel.setName(nation.getName());
            nationModel.setUuid(nation.getUUID());

            townModel.setNation(nationModel);
            townModel.setJoinedNationAt(town.getJoinedNationAt());
        }

        Resident mayor = town.getMayor();
        NameUuidModel mayorModel = new NameUuidModel();

        mayorModel.setName(mayor.getName());
        mayorModel.setUuid(mayor.getUUID());
        townModel.setMayor(mayorModel);

        townModel.setBoard(town.getBoard());
        townModel.setTag(town.getTag());
        townModel.setFormattedName(town.getFormattedName());
        townModel.setRegistered(town.getRegistered());
        townModel.setMapColor(town.getMapColorHexCode());

        List<Resident> residents = town.getResidents();
        List<NameUuidModel> residentModels = new ArrayList<>();

        for (Resident resident : residents) {
            NameUuidModel residentModel = new NameUuidModel();

            residentModel.setName(resident.getName());
            residentModel.setUuid(resident.getUUID());

            residentModels.add(residentModel);
        }

        townModel.setResidents(residentModels);

        Location spawn;

        try {
            spawn = town.getSpawn();
            CoordinatesModel spawnModel = new CoordinatesModel();
            spawnModel.setX(spawn.getX());
            spawnModel.setY(spawn.getY());
            spawnModel.setZ(spawn.getZ());
            spawnModel.setWorld(spawn.getWorld().getName());
            spawnModel.setType("spawn");

            townModel.setSpawn(spawnModel);
        } catch (TownyException ignored) {
        }

        List<TownBlock> townBlocks = town.getTownBlocks().stream().collect(Collectors.toList());
        List<CoordinatesModel> townBlockModels = new ArrayList<>();

        for (TownBlock townBlock : townBlocks) {
            CoordinatesModel townBlockModel = new CoordinatesModel();

            townBlockModel.setX(townBlock.getX());
            townBlockModel.setZ(townBlock.getZ());
            townBlockModel.setType(townBlock.getTypeName());
            townBlockModel.setWorld(townBlock.getWorld().getName());

            townBlockModels.add(townBlockModel);
        }

        townModel.setTownBlocks(townBlockModels);

        townModel.setIsNeutral(town.isNeutral());
        townModel.setIsOpen(town.isOpen());
        townModel.setIsPublic(town.isPublic());

        ctx.json(townModel);
    }
}
