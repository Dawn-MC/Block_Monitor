package com.dawndevelop.blockmonitorapi.Events;

import com.dawndevelop.blockmonitorapi.BlockMonitorAPI;
import lombok.Getter;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.persistence.DataFormats;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.sql.Time;
import java.sql.Timestamp;

public interface Event  {

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    long getId();

    DataContainer toDataContainer();

    Location<World> getLocation();

    String getEventType();

    Timestamp getTimestamp();
}
