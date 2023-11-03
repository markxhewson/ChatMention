package tech.markxhewson.chatmention.util;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CC {

    /*
    Handle hex color codes and normal color codes.
     */
    public static String translate(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

        for (Matcher matcher = pattern.matcher(message); matcher.find(); matcher = pattern.matcher(message)) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, String.valueOf(net.md_5.bungee.api.ChatColor.of(color)));
        }

        message = ChatColor.translateAlternateColorCodes('&', message); // Translates any & codes too
        return message;
    }

}
