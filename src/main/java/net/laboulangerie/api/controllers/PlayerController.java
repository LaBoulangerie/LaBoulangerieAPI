package net.laboulangerie.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.openapi.HttpMethod;
import io.javalin.openapi.OpenApi;
import io.javalin.openapi.OpenApiResponse;
import me.angeschossen.lands.api.applicationframework.util.ULID;
import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.nation.Nation;
import io.javalin.openapi.OpenApiContent;
import io.javalin.openapi.OpenApiParam;
import net.laboulangerie.api.LaBoulangerieAPI;
import net.laboulangerie.api.models.MmoModel;
import net.laboulangerie.api.models.NameIdModel;
import net.laboulangerie.api.models.PlayerModel;
import net.laboulangerie.api.models.ResidentModel;
import net.laboulangerie.api.models.TalentModel;
import net.laboulangerie.laboulangeriemmo.LaBoulangerieMmo;
import net.laboulangerie.laboulangeriemmo.api.player.MmoPlayer;

public class PlayerController {

    public static List<NameIdModel<UUID>> getAllPlayers() {
        ArrayList<NameIdModel<UUID>> players = new ArrayList<>();
        OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();

        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            NameIdModel<UUID> model = new NameIdModel<UUID>(offlinePlayer.getName(),
                    offlinePlayer.getUniqueId());
            players.add(model);
        }

        return players;
    }

    @OpenApi(description = "Get all players", operationId = "getPlayers", path = "/player", methods = HttpMethod.GET, tags = {
            "Player" }, responses = {
                    @OpenApiResponse(status = "200", description = "All players", content = {
                            @OpenApiContent(from = NameIdModel[].class) })
            })
    public static void getPlayers(Context ctx) {
        ctx.json(getAllPlayers());
    }

    @OpenApi(description = "Get player with name or UUID", operationId = "getPlayer", path = "/player/{identifier}", pathParams = {
            @OpenApiParam(name = "identifier", description = "Name or UUID of the player")
    }, methods = HttpMethod.GET, tags = {
            "Player" },

            responses = {
                    @OpenApiResponse(status = "404", description = "Player not found", content = {
                            @OpenApiContent(from = NotFoundResponse.class) }),
                    @OpenApiResponse(status = "200", description = "Player", content = {
                            @OpenApiContent(from = PlayerModel.class) })
            })

    public static void getPlayer(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        OfflinePlayer offlinePlayer;

        try {
            UUID uuid = UUID.fromString(identifier);
            offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        } catch (Exception ignored) {
            offlinePlayer = Bukkit.getOfflinePlayer(identifier);

        }

        if (!offlinePlayer.hasPlayedBefore()) {
            throw new NotFoundResponse();
        }

        PlayerModel playerModel = new PlayerModel();

        playerModel.setName(offlinePlayer.getName());
        playerModel.setUuid(offlinePlayer.getUniqueId());
        playerModel.setIsOnline(offlinePlayer.isOnline());
        playerModel.setFirstPlayed(offlinePlayer.getFirstPlayed());
        // Sometimes the server's time can be ahead the client making the request,
        // leading to incoherent data
        playerModel.setLastSeen(offlinePlayer.isOnline() ? offlinePlayer.getLastLogin() : offlinePlayer.getLastSeen());

        me.angeschossen.lands.api.player.OfflinePlayer resident = null;
        try {
            resident = LaBoulangerieAPI.LANDS_INTEGRATION
                    .getOfflineLandPlayer(offlinePlayer.getUniqueId()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to retrieve the resident information", e);
        }

        if (resident != null) {
            ResidentModel residentModel = new ResidentModel();

            // In Gaiartos, the player can only be in one land
            Land land = resident.getLands().iterator().hasNext() ? resident.getLands().iterator().next() : null;

            if (land != null) {
                NameIdModel<ULID> townModel = new NameIdModel<ULID>(
                        land.getName(),
                        land.getULID());
                residentModel.setLand(townModel);
                residentModel.setIsMayor(land.getOwnerUID().equals(offlinePlayer.getUniqueId()));

                Nation nation = land.getNation();

                if (nation != null) {
                    NameIdModel<ULID> nationModel = new NameIdModel<ULID>(
                            nation.getName(),
                            nation.getULID());
                    residentModel.setNation(nationModel);
                    residentModel.setIsKing(nation.getOwnerUID().equals(offlinePlayer.getUniqueId()));
                }
            }

            residentModel.setRoleName(land.getDefaultArea().getRole(offlinePlayer.getUniqueId()).getName());
            playerModel.setResident(residentModel);
        }

        MmoPlayer mmoPlayer = LaBoulangerieMmo.PLUGIN.getMmoPlayerManager()
                .getOfflinePlayer(offlinePlayer);

        if (mmoPlayer != null) {
            MmoModel mmoModel = new MmoModel();

            mmoModel.setPalier(mmoPlayer.getPalier());

            Set<String> talentStrings = LaBoulangerieMmo.talentsRegistry
                    .generateTalentsDataHolder().keySet();
            List<TalentModel> talentModels = new ArrayList<>();

            for (String talent : talentStrings) {
                TalentModel talentModel = new TalentModel();

                talentModel.setName(talent);
                talentModel.setLevel(mmoPlayer.getTalent(talent).getLevel());
                talentModel.setXp(mmoPlayer.getTalent(talent).getXp());
                talentModel.setXpToNextLevel(
                        mmoPlayer.getTalent(talent).getXpToNextLevel());
                talentModel.setMinLevelXp(mmoPlayer.getTalent(talent).getLevelXp());

                talentModels.add(talentModel);
            }

            mmoModel.setTalents(talentModels);
            playerModel.setMmo(mmoModel);
        }

        ctx.json(playerModel);
    }
}
