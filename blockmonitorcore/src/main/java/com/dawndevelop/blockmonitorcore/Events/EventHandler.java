package com.dawndevelop.blockmonitorcore.Events;

import com.dawndevelop.blockmonitorapi.BlockMonitorAPI;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.entity.ai.task.builtin.creature.horse.RunAroundLikeCrazyAITask;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class EventHandler {

    @Inject
    private Logger logger;

    protected Eventt eventt;

    public EventHandler(Event event){
        if (event instanceof ClientConnectionEvent.Join){
            ClientConnectionEvent.Join clientConnectionEventJoin = (ClientConnectionEvent.Join) event;
            DataContainer dataContainer = DataContainer.createNew();
            dataContainer.set(DataQuery.of("player"), clientConnectionEventJoin.getTargetEntity().toContainer());
            dataContainer.set(DataQuery.of("message"), clientConnectionEventJoin.getMessage().toPlain());
            eventt = Eventt.builder().eventType(EventTypes.ClientConnectionEvent.name()).dataContainer(dataContainer).worldLocation(clientConnectionEventJoin.getTargetEntity().getLocation()).build();
        }
    }

    public void Submit(){
        BlockMonitorAPI.getIStorageHandler().Insert(eventt);
    }
}
