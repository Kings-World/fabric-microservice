import { RedisChannel } from "#lib/constants";
import { formatAuthor, formatMessage } from "#lib/format";
import { ApplyOptions } from "@sapphire/decorators";
import { Listener } from "@sapphire/framework";
import type { Message } from "discord.js";

@ApplyOptions<Listener.Options>({})
export class MessageCreate extends Listener {
    override async run(message: Message) {
        const { channel, publisher } = this.container;
        if (
            message.channel.id !== channel.id ||
            (!message.content.length && !message.attachments.size) ||
            message.author.bot ||
            message.system ||
            message.webhookId
        ) return;

        let author = formatAuthor(message);

        if (message.reference) {
            const reply = await message.fetchReference();
            author = `${author} -> ${formatAuthor(reply)}`;
        }

        await publisher.publish(
            RedisChannel.DiscordToMinecraft,
            `${author} : ${formatMessage(message)}`
        );
    }
}
