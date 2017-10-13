package com.dawndevelop.blockmonitorapi.Events;

import com.dawndevelop.blockmonitorapi.BlockMonitorAPI;
import lombok.Getter;
import org.spongepowered.api.data.DataContainer;

import java.sql.Time;
import java.sql.Timestamp;

public interface Event {
    long id = BlockMonitorAPI.getSnowflake().next();

    long getId();

    DataContainer toDataContainer();

    String getEventType();

    Timestamp getTimestamp();
}
