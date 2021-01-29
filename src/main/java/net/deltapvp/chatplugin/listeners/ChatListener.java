package net.deltapvp.chatplugin.listeners;

import net.deltapvp.chatplugin.ChatPlugin;
import net.deltapvp.chatplugin.utils.PlaceholderUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
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
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        String message = event.getMessage();
        Component formattedMessage = getFormat(player, message);

        for (Player plr : event.getRecipients()) {
            ChatPlugin.getInstance().adventure().player(plr).sendMessage(formattedMessage);
        }
        event.getRecipients().clear();

        // This is done so the console doesn't look ugly
        event.setFormat(LegacyComponentSerializer.legacyAmpersand().serialize(formattedMessage)
                .replaceAll("(?:[^%]|\\A)%(?:[^%]|\\z)", "%%"));
    }

    public Component getFormat(Player player, String message) {
        TextComponent.Builder builder = text();
        String that = PlaceholderUtil.replacePlaceholders(player, ChatPlugin.getInstance().getChatFormat());
        String clickCommand = PlaceholderUtil.replacePlaceholders(player, ChatPlugin.getInstance().getClickCommand());
        List<String> hoverMessage = PlaceholderUtil.replacePlaceholders(player, ChatPlugin.getInstance().getHoverMessage());
        String form = that;
        form = form.replace("%message%", message);
        if (player.hasPermission("chatplugin.color")) {
            builder.append(LegacyComponentSerializer.legacyAmpersand().deserialize(form));
        } else if (player.hasPermission("chatplugin.minimessage")) {
            builder.append(MiniMessage.get().deserialize(MiniMessage.get().serialize(LegacyComponentSerializer.legacyAmpersand().deserialize(form)))); // this seems kinda messy
        } else { builder.append(text(form)); }
        String hoverString = String.join("\n", hoverMessage);
        builder.hoverEvent(showText(text(hoverString)));
        if (ChatPlugin.getInstance().isClickEnabled()) builder.clickEvent(ClickEvent.runCommand(clickCommand));
        // TODO: click event
        return builder.build();
    }
}
