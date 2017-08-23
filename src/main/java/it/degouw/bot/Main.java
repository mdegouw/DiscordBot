package it.degouw.bot;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.chat.Cat;
import it.degouw.bot.commands.essentials.Ping;
import it.degouw.bot.commands.etc.BotStats;
import it.degouw.bot.commands.etc.Bug;
import it.degouw.bot.commands.etc.Bug2;
import it.degouw.bot.commands.guildAdministration.VoiceKick;
import it.degouw.bot.handler.StartArgumentHandler;
import it.degouw.bot.listeners.*;
import it.degouw.bot.reference.STATIC;
import it.degouw.bot.settings.Settings;
import it.degouw.bot.util.CommandParser;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Main {

    static JDABuilder builder;

    public static JDA jda;

    public static final CommandParser parser = new CommandParser();

    public static HashMap<String, ICommand> commands = new HashMap<>();

    public static void main(String[] args) throws IOException {

        StartArgumentHandler.args = args;

        Settings.loadSettings();

        BotStats.load();

        try {
            if (!Settings.testForToken()) {
                System.out.println("[ERROR] PLEASE ENTER YOUR DISCORD API TOKEN FROM 'https://discordapp.com/developers/applications/me' IN THE TEXTFILE 'SETTINGS.txt' AND RESTART!");
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] PLEASE ENTER YOUR DISCORD API TOKEN FROM 'https://discordapp.com/developers/applications/me' IN THE TEXTFILE 'SETTINGS.txt' AND RESTART!");
            System.exit(0);
        }

        File savePath = new File("saves_playlists");
        if (!savePath.exists() || !savePath.isDirectory()) {
            System.out.println(
                    savePath.mkdir() ? "[INFO] Path \"saves_playlists\" successfully created!" : "[ERROR] Failed to create path \"saves_playlists\"!"
            );
        }

        builder = new JDABuilder(AccountType.BOT)
                .setToken(STATIC.TOKEN)
                .setAudioEnabled(true)
                .setAutoReconnect(true)
                .setStatus(STATIC.STATUS)
                .setGame(STATIC.GAME);

        initializeListeners();
        initializeCommands();

        try {
            builder.buildBlocking();
        } catch (LoginException | InterruptedException | RateLimitedException e) {
            e.printStackTrace();
        }

    }

    private static void initializeCommands() {

        commands.put("botstats", new BotStats());
        commands.put("ping", new Ping());
        commands.put("bug", new Bug());
        commands.put("vkick", new VoiceKick());
        commands.put("cat", new Cat());
        commands.put("bug2", new Bug2());

    }

    private static void initializeListeners() {

        builder.addEventListener(new ReadyListener());
        builder.addEventListener(new MessageListener());
        builder.addEventListener(new ReconnectListener());
        builder.addEventListener(new VoiceChannelListener());
        builder.addEventListener(new VoiceKickListener());
        builder.addEventListener(new ReactionListener());
        builder.addEventListener(new GuildJoinListener());
        builder.addEventListener(new BotJoinListener());

    }

}
