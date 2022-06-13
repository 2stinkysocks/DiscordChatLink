package me.twostinkysocks.discordchatlink;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class DiscordManager extends ListenerAdapter {

    private DiscordChatLink plugin;

    public DiscordManager(DiscordChatLink plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(!e.getAuthor().isBot() && e.getChannel().getId().equals(plugin.getConfig().getString("discord.channel-id"))) {
            Set<String> keys = plugin.getConfig().getConfigurationSection("discord.commands").getKeys(false);
            boolean isCommand = false;
            for(String key : keys) {
                if(e.getMessage().getContentRaw().startsWith(plugin.getConfig().getString("discord.command-prefix") + key)) {
                    if(plugin.getConfig().contains("discord.commands." + key + ".requiredrole")) {
                        if(!e.getMember().getRoles().contains(e.getGuild().getRoleById(plugin.getConfig().getString("discord.commands." + key + ".requiredrole")))) return;
                    }
                    String value = plugin.getConfig().getString("discord.commands." + key + ".execute");
                    String[] split = e.getMessage().getContentRaw().split(" ");
                    String[] args = Arrays.copyOfRange(split, 1, split.length);
                    int numArgs = StringUtils.countMatches(value, "%");
                    if(numArgs > args.length) {
                        e.getChannel().sendMessage(plugin.getConfig().getString("discord.invalid-command-error")).complete();
                        return;
                    }
                    if(value.contains("%n")) {
                        numArgs -= 1;
                    }
                    for(int i = 0; i < numArgs; i++) {
                        value = value.replaceAll("%"+i, args[i]);
                        args[i] = "\u0000";
                    }
                    if(value.contains("%n")) {
                        ArrayList<String> remaining = new ArrayList<>();
                        for(String arg : args) {
                            if(!arg.equals("\u0000")) {
                                remaining.add(arg);
                            }
                        }
                        value = value.replaceAll("%n", String.join(" ", remaining));
                    }
                    if(plugin.getConfig().getBoolean("discord.command-alerts")) System.out.println(e.getAuthor().getName() + " is executing a command: " + value);
                    plugin.getServer().getScheduler().callSyncMethod(plugin, new CommandScheduler(plugin, value));
                    isCommand = true;
                    break;
                }
            }
            if(!isCommand) {
                String username = e.getAuthor().getName();
                String message = e.getMessage().getContentRaw();
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("minecraft.message-format").replaceAll("%username%", username).replaceAll("%message%", message)));
            }
        }
    }

}
