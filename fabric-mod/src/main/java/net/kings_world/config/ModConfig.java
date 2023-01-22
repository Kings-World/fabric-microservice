package net.kings_world.config;

import org.bspfsystems.yamlconfiguration.file.YamlConfiguration;

import static net.kings_world.Utils.configFolder;

public class ModConfig {
    public final Yaml yaml;
    private final YamlConfiguration config;

    public ModConfig(String file) {
        this.yaml = new Yaml(configFolder().resolve(file).toFile(), file);
        this.config = yaml.getConfig();
        yaml.load();
    }

    public String getRedisHostname() {
        return config.getString("redis.hostname", "localhost");
    }

    public int getRedisPort() {
        return config.getInt("redis.port", 6379);
    }

    public String getRedisPassword() {
        return config.getString("redis.password", "");
    }
}
