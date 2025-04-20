package net.laboulangerie.api.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

public class StaffController {
    private static File staffFile = new File(LaBoulangerieAPI.PLUGIN.getDataFolder(), "staff.json");
    private static List<String> staffHierarchy = Arrays.asList("owner", "mod", "multi", "dev", "builder", "cm",
            "contributor");

    public static List<TypedNameIdModel> getStaffArray() {
        List<TypedNameIdModel> staffArray = new ArrayList<>();

        try {
            staffArray = Arrays.asList(GsonFiles.readArray(staffFile));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return staffArray;
    }

    @OpenApi(description = "Get staff members", operationId = "getStaff", path = "/staff", methods = HttpMethod.GET, tags = {
            "Staff", }, responses = {
                    @OpenApiResponse(status = "200", description = "Staff members", content = {
                            @OpenApiContent(from = TypedNameIdModel[].class) })
            })
    public static void getStaff(Context ctx) {
        ctx.json(getStaffArray());
    }

    @OpenApi(description = "Add staff", operationId = "addStaff", path = "/staff", methods = HttpMethod.POST, tags = {
            "Staff" }, requestBody = @OpenApiRequestBody(content = {
                    @OpenApiContent(from = TypedNameIdModel.class) }))
    public static void addStaff(Context ctx) {
        DecodedJWT decodedJWT = LaBoulangerieAPI.JWT_MANAGER.getJwtFromContext(ctx);
        if (decodedJWT == null)
            return;

        TypedNameIdModel newStaff = new Gson().fromJson(ctx.body(),
                new com.google.gson.reflect.TypeToken<TypedNameIdModel>() {
                }.getType());
        List<TypedNameIdModel> staffArray = new ArrayList<>(getStaffArray());

        // Check if staff already exists
        OptionalInt index = IntStream.range(0, staffArray.size())
                .filter(i -> staffArray.get(i).getId().equals(newStaff.getId()))
                .findFirst();

        if (index.isPresent()) {
            // Staff already present, deleting and readding with updated values.
            staffArray.remove(index.getAsInt());
        }

        staffArray.add(newStaff);

        staffArray.sort(new Comparator<TypedNameIdModel>() {
            @Override
            public int compare(TypedNameIdModel o1, TypedNameIdModel o2) {
                return Integer.compare(staffHierarchy.indexOf(o1.getType()), staffHierarchy.indexOf(o2.getType()));
            }
        });

        try {
            GsonFiles.writeArray(staffFile, staffArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OpenApi(description = "Delete staff", operationId = "deleteStaff", path = "/staff", methods = HttpMethod.DELETE, tags = {
            "Staff" }, requestBody = @OpenApiRequestBody(content = {
                    @OpenApiContent(from = TypedNameIdModel.class) }))
    public static void deleteStaff(Context ctx) {
        DecodedJWT decodedJWT = LaBoulangerieAPI.JWT_MANAGER.getJwtFromContext(ctx);
        if (decodedJWT == null)
            return;

        TypedNameIdModel staffToDelete = new Gson().fromJson(ctx.body(),
                new com.google.gson.reflect.TypeToken<TypedNameIdModel>() {
                }.getType());
        List<TypedNameIdModel> staffArray = new ArrayList<>(getStaffArray());

        staffArray.removeIf(
                d -> (d.getId().equals(staffToDelete.getId()))
                        || (d.getName().equals(staffToDelete.getName())));

        try {
            GsonFiles.writeArray(staffFile, staffArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}