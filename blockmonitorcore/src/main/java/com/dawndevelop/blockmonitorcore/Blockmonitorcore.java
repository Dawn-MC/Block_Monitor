package com.dawndevelop.blockmonitorcore;

import com.dawndevelop.blockmonitorcore.Events.EventHandler;
import com.dawndevelop.blockmonitorcore.Events.EventTypes;
import com.dawndevelop.blockmonitorcore.Events.Eventt;
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

    public Logger getLogger() {
        return logger;
    }

    @Listener
    public void onClientConnectionEvent(ClientConnectionEvent event){
        logger.info("Event called");

        EventHandler eh = new EventHandler();
        if (eh.HandleEvent(event)){
            logger.info("Event handled");
            eh.Submit();
        }else{
            logger.error("Event not handled");
        }




/*        if (event instanceof  ClientConnectionEvent.Join) {
            ClientConnectionEvent.Join jEvent = (ClientConnectionEvent.Join) event;
            DataContainer dataContainer = DataContainer.createNew();
            dataContainer.set(DataQuery.of("player"), jEvent.getTargetEntity().toContainer());
            dataContainer.set(DataQuery.of("message"), jEvent.getMessage().toPlain());
            Eventt eventt = Eventt.builder().dataContainer(dataContainer).worldLocation(jEvent.getTargetEntity().getLocation()).eventType(EventTypes.ClientConnectionEvent.name()).build();
        }*/
    }
}

