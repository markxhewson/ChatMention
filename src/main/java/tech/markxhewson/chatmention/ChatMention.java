package tech.markxhewson.chatmention;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import tech.markxhewson.chatmention.cache.PlayerCache;
import tech.markxhewson.chatmention.listener.AsyncChatListener;
import tech.markxhewson.chatmention.listener.ConnectionListener;

import java.util.Arrays;

public final class ChatMention extends JavaPlugin {

    @Getter
    private PlayerCache playerCache;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        initListeners();

        playerCache = new PlayerCache(this);

        getServer().getOnlinePlayers().forEach(player -> playerCache.addPlayer(player.getName()));
    }

    @Override
    public void onDisable() {}

    public void initListeners() {
        Arrays.asList(
                new AsyncChatListener(this),
                new ConnectionListener(this)
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }
}
