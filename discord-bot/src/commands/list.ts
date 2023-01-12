import { ApplyOptions } from "@sapphire/decorators";
import { Command } from "@sapphire/framework";

@ApplyOptions<Command.Options>({
    description: "View a list of online players",
})
export class List extends Command {
    override chatInputRun(interaction: Command.ChatInputCommandInteraction) {
        return interaction.reply({
            content: "Slash commands will return in the future!",
            ephemeral: true,
        });
    }

    override registerApplicationCommands(registry: Command.Registry) {
        registry.registerChatInputCommand(
            { name: this.name, description: this.description },
            { idHints: ["994289592519893073"] }
        );
    }
}
