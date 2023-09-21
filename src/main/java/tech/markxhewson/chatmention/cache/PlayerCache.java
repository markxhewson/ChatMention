package tech.markxhewson.chatmention.cache;

import tech.markxhewson.chatmention.ChatMention;

import java.util.LinkedList;

public class PlayerCache {

    private final ChatMention plugin;
    private final LinkedList<String> playerCache = new LinkedList<>();

    public PlayerCache(ChatMention plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(String playerName) {
        playerCache.add(playerName);
    }

    public void removePlayer(String playerName) {
        playerCache.remove(playerName);
    }

    public boolean hasPlayer(String playerName) { // allows checking for players without worrying about case sensitivity
        playerName = playerName.toLowerCase();

        for (String cachedName : playerCache) {
            if (cachedName.toLowerCase().equals(playerName)) {
                return true;
            }
        }

        return false;
    }




}
