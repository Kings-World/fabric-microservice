import { container } from "@sapphire/framework";
import { Result } from "@sapphire/result";
import { Stopwatch } from "@sapphire/stopwatch";
import { envParseString } from "@skyra/env-utilities";
import type { Awaitable } from "discord.js";

export async function timed<T>(
    fn: () => Awaitable<T>
): Promise<[Result<T, unknown>, Stopwatch]> {
    const stopwatch = new Stopwatch();

    stopwatch.start();
    const value = await Result.fromAsync(fn);
    stopwatch.stop();

    return [value, stopwatch];
}

export async function fetchChannel() {
    const id = envParseString("CHANNEL_ID");
    const channel = await container.client.channels.fetch(id);

    if (!channel) throw new Error(`Channel "${id}" does not exist`);
    if (!channel.isTextBased()) throw new Error(`Channel #${channel.name} is not text based`);

    return channel;
}

export function safeParseJson(data: string) {
    try {
        const json = JSON.parse(data);
        return json;
    } catch {
        return data;
    }
}

export function isValidJson(data: string) {
    try {
        JSON.parse(data);
        return true;
    } catch {
        return false;
    }
}
