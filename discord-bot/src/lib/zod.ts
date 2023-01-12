import { z } from "zod";
import { safeParseJson } from "./functions.js";

const schema = z.object({
    avatar: z.string().url(),
    name: z.string().min(1),
    content: z.string()
});

export function parseSchema(data: unknown, params?: Partial<z.ParseParams>) {
    if (typeof data === "string") data = safeParseJson(data);
    return schema.parse(data, params);
}
