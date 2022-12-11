package net.laboulangerie.api.controllers;

import java.util.ArrayList;
import java.util.List;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.openapi.HttpMethod;
import io.javalin.openapi.OpenApi;
import io.javalin.openapi.OpenApiResponse;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import net.laboulangerie.api.models.NameUuidModel;
import net.laboulangerie.api.models.TypedNameUuidModel;
import io.javalin.openapi.OpenApiContent;
import io.javalin.openapi.OpenApiParam;

public class SearchController {

    public static TypedNameUuidModel typedAs(NameUuidModel model, String type) {
        TypedNameUuidModel typedNameUuidModel = new TypedNameUuidModel();
        typedNameUuidModel.setName(model.getName());
        typedNameUuidModel.setUuid(model.getUuid());
        typedNameUuidModel.setType(type);

        return typedNameUuidModel;
    }

    @OpenApi(summary = "Search for player, town or nation by name", operationId = "search", path = "/search/{query}", pathParams = {
            @OpenApiParam(name = "query", description = "Search query") }, methods = HttpMethod.GET, tags = {
                    "Search" }, responses = {
                            @OpenApiResponse(status = "200", content = {
                                    @OpenApiContent(from = NameUuidModel[].class) })
                    })
    public static void search(Context ctx) {
        String query;

        try {
            query = ctx.pathParam("query");
        } catch (Exception e) {
            throw new BadRequestResponse();
        }

        List<TypedNameUuidModel> all = new ArrayList<>();
        NationController.getAllNations().forEach((n) -> {
            all.add(typedAs(n, "nation"));
        });
        ;
        TownController.getAllTowns().forEach((t) -> {
            all.add(typedAs(t, "town"));
        });
        ;
        PlayerController.getAllPlayers().forEach((p) -> {
            all.add(typedAs(p, "player"));
        });

        List<BoundExtractedResult<TypedNameUuidModel>> topExtractedResults = FuzzySearch.extractTop(query, all,
                x -> x.getName(),
                10);

        List<TypedNameUuidModel> topResults = new ArrayList<>();

        for (BoundExtractedResult<TypedNameUuidModel> res : topExtractedResults) {
            topResults.add(res.getReferent());
        }

        ctx.json(topResults);
    }
}
