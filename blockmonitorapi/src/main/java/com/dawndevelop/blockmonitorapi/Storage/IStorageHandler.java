package com.dawndevelop.blockmonitorapi.Storage;

import com.dawndevelop.blockmonitorapi.Events.Event;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Date;
import java.util.Map;

public interface IStorageHandler {
    void SetupStorageHandler(Map<String, Object> params);

    void ShutDownStorageHandler(Map<String, Object> params);

    void Insert(Event event);

    void Remove(long ID);

    Event FindByLocation(Location<World> worldLocation, int xRadius, int yRadius, int zRadius);

    Event FindByWorld(World world);

    boolean Purge(Date date);

    boolean isFileBased();

    String getDatabaseName();
}
