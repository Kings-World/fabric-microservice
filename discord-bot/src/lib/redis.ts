import Redis, { RedisOptions } from "ioredis";
import { envParseString, envParseInteger, envParseBoolean } from "@skyra/env-utilities";
import { container } from "@sapphire/framework";
import { RedisChannel } from "./constants.js";
import { fetchChannel, parseMessageJson, isValidJson } from "./functions.js";

export async function connectToRedis() {
    const options: RedisOptions = {
        host: envParseString("REDIS_HOST", "127.0.0.1"),
        port: envParseInteger("REDIS_PORT", 6379),
        password: envParseString("REDIS_PASSWORD", null) ?? undefined,
        tls: envParseBoolean("REDIS_TLS", false) ? {} : undefined,
        db: envParseInteger("REDIS_DB", 0),
    }

    container.logger.info(`Connecting to Redis (${options.host}:${options.port})`);
    container.subscriber = new Redis(options);
    container.publisher = new Redis(options);
}

export function subscribeToMinecraft(name = RedisChannel.MinecraftToDiscord) {
    const { subscriber, logger, webhook } = container;
    subscriber.subscribe(name);
    subscriber.on("message", async (redisChannel, message) => {
        logger.debug(`Received message from channel ${redisChannel}: ${message}`);
        if (name !== redisChannel) return;

        if (isValidJson(message)) {
            const data = parseMessageJson(message);
            await webhook.send({
                username: data.name,
                avatarURL: data.avatar,
                content: data.content,
                allowedMentions: { parse: ["roles", "users"] }
            });
        } else {
            const channel = await fetchChannel();
            await channel.send({ content: message });
        }
    });
}
