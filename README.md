# fabric-microservice

A version of the [fabric-mod][mod] that splits the Discord bot and the Minecraft mod into two microservices. They both communicate with each other via [Redis Pub/Sub][pub-sub].

### Prerequisites

- Docker ([docker.com][docker])
- Redis Server ([redis.io][redis])
- Fabric Server ([fabricmc.net][fabric])

## Installation

### Discord Bot

#### Docker

```
docker run --name discord-bot --env-file .env -tid ghcr.io/kings-world/fabric-microservice:main
```

#### Docker Compose

##### Using a `.env` file (recommended)

```yml
version: "3"

services:
  discord-bot:
    image: ghcr.io/kings-world/fabric-microservice:main
    container_name: discord-bot
    env_file: .env
```

Copy the environment variables from [.env.example](discord-bot/.env.example) and place them in a `.env` file.

**Note:** Both `docker-compose.yml` and `.env` must be in the same directory.

##### Using embedded variables

```yml
version: "3"

services:
  discord-bot:
    image: ghcr.io/kings-world/fabric-microservice:main
    container_name: discord-bot
    environment:
      # discord
      DISCORD_TOKEN: # bot token (required)
      WEBHOOK_URL: # webhook url (required)
      CHANNEL_ID: # channel id (required)
      # minecraft
      SERVER_ADDRESS: # server address (required)
      # redis
      REDIS_HOST: 127.0.0.1 # redis host (optional)
      REDIS_PORT: 6379 # redis port (optional)
      REDIS_PASSWORD: "" # redis password (optional)
      REDIS_TLS: false # redis tls (optional)
      REDIS_DB: 0 # redis db (optional)
```

### Minecraft

Download the JAR and place it into the servers `mods/` folder. Upon startup, a config will be made in `config/kings-world/config.yml` where you can provide your redis servers connection details. After modifying the config, the server must be restarted to reflect the changes. However, the `/kingsworld reload` command will be re-added in the future.

[mod]: https://github.com/Kings-World/fabric-mod
[pub-sub]: https://redis.io/docs/manual/pubsub/
[docker]: https://www.docker.com/
[redis]: https://redis.io/
[fabric]: https://fabricmc.net/
