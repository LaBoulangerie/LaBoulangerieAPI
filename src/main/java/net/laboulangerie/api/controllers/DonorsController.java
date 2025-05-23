package net.laboulangerie.api.controllers;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;

import io.javalin.http.Context;
import io.javalin.openapi.HttpMethod;
import io.javalin.openapi.OpenApi;
import io.javalin.openapi.OpenApiResponse;
import io.javalin.openapi.OpenApiContent;
import io.javalin.openapi.OpenApiRequestBody;
import net.laboulangerie.api.LaBoulangerieAPI;
import net.laboulangerie.api.database.GsonFiles;
import net.laboulangerie.api.models.TypedNameIdModel;

public class DonorsController {
    private static File donorsFile = new File(LaBoulangerieAPI.PLUGIN.getDataFolder(), "donors.json");

    public static List<TypedNameIdModel> getDonorsArray() {
        List<TypedNameIdModel> donorsArray = new ArrayList<>();

        try {
            donorsArray = Arrays.asList(GsonFiles.readArray(donorsFile));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return donorsArray;
    }

    @OpenApi(description = "Get donors", operationId = "getDonors", path = "/donors", methods = HttpMethod.GET, tags = {
            "Donors" }, responses = {
                    @OpenApiResponse(status = "200", description = "Donors", content = {
                            @OpenApiContent(from = TypedNameIdModel[].class) })
            })
    public static void getDonors(Context ctx) {
        ctx.json(getDonorsArray());
    }

    @OpenApi(description = "Add donor", operationId = "addDonor", path = "/donors", methods = HttpMethod.POST, tags = {
            "Donors" }, requestBody = @OpenApiRequestBody(content = {
                    @OpenApiContent(from = TypedNameIdModel.class) }))
    public static void addDonor(Context ctx) {
        DecodedJWT decodedJWT = LaBoulangerieAPI.JWT_MANAGER.getJwtFromContext(ctx);
        if (decodedJWT == null)
            return;

        TypedNameIdModel newDonor = new Gson().fromJson(ctx.body(),
                new com.google.gson.reflect.TypeToken<TypedNameIdModel>() {
                }.getType());

        List<TypedNameIdModel> donorsArray = new ArrayList<>(getDonorsArray());

        // Check if donor already exists
        OptionalInt index = IntStream.range(0, donorsArray.size())
                .filter(i -> donorsArray.get(i).getId().equals(newDonor.getId()))
                .findFirst();

        if (index.isPresent()) {
            TypedNameIdModel donor = donorsArray.get(index.getAsInt());
            // Increment the type (donation amount)
            donor.setType(Integer.toString(Integer.parseInt(donor.getType()) + Integer.parseInt(newDonor.getType())));
        } else {
            donorsArray.add(newDonor);
        }

        try {
            GsonFiles.writeArray(donorsFile, donorsArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OpenApi(description = "Delete donor", operationId = "deleteDonor", path = "/donors", methods = HttpMethod.DELETE, tags = {
            "Donors" }, requestBody = @OpenApiRequestBody(content = { @OpenApiContent(from = TypedNameIdModel.class)

    }))

    public static void deleteDonor(Context ctx) {
        DecodedJWT decodedJWT = LaBoulangerieAPI.JWT_MANAGER.getJwtFromContext(ctx);
        if (decodedJWT == null)
            return;

        TypedNameIdModel donorToDelete = new Gson().fromJson(ctx.body(),
                new com.google.gson.reflect.TypeToken<TypedNameIdModel>() {
                }.getType());
        List<TypedNameIdModel> donorArray = new ArrayList<>(getDonorsArray());

        donorArray.removeIf(
                d -> (d.getId().equals(donorToDelete.getId()))
                        || (d.getName().equals(donorToDelete.getName())));

        try {
            GsonFiles.writeArray(donorsFile, donorArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
