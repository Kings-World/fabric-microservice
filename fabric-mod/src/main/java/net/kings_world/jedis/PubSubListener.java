package net.kings_world.jedis;

import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import redis.clients.jedis.JedisPubSub;

import static net.kings_world.KingsWorld.logger;

public class PubSubListener extends JedisPubSub {
    private final MinecraftServer server;

    public PubSubListener(MinecraftServer server) {
        this.server = server;
    }
    
    public void onSubscribe(String channel, int subscribedChannels) {
        logger.info(String.format("Subscribed to %s", channel));
    }
    
    public void onUnsubscribe(String channel, int subscribedChannels) {
        logger.info(String.format("Unsubscribed from %s", channel));
    }

    public void onMessage(String channel, String message) {       
        this.server.getPlayerManager().broadcast(Text.of(message), false);
    }
}
