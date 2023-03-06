import type { Message } from "discord.js";

export function formatMessage(message: Message) {
    message.content = formatCustomEmojis(message);
    message.content = formatMentions(message);
    message.content = formatAttachments(message);
    message.content = formatStickers(message);
    return message.content;
}

export function formatAuthor({ member, author }: Message) {
    return member?.displayName ?? author.username;
}

export function formatAttachments({ attachments, content }: Message) {
    if (!attachments.size) return content;

    const isSpace = !content.length || content.endsWith(" ");
    const urls = highlight(attachments
        .map((attachment) => `attachment://${attachment.name}`)
        .join(" "));

    return content + (isSpace ? urls : ` ${urls}`);
}

export function formatStickers({ stickers, content }: Message) {
    if (!stickers.size) return content;

    const isSpace = !content.length || content.endsWith(" ");
    const urls = highlight(stickers
        .map((sticker) => `sticker://${sticker.name}`)
        .join(" "));

    return content + (isSpace ? urls : ` ${urls}`);
}

export function formatCustomEmojis({ content }: Message) {
    return content.replace(
        /<a?:([^:]+):(\d{17,20})>/g,
        //":$1:"
        (_, name) => highlight(`:${name}:`)
    );
}

export function formatMentions({ content, mentions }: Message) {
    const userIds = mentions.users.map(user => user.id);
    const roleIds = mentions.roles.map(role => role.id);
    const channelIds = mentions.channels.map(channel => channel.id);

    if (userIds.length) {
        const userRegex = new RegExp(`<@!?(${userIds.join("|")})>`, "g");
        content = content.replace(userRegex, (raw, id) => {
            const name = mentions.members?.get(id)?.displayName ?? mentions.users.get(id)?.username;
            return name ? highlight(`@${name}`) : raw;
        });
    }

    if (roleIds.length) {
        const roleRegex = new RegExp(`<@&(${roleIds.join("|")})>`, "g");
        content = content.replace(roleRegex, (raw, id) => {
            const name = mentions.roles.get(id)?.name;
            return name ? highlight(`@${name}`) : raw;
        });
    }

    if (channelIds.length) {
        const channelRegex = new RegExp(`<#(${channelIds.join("|")})>`, "g");
        content = content.replace(channelRegex, (raw, id) => {
            const channel = mentions.channels.get(id);
            const name = channel && "name" in channel ? channel.name : null;
            return name ? highlight(`#${name}`) : raw;
        });
    }

    return content;
}

export function highlight(string: string) {
    return `§7${string}§r`;
}
