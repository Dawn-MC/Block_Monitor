package com.dawndevelop.blockmonitorcore.Events;

import com.dawndevelop.blockmonitorapi.BlockMonitorAPI;
import com.dawndevelop.blockmonitorapi.Events.Event;
import lombok.Builder;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.sql.Timestamp;
import java.util.Objects;

@Builder
public class Eventt implements Event {

    private DataContainer dataContainer;

    private Location<World> worldLocation;

    private String eventType;

    private long id;

    @Override
    public long getId() {

        if (id == 0){
            id = BlockMonitorAPI.snowflake.next();
        }

        return id;
    }

    @Override
    public DataContainer toDataContainer() {
        return dataContainer;
    }

    @Override
    public Location<World> getLocation() {
        return worldLocation;
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
