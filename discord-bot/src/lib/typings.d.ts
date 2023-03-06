import { IntegerString, BooleanString } from "@skyra/env-utilities";
import type { WebhookClient } from "discord.js";
import type { Redis } from "ioredis";
import type { fetchChannel } from "./functions.js";

declare module "@skyra/env-utilities" {
    interface Env {
        // discord
        DISCORD_TOKEN: string;
        WEBHOOK_URL: string;
        CHANNEL_ID: string;

        // redis
        REDIS_HOST: string;
        REDIS_PORT: IntegerString;
        REDIS_PASSWORD: string;
        REDIS_TLS: BooleanString;
        REDIS_DB: IntegerString;
    }
}

declare module "@sapphire/pieces" {
    interface Container {
        subscriber: Redis;
        publisher: Redis;
        webhook: WebhookClient;
    }
}
