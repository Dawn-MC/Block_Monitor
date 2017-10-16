package com.dawndevelop.blockmonitorapi.Storage;

import com.dawndevelop.blockmonitorapi.Events.Event;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Date;
import java.util.Map;

public class MysqlStorageHandler implements IStorageHandler {
    @Override
    public void SetupStorageHandler(Map<String, Object> params) {

    }

    @Override
    public void ShutDownStorageHandler(Map<String, Object> params) {

    }

    @Override
    public void Insert(Event event) {

    }

    @Override
    public void Remove(long ID) {

    }

    @Override
    public Event FindByLocation(Location<World> worldLocation, int xRadius, int yRadius, int zRadius) {
        return null;
    }

    @Override
    public Event FindByWorld(World world) {
        return null;
    }

    @Override
    public boolean Purge(Date date) {
        return false;
    }

    @Override
    public boolean isFileBased() {
        return false;
    }

    @Override
    public String getDatabaseName() {
        return null;
    }
}
