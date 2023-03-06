import { inProduction } from "#lib/constants";
import { ApplyOptions } from "@sapphire/decorators";
import { Listener, Store } from "@sapphire/framework";
import { blue, gray, yellow } from "colorette";
import type { Client } from "discord.js";

@ApplyOptions<Listener.Options>({ once: true })
export class Ready extends Listener {
    private readonly style = inProduction ? blue : yellow;

    override async run(client: Client<true>) {
        this.printStoreDebugInformation();
        this.container.logger.info(`${client.user.tag} is now ready to go!`);
    }

    private printStoreDebugInformation() {
        const { client, logger } = this.container;

        const stores = [...client.stores.values()];
        const last = stores.pop()!;

        for (const store of stores) {
            logger.info(this.styleStore(store, false));
        }

        logger.info(this.styleStore(last, true));
    }

    private styleStore(store: Store<any>, last: boolean) {
        return gray(
            `${last ? "└─" : "├─"} Loaded ${this.style(
                store.size.toString().padEnd(3, " ")
            )} ${store.name}.`
        );
    }
}
