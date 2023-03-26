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
import net.laboulangerie.api.models.TypedNameUuidModel;

public class StaffController {
    public static List<TypedNameUuidModel> getStaffArray() {
        List<TypedNameUuidModel> staffArray = new ArrayList<>();

        try {
            Gson gson = new Gson();
            File staffFile = new File(LaBoulangerieAPI.PLUGIN.getDataFolder(), "staff.json");
            TypedNameUuidModel[] staffList = gson.fromJson(new FileReader(staffFile), TypedNameUuidModel[].class);
            staffArray = Arrays.asList(staffList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return staffArray;
    }

    @OpenApi(description = "Get staff members", operationId = "getStaff", path = "/staff", methods = HttpMethod.GET, tags = {
            "Staff" }, responses = {
                    @OpenApiResponse(status = "200", description = "Staff members", content = {
                            @OpenApiContent(from = TypedNameUuidModel[].class) })
            })
    public static void getStaff(Context ctx) {
        ctx.json(getStaffArray());
    }
}
