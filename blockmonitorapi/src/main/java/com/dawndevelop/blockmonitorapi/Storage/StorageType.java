package com.dawndevelop.blockmonitorapi.Storage;

import java.util.Optional;

public enum StorageType {
    h2,
    mysql;

    public Optional<IStorageHandler>getIStorageHandler(){
        switch (this){
            case h2:
                return Optional.of(new H2StorageHandler());

            case mysql:
                return Optional.of(new MysqlStorageHandler());

            default:
                return Optional.empty();
        }
    }
}
