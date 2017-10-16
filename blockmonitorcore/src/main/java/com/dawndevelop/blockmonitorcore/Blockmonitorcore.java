package com.dawndevelop.blockmonitorcore;

import com.dawndevelop.blockmonitorcore.Events.EventHandler;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.DataFormats;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.IOException;

@Plugin(
        id = "blockmonitorcore",
        name = "Blockmonitorcore",
        version = "1.0.0",
        description = "block logging plugin",
        dependencies = {@Dependency(id = "blockmonitorapi")}
)
public class Blockmonitorcore {

    @Inject
    private Logger logger;

    @Listener
    public void onClientConnectionEvent(ClientConnectionEvent event){
        logger.info("Event called");
        new EventHandler(event).Submit();
    }
}
