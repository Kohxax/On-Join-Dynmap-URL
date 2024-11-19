package dev.bokukoha.onJoinDynmapURL;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class OnJoinDynmapURL extends JavaPlugin {

    @Override
    public void onEnable() {

        //load global URL sending setting from config.yml
        boolean sendURL = getConfig().getBoolean("Send-URL-Global");

        // Plugin startup logic
        getLogger().info("OnJoinDynmapURL is now enabled!");
        saveDefaultConfig();
        String mapURL = getConfig().getString("URL");

        if (mapURL == null) {
            getLogger().warning("§4URL is empty in config.yml. Please set valid URL.");
            getConfig().set("URL", "");
            saveConfig();
        }

        if (sendURL) {
            JoinListener joinListener = new JoinListener(mapURL);
            //registering event listener
            getServer().getPluginManager().registerEvents(joinListener, this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("OnJoinDynmapURL is now disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //permission
        if (!sender.hasPermission("OnJoinDynmapURL.commands.dynurl")) {
            sender.sendMessage("§4You do not have permission!");
            return false;
        }

        //command name
        if (!command.getName().equalsIgnoreCase("dynurl")) {
            return false;
        }

        //sender
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player in-game.");
            return false;
        }

        //length of args
        if (args.length == 1) {
            sender.sendMessage("§a/dynurl help for command list.");
            return false;
        }

        //on
        if (args[0].equalsIgnoreCase("on")) {
            getConfig().set("Send-URL-Global", true);
            saveConfig();
            sender.sendMessage("Dynmap URL sending is now "  + "§aEnabled");
        }

        //off
        if (args[0].equalsIgnoreCase("off")) {
            getConfig().set("Send-URL-Global", false);
            saveConfig();
            sender.sendMessage("Dynmap URL sending is now " +  "§4Disabled");
        }

        //help
        if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("§l§aDynmapTogglePlugin Commands");
            sender.sendMessage("/dynurl on - Enable Dynmap URL sending.");
            sender.sendMessage("/dynurl off - Disable Dynmap URL sending.");
            sender.sendMessage("/dynurl help - Show this help message.");
        }
        return true;

    }
}
