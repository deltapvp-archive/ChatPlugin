package net.deltapvp.chatplugin.command;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import net.deltapvp.chatplugin.ChatPlugin;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.event.ClickEvent.openUrl;
import static net.kyori.adventure.text.event.HoverEvent.showText;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

public class ChatPluginCommand implements TabExecutor {
    private final BukkitAudiences audience = ChatPlugin.getInstance().adventure();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 0 || !sender.hasPermission("chatplugin.use")) {
            Component comp = text()
                    .append(text("Running ", GOLD))
                    .append(text("ChatPlugin ", YELLOW, BOLD))
                    .append(text("version ", GOLD))
                    .append(text(ChatPlugin.getInstance().getDescription().getVersion(), YELLOW))
                    .hoverEvent(showText(text("Click here to go to the github page", GOLD)))
                    .clickEvent(openUrl("https://github.com/powercasgamer/chatplugin"))
                    .build();
            audience.sender(sender).sendMessage(comp);
            return true;
        }

        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "version": {
                Component comp = text()
                        .append(text("--------", GRAY, STRIKETHROUGH))
                        .append(newline())
                        .append(text("ChatPlugin Version", GOLD))
                        .append(text(": ", GRAY))
                        .append(text(ChatPlugin.getInstance().getDescription().getVersion(), YELLOW))
                        .append(newline())
                        .append(text("PlaceholderAPI Version", GOLD))
                        .append(text(": ", GRAY))
                        .append(text(PlaceholderAPIPlugin.getInstance().getDescription().getVersion(), YELLOW))
                        .append(newline())
                        .append(text("--------", GRAY, STRIKETHROUGH))
                        .build();
                audience.sender(sender).sendMessage(comp);
                break;
            }

            case "reload": {
                Component comp = text()
                        .append(text("You have reloaded the configuration.", GOLD))
                        .build();
                ChatPlugin.getInstance().reload();
                audience.sender(sender).sendMessage(comp);
                break;
            }
        }

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (!sender.hasPermission("chatplugin.use")) return Collections.emptyList();
        if (args.length == 0) return Arrays.asList("reload", "version");
        return Collections.emptyList();
    }
}
