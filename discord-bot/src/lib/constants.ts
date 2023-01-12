export const inProduction = process.env.NODE_ENV === "production";

export enum RedisChannel {
    DiscordToMinecraft = "discord-to-minecraft",
    MinecraftToDiscord = "minecraft-to-discord"
}
