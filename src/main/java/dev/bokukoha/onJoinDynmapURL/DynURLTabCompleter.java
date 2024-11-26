package dev.bokukoha.onJoinDynmapURL;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynURLTabCompleter implements TabCompleter {

    //tab key auto complete method
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        //command name
        if (!command.getName().equalsIgnoreCase("dynurl")) {
            return null;
        }

        //first arg
        if (args.length == 1) {
            List<String> subCommands = Arrays.asList("on", "off", "default", "help");
            return filterByInput(subCommands, args[0]);
        }

        //second arg
        if (args.length == 2 && args[0].equalsIgnoreCase("default")) {
            List<String> subCommands = Arrays.asList("on", "off");
            return filterByInput(subCommands, args[1]);
        }
        return null;

    }

    private List<String> filterByInput(List<String> options, String input) {
        List<String> filtered = new ArrayList<>();

        for (String option : options) {
            if (option.toLowerCase().startsWith(input.toLowerCase())) {
                filtered.add(option);
            }
        }
        return filtered;
    }
}
