package net.laboulangerie.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.openapi.HttpMethod;
import io.javalin.openapi.OpenApi;
import io.javalin.openapi.OpenApiResponse;
import io.javalin.openapi.OpenApiContent;
import io.javalin.openapi.OpenApiParam;
import net.laboulangerie.api.models.MmoModel;
import net.laboulangerie.api.models.NameUuidModel;
import net.laboulangerie.api.models.PlayerModel;
import net.laboulangerie.api.models.ResidentModel;
import net.laboulangerie.api.models.TalentModel;
import net.laboulangerie.laboulangeriemmo.LaBoulangerieMmo;
import net.laboulangerie.laboulangeriemmo.api.player.MmoPlayer;

public class PlayerController {

    public static List<NameUuidModel> getAllPlayers() {
        ArrayList<NameUuidModel> players = new ArrayList<>();
        OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();

        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            NameUuidModel model = new NameUuidModel();

            model.setName(offlinePlayer.getName());
            model.setUuid(offlinePlayer.getUniqueId());

            players.add(model);
        }

        return players;
    }

    @OpenApi(description = "Get all players", operationId = "getPlayers", path = "/player", methods = HttpMethod.GET, tags = {
            "Player" }, responses = {
                    @OpenApiResponse(status = "200", description = "All players", content = {
                            @OpenApiContent(from = NameUuidModel[].class) })
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
        playerModel.setLastSeen(offlinePlayer.isOnline() ? offlinePlayer.getLastLogin() : offlinePlayer.getLastSeen()); // Sometimes
                                                                                                                        // the
                                                                                                                        // server's
                                                                                                                        // time
                                                                                                                        // can
                                                                                                                        // be
                                                                                                                        // ahead
                                                                                                                        // the
                                                                                                                        // client
                                                                                                                        // making
                                                                                                                        // the
                                                                                                                        // request,
                                                                                                                        // leading
                                                                                                                        // to
                                                                                                                        // incoherent
                                                                                                                        // data

        Resident resident = TownyAPI.getInstance().getResident(offlinePlayer.getUniqueId());

        if (resident != null) {
            ResidentModel residentModel = new ResidentModel();

            Town town = resident.getTownOrNull();

            if (town != null) {
                NameUuidModel townModel = new NameUuidModel();

                townModel.setName(town.getName());
                townModel.setUuid(town.getUUID());

                residentModel.setTown(townModel);
            }

            Nation nation = resident.getNationOrNull();

            if (nation != null) {
                NameUuidModel nationModel = new NameUuidModel();

                nationModel.setName(nation.getName());
                nationModel.setUuid(nation.getUUID());

                residentModel.setNation(nationModel);
            }

            List<Resident> friends = resident.getFriends();

            if (!friends.isEmpty()) {

                ArrayList<NameUuidModel> friendsModelList = new ArrayList<>();
                for (Resident friend : friends) {
                    NameUuidModel friendModel = new NameUuidModel();

                    friendModel.setName(friend.getName());
                    friendModel.setUuid(friend.getUUID());

                    friendsModelList.add(friendModel);
                }

                residentModel.setFriends(friendsModelList);
            }

            residentModel.setIsMayor(resident.isMayor());
            residentModel.setIsKing(resident.isKing());
            residentModel.setTownRanks(resident.getTownRanks());
            residentModel.setNationRanks(resident.getNationRanks());
            residentModel.setSurname(resident.getSurname());
            residentModel.setTitle(resident.getTitle());
            residentModel.setPrefix(resident.getNamePrefix());
            residentModel.setPostfix(resident.getNamePostfix());
            residentModel.setFormattedName(resident.getFormattedName());

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
