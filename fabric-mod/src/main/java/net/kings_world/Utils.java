package net.kings_world;

import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.kings_world.config.ModConfig;
import net.minecraft.entity.Entity;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static net.kings_world.KingsWorld.*;

public class Utils {
    public static void printBanner() {
        logger.info("_  _ _ _  _ ____ ____    _ _ _ ____ ____ _    ___  ");
        logger.info("|_/  | |\\ | | __ [__     | | | |  | |__/ |    |  \\ ");
        logger.info("| \\_ | | \\| |__] ___]    |_|_| |__| |  \\ |___ |__/ ");
        logger.info("                                                   ");
    }

    public static Path configFolder() {
        return FabricLoader.getInstance().getConfigDir().resolve(modId);
    }

    public static void publishViaPlayer(Entity entity, ModConfig.Message message, HashReplacer hashMap) {
        if (message.isDisabled()) return;
        JsonObject json = new JsonObject();
        HashReplacer avatarHash = new HashReplacer();
        avatarHash.put("uuid", entity.getUuid().toString());

        json.addProperty("avatar", hashReplace(modConfig.getAvatarUrl(), avatarHash));
        json.addProperty("name", entity.getEntityName());
        json.addProperty("content", message.display(hashMap));

        publishToChannel(json.toString());
    }

    private static void publishToChannel(String message) {
        if (isShuttingDown) return;
        publisher.publish("minecraft-to-discord", message);
    }

    public static void publish(ModConfig.Message message) {
        if (message.isDisabled()) return;
        publishToChannel(message.getContent());
    }

    public static String hashReplace(String content, HashReplacer hashMap) {
        String newContent = content;
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            newContent = newContent.replaceAll("\\{" + entry.getKey() + "}", entry.getValue());
        }
        return newContent;
    }

    public static class HashReplacer extends HashMap<String, String> { }
}
