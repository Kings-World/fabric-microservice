package net.kings_world.config;

import net.kings_world.Utils;
import org.bspfsystems.yamlconfiguration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import static net.kings_world.Utils.configFolder;
import static net.kings_world.Utils.hashReplace;

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

    public String getAvatarUrl() {
        return config.getString("avatar_url", "https://crafthead.net/helm/{uuid}");
    }

    public final Message serverStarted = new Message(
        "server_started",
        ":white_check_mark: The server has started!"
    );

    public final Message serverStopped = new Message(
        "server_stopped",
        ":octagonal_sign: The server has stopped!"
    );

    public final Message chatMessage = new Message(
        "chat_message",
        "{name}: {content}"
    );

    public final Message playerJoin = new Message(
        "player_join",
        ":arrow_right: {name} has joined!"
    );

    public final Message playerLeave = new Message(
        "player_leave",
        ":arrow_left: {name} has left!"
    );

    public final Message playerDeath = new Message(
        "player_death",
        ":skull: {message}"
    );

    public final Message playerAdvancement = new Message(
        "player_advancement",
        ":medal: {name} has completed the advancement **{title}**!"
    );

    public class Message {
        public final String path;
        public final String def;

        public Message(String path, @Nullable String def) {
            this.path = path;
            this.def = def;
        }

        public String getContent() {
            return config.getString("messages." + path + ".content", def);
        }

        public boolean isDisabled() {
            return !config.getBoolean("messages." + path + ".enabled", true);
        }

        public String display(Utils.HashReplacer hashMap) {
            return hashReplace(getContent(), hashMap);
        }
    }
}
