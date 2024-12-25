package dev.bokukoha.onJoinDynmapURL;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final String mapURL;
    private final OnJoinDynmapURL plugin;

    public JoinListener(String mapURL, OnJoinDynmapURL plugin) {
        this.mapURL = mapURL;
        this.plugin = plugin;
    }

    public void sendMapURL(Player player) {
        player.sendMessage("§aDynmapURL" + "§f: " + mapURL);
    }

    @EventHandler
    public void onPlayerJoinEvents(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //get personal setting from database
        boolean sendURLPersonal = plugin.getDatabaseManager().getSendURLSetting(player.getUniqueId());

        if (!sendURLPersonal) {
            return;
        }

        //send URL if personal setting is true
        sendMapURL(player);
    }
}

