package me.twostinkysocks.discordchatlink;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public class MinecraftManager implements Listener {

    private JavaPlugin plugin;
    private JDA client;

    public MinecraftManager(JavaPlugin plugin, JDA client) {
        this.plugin = plugin;
        this.client = client;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent e) {
        client.getTextChannelById(plugin.getConfig().getString("discord.channel-id"))
                .sendMessageEmbeds(
                        new EmbedBuilder()
                                .setAuthor(e.getPlayer().getName(), null, "https://crafatar.com/avatars/" + e.getPlayer().getUniqueId())
                                .appendDescription(ChatColor.stripColor(e.getMessage()))
                                .setColor(Color.decode(plugin.getConfig().getString("discord.embed.color")))
                                .build()
                ).complete();
    }

}
