package net.deltapvp.chatplugin.listeners;

import net.deltapvp.chatplugin.ChatPlugin;
import net.deltapvp.chatplugin.utils.PlaceholderUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.event.HoverEvent.showText;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        event.setCancelled(true);
        for (Player plr : event.getRecipients()) {
            ChatPlugin.getInstance().adventure().player(plr).sendMessage(getFormat(player, message));
        }

        ChatPlugin.getInstance().adventure().console().sendMessage(getFormat(player, message));
    }

    public Component getFormat(Player player, String message) {
        TextComponent.Builder builder = text();

        String that = PlaceholderUtil.replacePlaceholders(player, ChatPlugin.getInstance().getChatFormat());
        List<String> hoverMessage = PlaceholderUtil.replacePlaceholders(player, ChatPlugin.getInstance().getHoverMessage());
        String form = that;
        form = form.replace("%message%", message);
        if (player.hasPermission("chatplugin.color")) {
            builder.append(LegacyComponentSerializer.legacyAmpersand().deserialize(form));
        } else { builder.append(text(form)); }
        String hoverString = String.join("\n", hoverMessage);
      //  hoverMessage.forEach(hm -> builder.hoverEvent(showText(text(hm))));
        builder.hoverEvent(showText(text(hoverString)));
        //return LegacyComponentSerializer.legacyAmpersand().deserialize(LegacyComponentSerializer.legacyAmpersand().serialize(builder.build()));
        return builder.build();
    }
}
