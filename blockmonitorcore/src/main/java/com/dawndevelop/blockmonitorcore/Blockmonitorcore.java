package com.dawndevelop.blockmonitorcore;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

@Plugin(
        id = "blockmonitorcore",
        name = "Blockmonitorcore",
        dependencies = {@Dependency(id = "blockmonitorapi")}
)
public class Blockmonitorcore {

    @Inject
    private Logger logger;


    @Listener
    public void onServerStart(GameStartedServerEvent event) {
    }


    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event, @Getter("getTargetEntity") Player player) {
    }

}
