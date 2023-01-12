import {
    isGuildBasedChannel,
    canReadMessages,
} from "@sapphire/discord.js-utilities";
import { container } from "@sapphire/framework";
import { Result } from "@sapphire/result";
import { Stopwatch } from "@sapphire/stopwatch";
import { envParseString } from "@skyra/env-utilities";
import type { Awaitable, Message } from "discord.js";

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
    const channel = await container.client.channels.fetch(
        envParseString("CHANNEL_ID")
    );
    if (
        !isGuildBasedChannel(channel) ||
        !canReadMessages(channel) ||
        !channel.isTextBased()
    ) {
        throw new Error("Channel must be text based");
    }

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

export function contentWithAttachments({ attachments, content }: Message) {
    if (attachments.size === 0) return content;

    const isSpace = !content.length || content.endsWith(" ");
    const urls = attachments
        .map((attachment) => attachment.url)
        .join(" ");

    return content + (isSpace ? urls : ` ${urls}`);
}
