import { ApplyOptions } from "@sapphire/decorators";
import { Command } from "@sapphire/framework";
import { fetchStatus } from "#lib/functions";

@ApplyOptions<Command.Options>({
    description: "View a list of online players",
})
export class List extends Command {
    override async chatInputRun(interaction: Command.ChatInputCommandInteraction) {
        await interaction.deferReply({ ephemeral: true });

        const status = await fetchStatus();

        if (!status.online) {
            return interaction.editReply("The server is currently offline!");
        }

        const { online, max, list } = status.players;
        let message = `There are ${online} of ${max} players online\n\n`;

        for (const player of list) {
            message += `• ${player.name_clean}\n`;
        }

        return interaction.editReply(message);
    }

    override registerApplicationCommands(registry: Command.Registry) {
        registry.registerChatInputCommand(
            { name: this.name, description: this.description },
            { idHints: ["994289592519893073"] }
        );
    }
}
