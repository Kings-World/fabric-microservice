package net.kings_world;

import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;

import java.nio.file.Path;

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

    public static void publishViaPlayer(Entity entity, String message) {
        JsonObject json = new JsonObject();
        json.addProperty("avatar", String.format(
                "https://crafatar.com/avatars/%s?size=128&overlay",
                entity.getUuid().toString()
        ));
        json.addProperty("name", entity.getEntityName());
        json.addProperty("content", message);
        publish(json.toString());
    }

    public static void publish(String message) {
        publisher.publish("minecraft-to-discord", message);
    }
}
