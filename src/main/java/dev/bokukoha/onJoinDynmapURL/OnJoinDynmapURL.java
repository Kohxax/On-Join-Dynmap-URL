package dev.bokukoha.onJoinDynmapURL;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.UUID;

public class OnJoinDynmapURL extends JavaPlugin {

    private DatabaseManager databaseManager;
    private String mapURL;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        //import URL from config.yml
        mapURL = getConfig().getString("URL");

        //load default URL sending setting from config.yml
        boolean sendURLDefault = getConfig().getBoolean("Send-URL-Default");

        //initialize database manager
        databaseManager = new DatabaseManager();

        // Plugin startup logic
        getLogger().info("On-Join-Dynmap-URL is now enabled!");

        //send message if URL section in config.yml is empty
        if (mapURL == null) {
            getLogger().warning("§4URL is empty in config.yml. Please set valid URL.");
            getConfig().set("URL", "");
            saveConfig();
        }

        //register player join listener
        if (sendURLDefault) {
            JoinListener joinListener = new JoinListener(mapURL, this);
            getServer().getPluginManager().registerEvents(joinListener, this);
        }

        //register tab completer
        getCommand("dynurl").setTabCompleter(new DynURLTabCompleter());
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("On-Join-Dynmap-URL is now disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //command name
        if (!command.getName().equalsIgnoreCase("dynurl")) {
            return false;
        }

        //dyn url permission
        if (!sender.hasPermission("OnJoinDynmapURL.commands.dynurl")) {
            havePermission(sender);
            return true;
        }

        //sender
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player in-game.");
            return true;
        }

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();
        String name = player.getName();

        //length of args
        if (args.length == 0) {
            sendMapURL(player);
            return true;
        }

        //command list
        switch (args[0].toLowerCase()) {

            //set personal URL sending on
            case "on":
                databaseManager.setSendURLSetting(uuid, name, true);
                sender.sendMessage("§aDynmap URL sending enabled.");
                break;

            //set personal URL sending off
            case "off":
                databaseManager.setSendURLSetting(uuid, name, false);
                sender.sendMessage("§4Dynmap URL sending disabled.");
                break;

            //Set Default sending URL
            case "default":

                //default.* permission check
                if (!sender.hasPermission("OnJoinDynmapURL.commands.default")) {
                    havePermission(sender);
                    break;
                }

                //length of args after default
                if (args.length < 2) {
                    unknownCommand(sender);
                    break;
                }

                switch (args[1].toLowerCase()) {
                    case "on":
                        getConfig().set("Send-URL-Default", true);
                        saveConfig();
                        sender.sendMessage("Default setting for Dynmap URL sending is now " + "§aEnabled");
                        break;

                    case "off":
                        getConfig().set("Send-URL-Default", false);
                        saveConfig();
                        sender.sendMessage("Default setting for Dynmap URL sending is now " + "§4Disabled");
                        break;

                    default:
                        unknownCommand(sender);
                        break;
                }
                break;

            //help message
            case "help":
                sendHelpMessage(sender);
                break;

            default:
                unknownCommand(sender);
                break;
        }
        return true;
    }

    //Help message
    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage("§l§a---------DynmapURL Toggle Commands---------");
        sender.sendMessage("/dynurl - Send Dynmap URL to you.");
        sender.sendMessage("/dynurl on - Enable Dynmap URL sending to you.");
        sender.sendMessage("/dynurl off - Disable Dynmap URL sending to you.");
        sender.sendMessage("/dynurl default on - Set global URL sending to Enable.");
        sender.sendMessage("/dynurl default off - Set global URL sending to Disable.");
        sender.sendMessage("/dynurl help - Show this help message.");
    }

    //Permission message
    private void havePermission(CommandSender sender) {
        sender.sendMessage("§4You do not have permission!");
    }

    //Unknown command message
    private void unknownCommand(CommandSender sender) {
        sender.sendMessage("Unknown Command! /dynurl help for command list");
    }

    private void sendMapURL(Player player) {
        player.sendMessage("§aDynmapURL" + "§f: " + mapURL);
    }
}
