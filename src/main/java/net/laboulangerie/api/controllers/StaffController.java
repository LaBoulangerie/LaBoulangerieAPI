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
import net.laboulangerie.api.LaBoulangerieAPI;
import net.laboulangerie.api.database.GsonFiles;
import net.laboulangerie.api.models.TypedNameUuidModel;

public class StaffController {
    private static File staffFile = new File(LaBoulangerieAPI.PLUGIN.getDataFolder(), "staff.json");

    public static List<TypedNameUuidModel> getStaffArray() {
        List<TypedNameUuidModel> staffArray = new ArrayList<>();

        try {
            staffArray = Arrays.asList(GsonFiles.readArray(staffFile));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return staffArray;
    }

    @OpenApi(description = "Get staff members", operationId = "getStaff", path = "/staff", methods = HttpMethod.GET, tags = {
            "Staff", "Admin" }, responses = {
                    @OpenApiResponse(status = "200", description = "Staff members", content = {
                            @OpenApiContent(from = TypedNameUuidModel[].class) })
            })
    public static void getStaff(Context ctx) {
        ctx.json(getStaffArray());
    }

    @OpenApi(description = "Add staff", operationId = "addStaff", path = "/staff", methods = HttpMethod.POST, tags = {
            "Staff" })
    public static void addStaff(Context ctx) {
        DecodedJWT decodedJWT = LaBoulangerieAPI.JWT_MANAGER.getJwtFromContext(ctx);
        if (decodedJWT == null)
            return;

        TypedNameUuidModel newStaff = new Gson().fromJson(ctx.body(), TypedNameUuidModel.class);

        List<TypedNameUuidModel> staffArray = new ArrayList<>(getStaffArray());

        // Check if staff already exists
        OptionalInt index = IntStream.range(0, staffArray.size())
                .filter(i -> staffArray.get(i).getUuid().equals(newStaff.getUuid()))
                .findFirst();

        if (index.isPresent()) {
            // Staff already present, deleting and readding with updated values.
            staffArray.remove(index.getAsInt());
        }

        staffArray.add(newStaff);

        staffArray.sort(new Comparator<TypedNameUuidModel>() {
            @Override
            public int compare(TypedNameUuidModel o1, TypedNameUuidModel o2) {
                return o1.getType().compareTo(o2.getType());
            }
        });

        try {
            GsonFiles.writeArray(staffFile, staffArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OpenApi(description = "Delete staff", operationId = "deleteStaff", path = "/staff", methods = HttpMethod.DELETE, tags = {
            "Staff", "Admin" })
    public static void deleteStaff(Context ctx) {
        DecodedJWT decodedJWT = LaBoulangerieAPI.JWT_MANAGER.getJwtFromContext(ctx);
        if (decodedJWT == null)
            return;

        String staffUuid = ctx.formParam("uuid");
        List<TypedNameUuidModel> staffArray = getStaffArray();

        staffArray.removeIf(d -> d.getUuid().toString() == staffUuid);

        try {
            GsonFiles.writeArray(staffFile, staffArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}