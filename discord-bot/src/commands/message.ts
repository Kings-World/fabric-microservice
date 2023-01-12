import { ApplyOptions } from "@sapphire/decorators";
import { Command } from "@sapphire/framework";
import { ApplicationCommandOptionType } from "discord.js";

@ApplyOptions<Command.Options>({
    description: "Message a player that is online",
})
export class Message extends Command {
    override chatInputRun(interaction: Command.ChatInputCommandInteraction) {
        return interaction.reply({
            content: "Slash commands will return in the future!",
            ephemeral: true,
        });
    }

    override autocompleteRun(interaction: Command.AutocompleteInteraction) {
        return interaction.respond([
            {
                name: "Slash commands will return in the future!",
                value: "soon"
            }
        ])
    }

    override registerApplicationCommands(registry: Command.Registry) {
        registry.registerChatInputCommand(
            {
                name: this.name,
                description: this.description,
                options: [
                    {
                        name: "player",
                        description: "The player to message",
                        type: ApplicationCommandOptionType.String,
                        required: true,
                        autocomplete: true,
                    },
                    {
                        name: "content",
                        description: "The message you want to send",
                        type: ApplicationCommandOptionType.String,
                        required: true
                    }
                ]
            },
            { idHints: ["994403276697108613"] }
        );
    }
}
