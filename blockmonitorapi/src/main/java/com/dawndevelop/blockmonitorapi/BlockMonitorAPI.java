package com.dawndevelop.blockmonitorapi;

import com.dawndevelop.blockmonitorapi.Storage.IStorageHandler;
import com.dawndevelop.blockmonitorapi.Storage.StorageType;
import com.google.inject.Inject;
import com.relops.snowflake.Snowflake;
import lombok.Getter;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Plugin(
        id = "blockmonitorapi",
        name = "BlockMonitorAPI"
)
public class BlockMonitorAPI {

    @Inject
    public Logger logger;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private Path defaultConfig;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path privateConfigDir;

    @Getter
    private static IStorageHandler iStorageHandler;

    public static Snowflake snowflake;

    private File dependenciesDir = new File(File.separatorChar + "mods" + File.separatorChar + "dependencies");

    private File snowflakeDep = new File(dependenciesDir, "snowflake:1.1.jar");

    @Listener
    public void PreInit(GamePreInitializationEvent event) throws URISyntaxException {

        if (!dependenciesDir.exists()){
            if (!dependenciesDir.mkdir()){
                System.err.println("Directory creation failed");
            }
        }

        if (!snowflakeDep.exists()){

            try {
                snowflakeDep.createNewFile();
                URL website = new URL("https://oss.sonatype.org/content/repositories/releases/com/relops/snowflake/1.1/snowflake-1.1.jar");
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream(snowflakeDep);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
                rbc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
                IStorageHandler StorageHandler = iStorageHandlerOptional.get();

                if (StorageHandler.isFileBased()){
                    Map<String, Object> objectMap = new TreeMap<>();
                    objectMap.put("dirpath", privateConfigDir.toAbsolutePath());

                    StorageHandler.SetupStorageHandler(objectMap);
                } else {
                    Map<String, Object> objectMap = new TreeMap<>();
                    objectMap.put("ip", commentedConfigurationNode.getNode("config", "database", "ip").getString());
                    objectMap.put("username", commentedConfigurationNode.getNode("config", "database", "username").getString());
                    objectMap.put("password", commentedConfigurationNode.getNode("config", "database", "password").getString());
                    StorageHandler.SetupStorageHandler(objectMap);
                }

                iStorageHandler = StorageHandler;
                logger.info("Block monitor PreInited successfully, database connections have been handled loading will now continue!");
            }else {
                logger.error("Block monitor couldn't preinit! most likely a invalid database type!");
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("Block monitor PreInited failed please make sure your details are correct in the config and if so report this!");
        }
    }
}