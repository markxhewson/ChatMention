package tech.markxhewson.chatmention.listener;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tech.markxhewson.chatmention.ChatMention;
import tech.markxhewson.chatmention.util.CC;

public class AsyncChatListener implements Listener {

    private final ChatMention plugin;
    private final Sound MENTION_SOUND;
    private final String MENTION_ALERT;

    public AsyncChatListener(ChatMention plugin) {
        this.plugin = plugin;
        this.MENTION_SOUND = Sound.valueOf(plugin.getConfig().getString("mention.sound"));
        this.MENTION_ALERT = CC.translate(plugin.getConfig().getString("mention.alert"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChatReceived(AsyncPlayerChatEvent event) {
        String[] messageParts = event.getMessage().split(" ");
        String playerMentioned = findMentionedPlayer(messageParts);

        if (playerMentioned == null || playerMentioned.equals(event.getPlayer().getName())) {
            return;
        }

        event.setCancelled(true);
        Player mentionedPlayer = plugin.getServer().getPlayer(playerMentioned);

        if (mentionedPlayer != null) {
            processMention(event, mentionedPlayer, playerMentioned);
        }

    }

    private void processMention(AsyncPlayerChatEvent event, Player mentionedPlayer, String originalName) {
        for (Player recipient : event.getRecipients()) {
            String formattedMessage = formatMessage(event, recipient, mentionedPlayer, originalName);
            recipient.sendMessage(formattedMessage);

            if (recipient == mentionedPlayer) {
                mentionedPlayer.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(CC.translate(MENTION_ALERT.replace("%player%", event.getPlayer().getName()))));
                playMentionSound(mentionedPlayer);
            }
        }
    }

    private String findMentionedPlayer(String[] message) {
        for (String playerName : message) {
            if (plugin.getPlayerCache().hasPlayer(playerName)) {
                return playerName;
            }
        }
        return null;
    }

    private String formatMessage(AsyncPlayerChatEvent event, Player recipient, Player mentionedPlayer, String originalName) {
        String lastColor = ChatColor.getLastColors(event.getMessage()); // to get the original color of the mention so the mention color doesn't bleed into the rest of the message
        String mentionColor = mentionedPlayer != null ? plugin.getConfig().getString("mention.color") : ChatColor.RESET.toString();

        return event.getFormat()
                .replace("%1$s", event.getPlayer().getName())
                .replace("%2$s", recipient.getName().equals(mentionedPlayer != null ? mentionedPlayer.getName() : "")
                        ? event.getMessage().replace(originalName, CC.translate(mentionColor + mentionedPlayer.getName() + (lastColor.isEmpty() ? ChatColor.RESET : lastColor)))
                        : event.getMessage());
    }

    private void playMentionSound(Player player) {
        player.playSound(player.getLocation(), MENTION_SOUND, 1, 1);
    }
}
