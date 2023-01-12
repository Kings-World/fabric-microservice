// unless explicitly defined, set NODE_ENV as development:
process.env.NODE_ENV = "development";

// default imports
import "dotenv/config";
import "reflect-metadata";

// sapphire imports
import "@skyra/env-utilities";
import "@sapphire/plugin-logger/register";
import "@sapphire/plugin-hmr/register";

import * as colorette from "colorette";
import {
    ApplicationCommandRegistries,
    RegisterBehavior,
} from "@sapphire/framework";
import { startClient } from "#lib/client";
import { connectToRedis, subscribeToMinecraft } from "#lib/redis";

// enable colorette
colorette.createColors({ useColor: true });

// set default command behaviour
ApplicationCommandRegistries.setDefaultBehaviorWhenNotIdentical(
    RegisterBehavior.Overwrite
);

// start the bot
await startClient();

// connect to redis
await connectToRedis();

// subscribe to redis channels
subscribeToMinecraft();
