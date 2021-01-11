package net.deltapvp.chatplugin;

import net.deltapvp.chatplugin.listeners.ChatListener;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ChatPlugin extends JavaPlugin {
    private static ChatPlugin instance;
    private BukkitAudiences adventure;
    private String chatFormat;
    private List<String> hoverMessage;

    @Override
    public void onEnable() {
        instance = this;
        this.adventure = BukkitAudiences.create(this);
        saveDefaultConfig();
        chatFormat = getConfig().getString("format");
        hoverMessage = getConfig().getStringList("hover");
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
    }

    @Override
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
        instance = null;
    }

    public BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    public void reload() {
        reloadConfig();
        chatFormat = getConfig().getString("format");
        hoverMessage = getConfig().getStringList("hover");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("chatplugin.reload")) {
            reload();
            adventure.sender(sender).sendMessage(Component.text("You have reloaded the plugin!"));
        }
        return true;
    }

    public static ChatPlugin getInstance() {
        return instance;
    }

    public String getChatFormat() {
        return chatFormat;
    }

    public List<String> getHoverMessage() {
        return hoverMessage;
    }
}
