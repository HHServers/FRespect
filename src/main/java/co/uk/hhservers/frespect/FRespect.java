package co.uk.hhservers.frespect;

import co.uk.hhservers.frespect.commands.FCommand;
import co.uk.hhservers.frespect.config.Config;
import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;

@Plugin(
        id = "frespect",
        name = "Frespect",
        url = "http://hhservers.co.uk",
        authors = {
                "blvxr"
        },
        description = "yes"
)
public class FRespect {

    private static FRespect instance;

    @Inject
    private Logger logger;

    @Listener
    public void onGameInit(GameInitializationEvent event) {
        instance = this;
        Sponge.getCommandManager().register(instance, FCommand.build(), "f", "frespect");
    }

    @Inject
    @DefaultConfig(sharedRoot = true)
    private File file;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> loader;

    @Listener
    public void onGamePreInit(GamePreInitializationEvent e) throws IOException {
        Config.setup(file, loader);
        Config.load();
        logger.info("PreInit");
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
    }

    @Listener
    public void onGameReload(GameReloadEvent e) throws IOException {
        logger.info("FRespect config reloaded.");
        Config.load();
    }
}
