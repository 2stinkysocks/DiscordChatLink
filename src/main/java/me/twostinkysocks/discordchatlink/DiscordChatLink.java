package me.twostinkysocks.discordchatlink;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.File;

public final class DiscordChatLink extends JavaPlugin implements Listener {

    public JDA client;

    @Override
    public void onEnable() {
        File configFile = new File(getDataFolder(), "config.yml");
        if(!configFile.exists()) {
            saveDefaultConfig();
        }
        reloadConfig();
        try {
            client = JDABuilder.createLight(getConfig().getString("discord.bot-token"), GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(new DiscordManager(this))
                    .build()
                    .awaitReady();

        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

        getServer().getPluginManager().registerEvents(new MinecraftManager(this, client), this);
    }

}
