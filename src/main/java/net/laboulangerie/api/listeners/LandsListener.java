package net.laboulangerie.api.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.angeschossen.lands.api.events.LandCreateEvent;
import me.angeschossen.lands.api.events.LandDeleteEvent;
import me.angeschossen.lands.api.events.nation.edit.NationCreateEvent;
import me.angeschossen.lands.api.events.nation.edit.NationDeleteEvent;
import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.nation.Nation;
import net.laboulangerie.api.LaBoulangerieAPI;
import net.laboulangerie.api.models.EventModel;
import net.laboulangerie.api.models.NameIdModel;

public class LandsListener implements Listener {

    @EventHandler
    public void onNewLand(LandCreateEvent event) {
        EventModel eventModel = new EventModel();
        eventModel.setEvent(event.getEventName());

        Land land = event.getLand();
        NameIdModel landModel = new NameIdModel(
                land.getName(),
                land.getULID().toString());

        eventModel.put("land", landModel);
        LaBoulangerieAPI.broadcast(eventModel);
    }

    @EventHandler
    public void onNewNation(NationCreateEvent event) {
        EventModel eventModel = new EventModel();
        eventModel.setEvent(event.getEventName());

        Nation nation = event.getNation();
        NameIdModel nationModel = new NameIdModel(
                nation.getName(),
                nation.getULID().toString());

        eventModel.put("nation", nationModel);
        LaBoulangerieAPI.broadcast(eventModel);
    }

    @EventHandler
    public void onDeleteLand(LandDeleteEvent event) {
        EventModel eventModel = new EventModel();
        eventModel.setEvent(event.getEventName());

        Land land = event.getLand();
        NameIdModel landModel = new NameIdModel(
                land.getName(),
                land.getULID().toString());

        eventModel.put("land", landModel);
        LaBoulangerieAPI.broadcast(eventModel);
    }

    @EventHandler
    public void onDeleteNation(NationDeleteEvent event) {
        EventModel eventModel = new EventModel();
        eventModel.setEvent(event.getEventName());

        Nation nation = event.getNation();
        NameIdModel nationModel = new NameIdModel(
                nation.getName(),
                nation.getULID().toString());

        eventModel.put("nation", nationModel);
        LaBoulangerieAPI.broadcast(eventModel);
    }
}
