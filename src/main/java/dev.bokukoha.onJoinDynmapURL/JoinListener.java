package dev.bokukoha.onJoinDynmapURL;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener{

    private final String mapURL;

    //constructor of mapURL
    public JoinListener(String mapURL) {
        this.mapURL = mapURL;
    }

    //sending URL
    public void sendMapURL(Player player) {
        player.sendMessage("§aDynmapURL" + "§f: " + mapURL);
    }

    //detect player connection
    @EventHandler
    public void onPlayerJoinEvents(PlayerJoinEvent event) {
        sendMapURL(event.getPlayer());
    }
}
