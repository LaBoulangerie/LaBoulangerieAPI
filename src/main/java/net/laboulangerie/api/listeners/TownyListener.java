package net.laboulangerie.api.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.event.DeleteNationEvent;
import com.palmergames.bukkit.towny.event.DeleteTownEvent;
import com.palmergames.bukkit.towny.event.NewNationEvent;
import com.palmergames.bukkit.towny.event.NewTownEvent;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

import net.laboulangerie.api.LaBoulangerieAPI;
import net.laboulangerie.api.models.EventModel;
import net.laboulangerie.api.models.NameUuidModel;

public class TownyListener implements Listener {

    @EventHandler
    public void onNewTown(NewTownEvent event) {
        EventModel eventModel = new EventModel();
        eventModel.setEvent(event.getEventName());

        Town town = event.getTown();
        NameUuidModel townModel = new NameUuidModel();

        townModel.setName(town.getName());
        townModel.setUuid(town.getUUID());

        eventModel.put("town", townModel);
        LaBoulangerieAPI.broadcast(eventModel);
    }

    @EventHandler
    public void onNewNation(NewNationEvent event) {
        EventModel eventModel = new EventModel();
        eventModel.setEvent(event.getEventName());

        Nation nation = event.getNation();
        NameUuidModel nationModel = new NameUuidModel();

        nationModel.setName(nation.getName());
        nationModel.setUuid(nation.getUUID());

        eventModel.put("nation", nationModel);
        LaBoulangerieAPI.broadcast(eventModel);
    }

    @EventHandler
    public void onDeleteTown(DeleteTownEvent event) {
        EventModel eventModel = new EventModel();
        eventModel.setEvent(event.getEventName());

        NameUuidModel townModel = new NameUuidModel();

        townModel.setName(event.getTownName());
        townModel.setUuid(event.getTownUUID());

        Resident mayor = event.getMayor();
        NameUuidModel mayorModel = new NameUuidModel();

        mayorModel.setName(mayor.getName());
        mayorModel.setUuid(mayor.getUUID());

        eventModel.put("town", townModel);
        eventModel.put("mayor", mayorModel);
        LaBoulangerieAPI.broadcast(eventModel);
    }

    @EventHandler
    public void onDeleteNation(DeleteNationEvent event) {
        EventModel eventModel = new EventModel();
        eventModel.setEvent(event.getEventName());

        NameUuidModel nationModel = new NameUuidModel();

        nationModel.setName(event.getNationName());
        nationModel.setUuid(event.getNationUUID());

        Resident king = TownyAPI.getInstance().getResident(event.getNationKing());
        NameUuidModel kingModel = new NameUuidModel();

        kingModel.setName(king.getName());
        kingModel.setUuid(king.getUUID());

        eventModel.put("nation", nationModel);
        eventModel.put("king", kingModel);
        LaBoulangerieAPI.broadcast(eventModel);
    }
}
