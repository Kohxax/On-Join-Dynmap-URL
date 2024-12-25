A simple Spigot plugin that allows you to create a custom join message for your server.
Works with spigot 1.8.8 and above.

## Installation
1. Download the latest release from the [releases page](https://github.com/Kohxax/On-Join-Dynmap-URL/releases/)
2. Place the downloaded jar file in your server's `plugins` folder
3. Restart your server
4. Edit the `config.yml` file in the `On-Join-Dynmap-Url` folder in your `plugins` folder
5. Reload the plugin using `/reload` or restart your server

## Configuration
```yaml
URL: "you can set the URL here"
Send-URL-Default: you can set default setting for send URL(true/false)
```

## Commands
- `/dynurl` - Send Dynmap URL to player
- `/dynurl on` - Enable send URL to player
- `/dynurl off` - Disable send URL to player
- `/dynurl default on` - Enable default setting for send URL
- `/dynurl default off` - Disable default setting for send URL
- `/dynurl help` - Show help message

## Permissions
- `OnJoinDynmapURL.*` - Allows access to all `/dynurl` commands
- `OnJoinDynmapURL.commands.dynurl` - Allows access to `/dynurl <on/off/help>`
- `OnJoinDynmapURL.commands.default` - Allows access to `/dynurl default <on/off>`

## Contact
- Discord: Bokukoha
- X (Twitter): @Kohxax
