package onJoinDynmapURL;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class OnJoinDynmapURL extends JavaPlugin {

    boolean sendURL = getConfig().getBoolean("Send-URL-Global");

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("OnJoinDynmapURL is now enabled!");
        saveDefaultConfig();
        String mapURL = getConfig().getString("URL");

        if (sendURL) {
            JoinListener joinListener = new JoinListener(mapURL);
            //イベントリスナー登録
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
        if (sender.hasPermission("OnJoinDynmapURL.commands.dynurl")) {

            //command name
            if (command.getName().equalsIgnoreCase("dynurl")) {

                //sender judge
                if (sender instanceof Player) {

                    //length of args
                    if (args.length == 1) {

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

                    } else {
                        sender.sendMessage("§a/dynurl help for command list.");
                    }

                } else {
                    sender.sendMessage("This command can only be executed by a player in-game.");
                }
                return true;

            } else {
                return false;
            }

        } else {
            sender.sendMessage("§4You do not have permission!");
            return false;
        }
    }
}
