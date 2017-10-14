package com.dawndevelop.blockmonitorapi;

import com.dawndevelop.blockmonitorapi.Storage.IStorageHandler;
import com.dawndevelop.blockmonitorapi.Storage.StorageType;
import com.google.inject.Inject;
import com.relops.snowflake.Snowflake;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Plugin(
        id = "blockmonitorapi",
        name = "BlockMonitorAPI"
)
public class BlockMonitorAPI {

    @Inject
    private Logger logger;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private Path defaultConfig;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path privateConfigDir;

    @lombok.Getter
    private static IStorageHandler iStorageHandler;

    @lombok.Getter
    private static Snowflake snowflake;

    @Listener
    public void PreInit(GamePreInitializationEvent event){
        if (Files.notExists(defaultConfig)){
            logger.info("Block monitors config could not be found creating new one");
            Sponge.getAssetManager().getAsset(this,"config.hocon").ifPresent(asset -> {
                try {
                    asset.copyToFile(defaultConfig, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        ConfigurationLoader<CommentedConfigurationNode> loader =
                HoconConfigurationLoader.builder().setPath(defaultConfig).build();

        try {
            CommentedConfigurationNode commentedConfigurationNode = loader.load();

            snowflake = new Snowflake(commentedConfigurationNode.getNode("config", "snowflake", "machine_id").getInt());

            StorageType storageType = StorageType.valueOf(commentedConfigurationNode.getNode("config", "database", "type").getString().toLowerCase());
            Optional<IStorageHandler> iStorageHandlerOptional = storageType.getIStorageHandler();
            if (iStorageHandlerOptional.isPresent()){
                IStorageHandler iStorageHandler = iStorageHandlerOptional.get();

                if (iStorageHandler.isFileBased()){
                    Map<String, Object> objectMap = new TreeMap<>();
                    objectMap.put("dirpath", privateConfigDir.toAbsolutePath());

                    iStorageHandler.SetupStorageHandler(objectMap);
                } else {
                    Map<String, Object> objectMap = new TreeMap<>();
                    objectMap.put("ip", commentedConfigurationNode.getNode("config", "database", "ip").getString());
                    objectMap.put("username", commentedConfigurationNode.getNode("config", "database", "username").getString());
                    objectMap.put("password", commentedConfigurationNode.getNode("config", "database", "password").getString());
                    iStorageHandler.SetupStorageHandler(objectMap);
                }
            }
            logger.info("Block monitor PreInited successfully, database connections have been handled loading will now continue!");
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("Block monitor PreInited failed please make sure your details are correct in the config and if so report this!");
        }
    }
}