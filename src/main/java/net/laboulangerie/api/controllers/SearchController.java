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
import net.laboulangerie.api.models.NameIdModel;
import net.laboulangerie.api.models.TypedNameIdModel;
import io.javalin.openapi.OpenApiContent;
import io.javalin.openapi.OpenApiParam;

public class SearchController {
    @OpenApi(description = "Search for player, land or nation by name", operationId = "search", path = "/search/{query}", pathParams = {
            @OpenApiParam(name = "query", description = "Search query") }, methods = HttpMethod.GET, tags = {
                    "Search" }, responses = {
                            @OpenApiResponse(status = "200", description = "Query results", content = {
                                    @OpenApiContent(from = NameIdModel[].class) })
                    })
    public static void search(Context ctx) {
        String query;

        try {
            query = ctx.pathParam("query");
        } catch (Exception e) {
            throw new BadRequestResponse();
        }

        List<TypedNameIdModel> all = new ArrayList<>();
        NationController.getAllNations().forEach((n) -> {
            all.add(new TypedNameIdModel(n.getName(), n.getId(), "nation"));
        });

        LandController.getAllLands().forEach((t) -> {
            all.add(new TypedNameIdModel(t.getName(), t.getId(), "land"));
        });

        PlayerController.getAllPlayers().forEach((p) -> {
            all.add(new TypedNameIdModel(p.getName(), p.getId(), "player"));
        });

        List<BoundExtractedResult<TypedNameIdModel>> topExtractedResults = FuzzySearch.extractTop(query, all,
                x -> x.getName(),
                10);

        List<TypedNameIdModel> topResults = new ArrayList<>();

        for (BoundExtractedResult<TypedNameIdModel> res : topExtractedResults) {
            topResults.add(res.getReferent());
        }

        ctx.json(topResults);
    }
}
