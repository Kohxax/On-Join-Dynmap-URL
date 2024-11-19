package onJoinDynmapURL;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener{

    private final String mapURL;

    //mapURLのコンストラクタ
    public JoinListener(String mapURL) {
        this.mapURL = mapURL;
    }

    //URL送信メソッド
    public void sendMapURL(Player player) {
        player.sendMessage("§aDynmapURL" + "§f: " + mapURL);
    }

    //プレイヤーの接続を検知
    @EventHandler
    public void onPlayerJoinEvents(PlayerJoinEvent event) {
        sendMapURL(event.getPlayer());
    }
}
