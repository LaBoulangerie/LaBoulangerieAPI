package net.laboulangerie.api.controllers;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import io.javalin.http.Context;
import io.javalin.openapi.HttpMethod;
import io.javalin.openapi.OpenApi;
import io.javalin.openapi.OpenApiResponse;
import io.javalin.openapi.OpenApiContent;
import net.laboulangerie.api.LaBoulangerieAPI;
import net.laboulangerie.api.models.NameUuidModel;

public class DonorsController {
    public static List<NameUuidModel> getDonorsArray() {
        List<NameUuidModel> donorsArray = new ArrayList<>();

        try {
            Gson gson = new Gson();
            File donorsFile = new File(LaBoulangerieAPI.PLUGIN.getDataFolder(), "donors.json");
            NameUuidModel[] donorsList = gson.fromJson(new FileReader(donorsFile), NameUuidModel[].class);
            donorsArray = Arrays.asList(donorsList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return donorsArray;
    }

    @OpenApi(description = "Get donors", operationId = "getDonors", path = "/donors", methods = HttpMethod.GET, tags = {
            "Donors" }, responses = {
                    @OpenApiResponse(status = "200", description = "Donors", content = {
                            @OpenApiContent(from = NameUuidModel[].class) })
            })
    public static void getDonors(Context ctx) {
        ctx.json(getDonorsArray());
    }
}
