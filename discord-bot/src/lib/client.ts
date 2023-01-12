import { SapphireClient, LogLevel, container } from "@sapphire/framework";
import { envParseString } from "@skyra/env-utilities";
import { ActivityType, GatewayIntentBits, WebhookClient } from "discord.js";
import { inProduction } from "./constants.js";
import { timed } from "./functions.js";

const client = new SapphireClient({
    loadDefaultErrorListeners: true,
    logger: { level: inProduction ? LogLevel.Info : LogLevel.Debug },
    hmr: { enabled: !inProduction },
    shards: "auto",
    presence: {
        activities: [
            { name: "play.kings-world.net", type: ActivityType.Playing },
        ],
    },
    intents: [GatewayIntentBits.Guilds, GatewayIntentBits.GuildMembers, GatewayIntentBits.GuildMessages, GatewayIntentBits.MessageContent],
    allowedMentions: { repliedUser: false },
});

container.webhook = new WebhookClient({
    url: envParseString("WEBHOOK_URL"),
});

export async function startClient() {
    const [result, took] = await timed(async () => {
        client.logger.info("Logging in");
        await client.login(envParseString("DISCORD_TOKEN"));
    });

    if (result.isErr()) {
        client.logger.fatal(result.unwrapErr());
        client.destroy();
        process.exit(1);
    }

    client.logger.info(`Logged in (took ${took})`);
}
