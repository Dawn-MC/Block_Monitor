package com.dawndevelop.blockmonitorapi.Storage;

import com.dawndevelop.blockmonitorapi.Events.Event;

import java.util.Map;

public interface IStorageHandler {
    void SetupStorageHandler(Map<String, Object> params);

    void ShutDownStorageHandler(Map<String, Object> params);

    void Insert(Event event);

    void Remove(long ID);

    boolean isFileBased();

    String getDatabaseName();
}
