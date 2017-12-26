package com.dawndevelop.blockmonitorapi.Storage;

import com.dawndevelop.blockmonitorapi.Events.Event;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.persistence.DataFormats;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

public class H2StorageHandler implements IStorageHandler {
    private HikariDataSource dataSource;

    @Override
    public void SetupStorageHandler(Map<String, Object> params) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2://" + params.get("dirpath"));

        config.setDriverClassName("org.h2.Driver");
        config.setMaximumPoolSize(100);
        dataSource = new HikariDataSource(config);

        try (Connection con = dataSource.getConnection()){
            con.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS `block_monitor_events` (" +
                    "`id` BIGINT AUTO_INCREMENT NOT NULL," +
                    "`event_information` LONGTEXT," +
                    "`event_type` TEXT," +
                    "`timestamp` TIMESTAMP," +
                            "`locX` INT," +
                            "`locY` INT," +
                            "`locZ` INT," +
                            "`worldId` CHAR(40)" +
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
            if (event == null){
                System.err.println("event was null! returning.");
                return;
            }

            if (event.getId() == 0){
                System.err.println("getId was null! returning.");
                return;
            }

            PreparedStatement preparedStatement = con.prepareStatement(
                    "INSERT INTO `block_monitor_events` (`id`, `event_information`, `event_type`, `timestamp`, `locX`, `locY`, `locZ`, `worldId`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");


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
            preparedStatement.setInt(5, event.getLocation().getBlockX());
            preparedStatement.setInt(6, event.getLocation().getBlockY());
            preparedStatement.setInt(7, event.getLocation().getBlockZ());
            preparedStatement.setString(8, event.getLocation().getExtent().getUniqueId().toString());

            preparedStatement.execute();

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
        return true;
    }

    @Override
    public String getDatabaseName() {
        return "h2";
    }
}
