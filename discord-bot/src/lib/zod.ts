import { z } from "zod";

export const messageSchema = z.object({
    avatar: z.string().url(),
    name: z.string().min(1),
    content: z.string()
});

export const statusSchema = z.object({
    online: z.boolean(),
    host: z.string(),
    port: z.number(),
    eula_blocked: z.boolean(),
    retrieved_at: z.number(),
    expires_at: z.number(),
    version: z.object({
        name_raw: z.string(),
        name_clean: z.string(),
        name_html: z.string(),
        protocol: z.number(),
    }),
    players: z.object({
        online: z.number(),
        max: z.number(),
        list: z.array(
            z.object({
                uuid: z.string(),
                name_raw: z.string(),
                name_clean: z.string(),
                name_html: z.string(),
            })
        )
    }),
    motd: z.object({
        raw: z.string(),
        clean: z.string(),
        html: z.string(),
    }),
    icon: z.string()
})
