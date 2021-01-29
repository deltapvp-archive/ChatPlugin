package net.deltapvp.chatplugin;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.expansion.cloud.CloudExpansion;
import net.deltapvp.chatplugin.command.ChatPluginCommand;
import net.deltapvp.chatplugin.listeners.ChatListener;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ChatPlugin extends JavaPlugin {
    private static ChatPlugin instance;
    private BukkitAudiences adventure;
    private String chatFormat;
    private List<String> hoverMessage;
    private String clickCommand;
    private boolean clickEnabled;

    @Override
    public void onEnable() {
        instance = this;
        // check if the PAPI Player expansion is installed
      //  if (!PlaceholderAPIPlugin.getInstance().getCloudExpansionManager().findCloudExpansionByName("Player").isPresent()) {
           // getLogger().warning("You need to install the 'Player' PlaceholderAPI Expansion. Trying to install it now.");
            // This download actually work yet, probably people PAPI hasn't done their stuff yet.
           // Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "papi ecloud download Player");
           // Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "papi reload");
      //  }

        this.adventure = BukkitAudiences.create(this);
        setupMetrics();
        saveDefaultConfig();
        chatFormat = getConfig().getString("format");
        hoverMessage = getConfig().getStringList("hover");
        clickCommand = getConfig().getString("clickCommand");
        clickEnabled = getConfig().getBoolean("clickEnabled");
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        ChatPluginCommand cpc = new ChatPluginCommand();
        getCommand("chatplugin").setExecutor(cpc);
        getCommand("chatplugin").setTabCompleter(cpc);
        doweirdshitoik();
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
        clickCommand = getConfig().getString("clickCommand");
        clickEnabled = getConfig().getBoolean("clickEnabled");
    }

    public void doweirdshitoik() {
        Entity ent = Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), 0, 80, 0), EntityType.DONKEY);
        getLogger().info(ent.getUniqueId() + "");
    }

    private void setupMetrics() {
        Metrics metrics = new Metrics(this, 9977);
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

    public String getClickCommand() {
        return clickCommand;
    }

    public boolean isClickEnabled() {
        return clickEnabled;
    }
}
