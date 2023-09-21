package tech.markxhewson.chatmention.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tech.markxhewson.chatmention.ChatMention;

public class ConnectionListener implements Listener {

    private final ChatMention plugin;

    public ConnectionListener(ChatMention plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerConnect(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getPlayerCache().addPlayer(player.getName());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getPlayerCache().removePlayer(player.getName());
    }

}
