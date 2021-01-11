package net.deltapvp.chatplugin.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.List;

public class PlaceholderUtil {

    public static String replacePlaceholders(Player player, String input) {
        return PlaceholderAPI.setPlaceholders(player, input);
    }

    public static List<String> replacePlaceholders(Player player, List<String> input) {
        return PlaceholderAPI.setPlaceholders(player, input);
    }

    
}
