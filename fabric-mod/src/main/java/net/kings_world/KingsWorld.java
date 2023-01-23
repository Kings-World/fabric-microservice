package net.kings_world;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.kings_world.config.ModConfig;
import net.kings_world.jedis.JedisClient;
import net.kings_world.jedis.JedisSubscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.kings_world.Utils.*;

public class KingsWorld implements ModInitializer {
    public static final String modId = "kings-world";
    public static final Logger logger = LoggerFactory.getLogger(modId);
    public static final JedisSubscriber redis = new JedisSubscriber();
    public static boolean isShuttingDown = false;
    public static JedisClient publisher;
    public static ModConfig modConfig;

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            loadConfig();
            redis.subscribe(server);
            publish(":white_check_mark: The server has started!");
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            publish(":octagonal_sign: The server has stopped!");
            isShuttingDown = true;
            redis.unsubscribe();
            publisher.close();
        });

        ServerMessageEvents.CHAT_MESSAGE.register((message, sender, typeKey) ->
            publishViaPlayer(sender, String.format(
                "%s: %s",
                sender.getName().getString(),
                message.getSignedContent().plain()
            ))
        );
    }

    public void loadConfig() {
        printBanner();
        modConfig = new ModConfig("config.yml");
        publisher = new JedisClient();
    }
}