package com.dawndevelop.blockmonitorcore.Events;

import com.dawndevelop.blockmonitorapi.BlockMonitorAPI;
import com.dawndevelop.blockmonitorcore.Blockmonitorcore;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.entity.ai.task.builtin.creature.horse.RunAroundLikeCrazyAITask;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.util.Objects;

public class EventHandler {

    private Eventt eventt;

    public boolean HandleEvent(Event event){
        if (event instanceof  ClientConnectionEvent.Join) {
            ClientConnectionEvent.Join jEvent = (ClientConnectionEvent.Join) event;
            DataContainer dataContainer = DataContainer.createNew();
            dataContainer.set(DataQuery.of("player"), jEvent.getTargetEntity().toContainer());
            dataContainer.set(DataQuery.of("message"), jEvent.getMessage().toPlain());
            eventt = Eventt.builder().dataContainer(dataContainer).worldLocation(jEvent.getTargetEntity().getLocation()).eventType(EventTypes.ClientConnectionEvent.name()).build();
            return true;
        }

        return false;
    }

    public void Submit(){
        if (this.eventt == null){
            throw new IllegalStateException("Critical error: eventt cannot be null at the submit stage");
        }

        BlockMonitorAPI.getIStorageHandler().Insert(this.eventt);
    }
}
