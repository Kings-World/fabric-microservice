package net.kings_world.jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.minecraft.server.MinecraftServer;

public class JedisSubscriber {
    private JedisClient jedis;
    private PubSubListener jedisPubSub;
    private ExecutorService executor;

    public void subscribe(MinecraftServer server) {
        jedis = new JedisClient();
        jedisPubSub = new PubSubListener(server);

        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> jedis.subscribe(jedisPubSub, "discord-to-minecraft"));
    }

    public void unsubscribe() {
        jedisPubSub.unsubscribe();
        executor.shutdown();

        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(5, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
