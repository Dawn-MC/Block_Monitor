package com.dawndevelop.blockmonitorcore.Events;

import com.dawndevelop.blockmonitorapi.Events.Event;
import lombok.Builder;
import lombok.Data;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.sql.Timestamp;
import java.util.Optional;

@Builder
public class ClientEvent implements Event {

    public static Optional<ClientEvent> FromEvent(ClientConnectionEvent event){
        if (event instanceof ClientConnectionEvent.Join){
            ClientConnectionEvent.Join joinEvent = (ClientConnectionEvent.Join)event;

            DataContainer dataContainer = DataContainer.createNew();
            dataContainer.set(DataQuery.of("message"), joinEvent.getMessage().toPlain());
            dataContainer.set(DataQuery.of("target"), joinEvent.getTargetEntity().toContainer());

            
        }else if (event instanceof ClientConnectionEvent.Disconnect){

        }
        return Optional.empty();
    }

    private DataContainer dataContainer;

    private String eventType;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public DataContainer toDataContainer() {
        return null;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public Timestamp getTimestamp() {
        return timestamp;
    }
}
