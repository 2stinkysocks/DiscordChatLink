# Discord Chat Link

Discord Chat Link is a simple spigot plugin that facilitates linking a Discord channel to a Minecraft server's chat.

## Setup

1. Install the plugin by placing it in your server's `plugins` folder, and run the server to generate a config file
2. Open the config file located at `plugins/DiscordChatLink/config.yml`
3. Replace the placeholder `channel-id` in the config with the channel ID of the Discord channel you want to use ([tutorial](https://support.discord.com/hc/en-us/articles/206346498-Where-can-I-find-my-User-Server-Message-ID))
4. Create a discord bot at [Discord's developer portal](https://discord.com/developers/applications), [obtain the bot's token, and invite it to your server](https://github.com/reactiflux/discord-irc/wiki/Creating-a-discord-bot-&-getting-a-token)
5. **Enable the Message Content intent under the bot section of the developer portal!**
6. Replace the placeholder `bot-token` in the config file with your bot's token
7. Make sure your bot has permission to speak in the channel you chose, and then start the Minecraft server
8. Try typing a message ingame and in the Discord channel to test it out!

### Commands

Discord Chat Link comes with functionality to allow Discord users to execute commands ingame.

### Be warned that all commands will be executed with maximum privileges!

Three example commands are included within the default config file.

To test them out, type `-say Hello World` in the discord channel!

The `/say` command ingame should execute correctly, however will print out `[Server] Hello`

This is due to how arguments work in the configuration.

Let's create a `/ban` command for Discord moderators as an example

The `/ban` command in most plugins uses this format: `/ban <player> <time> <reason>`

To create the command, we first need to create a new entry under `commands` in the config file

The file should look something like this

```yaml
commands:
    say:
      execute: "say %0"
    ban:
      execute: "ban %0 %1 %n"
      requiredrole: "your-role-id"
```

`ban:` determines what the command name will be on the discord side, and `execute:` determines what command will be executed on the minecraft side

`%0`, `%1`, and `%n` all represent arguments that will be passed from discord to minecraft

Here's an example:

If the discord moderator types `-ban 2stinkysocks 7d causing a disturbance`, `2stinkysocks` would be converted to `%0`, `7d` would be converted to `%1`, and all remaining args would be converted to `%n`

The ingame command would become `/ban 2stinkysocks 7d causing a disturbance`

The last thing is the `requiredrole` attribute, which makes the command only executable by those with the role ID set in the config. Commands without this attribute can be executed by anyone.

Getting a role's ID is very similar to getting a channel's ID
