package net.kings_world.jedis;

import redis.clients.jedis.Jedis;

import static net.kings_world.KingsWorld.modConfig;

public class JedisClient extends Jedis {
    public JedisClient() {
        super(modConfig.getRedisHostname(), modConfig.getRedisPort());
        if (!modConfig.getRedisPassword().isBlank()) {
            super.auth(modConfig.getRedisPassword());
        }
    }
}
