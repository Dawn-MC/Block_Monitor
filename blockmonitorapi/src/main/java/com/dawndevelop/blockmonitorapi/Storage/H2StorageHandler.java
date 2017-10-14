package com.dawndevelop.blockmonitorapi.Storage;

import com.dawndevelop.blockmonitorapi.Events.Event;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.persistence.DataFormats;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class H2StorageHandler implements IStorageHandler {
    private HikariDataSource dataSource;

    @Override
    public void SetupStorageHandler(Map<String, Object> params) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2://" + params.get("dirpath") + "/blockmonitor");
        config.setDriverClassName("org.h2.Driver");
        config.setMaximumPoolSize(100);
        dataSource = new HikariDataSource(config);

        try (Connection con = dataSource.getConnection()){
            con.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS `block_monitor_events` (" +
                    "`id` BIGINT AUTO_INCREMENT NOT NULL," +
                    "`event_information` LONGTEXT," +
                    "`event_type` TEXT," +
                    "`timestamp` TIMESTAMP" +
                    ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ShutDownStorageHandler(Map<String, Object> params) {
        dataSource.close();
    }

    @Override
    public void Insert(Event event) {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "INSERT INTO `block_monitor_events` (`id`, `event_information`, `event_type`, `timestamp`) VALUES (?, ?, ?, ?);");

            preparedStatement.setLong(1, event.getId());
            event.toDataContainer().getView(DataQuery.of()).ifPresent(dataView -> {
                try {
                    preparedStatement.setString(2, DataFormats.JSON.write(dataView));
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            preparedStatement.setString(3, event.getEventType());
            preparedStatement.setTimestamp(4, event.getTimestamp());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Remove(long ID) {
        try (Connection con = dataSource.getConnection()){
            con.createStatement().execute(String.format("DELETE FROM `block_monitor_events` WHERE `id` = %d;", ID));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isFileBased() {
        return true;
    }

    @Override
    public String getDatabaseName() {
        return "h2";
    }
}
