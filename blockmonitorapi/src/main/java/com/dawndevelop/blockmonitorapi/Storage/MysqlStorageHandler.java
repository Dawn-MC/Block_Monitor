package com.dawndevelop.blockmonitorapi.Storage;

import com.dawndevelop.blockmonitorapi.Events.Event;

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
    public boolean isFileBased() {
        return false;
    }

    @Override
    public String getDatabaseName() {
        return null;
    }
}
