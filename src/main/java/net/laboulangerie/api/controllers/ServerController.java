package net.laboulangerie.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import io.javalin.http.Context;
import io.javalin.openapi.HttpMethod;
import io.javalin.openapi.OpenApi;
import io.javalin.openapi.OpenApiResponse;
import io.javalin.openapi.OpenApiContent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.laboulangerie.api.models.NameUuidModel;
import net.laboulangerie.api.models.ServerModel;

public class ServerController {
    @OpenApi(description = "Get server informations", operationId = "getServer", path = "/server", methods = HttpMethod.GET, tags = {
            "Server" }, responses = {
                    @OpenApiResponse(status = "200", description = "Server informations", content = {
                            @OpenApiContent(from = ServerModel.class) })
            })

    public static void getServer(Context ctx) {
        ServerModel model = new ServerModel();
        org.bukkit.Server bukkitServer = Bukkit.getServer();

        model.setName(bukkitServer.getName());
        model.setMotd(PlainTextComponentSerializer.plainText().serialize(bukkitServer.motd()));
        model.setVersion(bukkitServer.getVersion());
        model.setBukkitVersion(bukkitServer.getBukkitVersion());
        model.setMaxPlayers(bukkitServer.getMaxPlayers());

        List<Player> onlinePlayers = bukkitServer.getOnlinePlayers().stream().collect(Collectors.toList());
        List<NameUuidModel> onlinePlayerModels = new ArrayList<>();

        for (Player player : onlinePlayers) {
            NameUuidModel onlinePlayerModel = new NameUuidModel();

            onlinePlayerModel.setName(player.getName());
            onlinePlayerModel.setUuid(player.getUniqueId());

            onlinePlayerModels.add(onlinePlayerModel);
        }

        model.setOnlinePlayers(onlinePlayerModels);

        ctx.json(model);
    }
}
