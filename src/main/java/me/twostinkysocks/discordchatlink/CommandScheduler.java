package me.twostinkysocks.discordchatlink;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Callable;

public class CommandScheduler implements Callable {

    private String command;
    private JavaPlugin plugin;

    public CommandScheduler(JavaPlugin plugin, String command) {
        this.command = command;
        this.plugin = plugin;
    }
    @Override
    public Object call() throws Exception {
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
        return null;
    }
}
