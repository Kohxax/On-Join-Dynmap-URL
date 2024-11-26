package dev.bokukoha.onJoinDynmapURL;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class OnJoinDynmapURL extends JavaPlugin {

    @Override
    public void onEnable() {

        //load Default URL sending setting from config.yml
        boolean sendURLDefault = getConfig().getBoolean("Send-URL-Default");

        // Plugin startup logic
        getLogger().info("On-Join-Dynmap-URL is now enabled!");
        saveDefaultConfig();
        String mapURL = getConfig().getString("URL");

        if (mapURL == null) {
            getLogger().warning("§4URL is empty in config.yml. Please set valid URL.");
            getConfig().set("URL", "");
            saveConfig();
        }

        if (sendURLDefault) {
            JoinListener joinListener = new JoinListener(mapURL);
            //registering event listener
            getServer().getPluginManager().registerEvents(joinListener, this);
        }
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

        //length of args
        if (args.length == 0) {
            unknownCommand(sender);
            return true;
        }

        //command list
        switch (args[0].toLowerCase()) {

            //Enable personal setting - WIP
            case "on":
                sender.sendMessage("This function is WIP.");
                break;

            //Disable personal setting - WIP
            case "off":
                sender.sendMessage("This function is WIP.");
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

            //other
            default:
                unknownCommand(sender);
                break;
        }
        return true;
    }

    //Help message
    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage("§l§a--DynmapTogglePlugin Commands--");
        sender.sendMessage("/dynurl on - Enable Dynmap URL sending.");
        sender.sendMessage("/dynurl off - Disable Dynmap URL sending.");
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
}
