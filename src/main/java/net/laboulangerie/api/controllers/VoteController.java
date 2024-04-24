package net.laboulangerie.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.bencodez.votingplugin.VotingPluginMain;
import com.bencodez.votingplugin.objects.VoteSite;
import io.javalin.http.Context;
import io.javalin.openapi.HttpMethod;
import io.javalin.openapi.OpenApi;
import io.javalin.openapi.OpenApiResponse;
import io.javalin.openapi.OpenApiContent;

public class VoteController {
    public static List<String> getVotes() {
        List<VoteSite> sites = VotingPluginMain.getPlugin().getVoteSites();
        List<String> urls = sites.stream().filter(VoteSite::isEnabled).map((site) -> site.getVoteURL(false))
                .collect(Collectors.toList());
        return urls;
    }

    @OpenApi(description = "Get all vote sites", operationId = "getVotes", path = "/vote", methods = HttpMethod.GET, tags = {
            "Vote" }, responses = {
                    @OpenApiResponse(status = "200", description = "Vote URLs", content = {
                            @OpenApiContent(from = String[].class) })
            })
    public static void getVotes(Context ctx) {
        ctx.json(getVotes());
    }
}
