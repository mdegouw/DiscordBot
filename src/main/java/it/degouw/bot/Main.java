package it.degouw.bot;


import it.degouw.bot.commands.EventListener;
import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.administration.*;
import it.degouw.bot.commands.chat.*;
import it.degouw.bot.commands.essentials.*;
import it.degouw.bot.commands.etc.*;
import it.degouw.bot.commands.guildAdministration.*;
import it.degouw.bot.commands.music.Music;
import it.degouw.bot.commands.settings.*;
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

        // Administration
        commands.put("broadcast", new Broadcast());
        commands.put("guilds", new Guilds());
        commands.put("restart", new Restart());
        commands.put("stop", new Stop());
        commands.put("update", new Update());

        // Guild Administration
        commands.put("vkick", new VoiceKick());
        commands.put("blacklist", new BlackList());
        commands.put("spacer", new Spacer());
        commands.put("report", new Report());
        commands.put("mute", new Mute());
        commands.put("moveall", new MoveAll());
        commands.put("kick", new Kick());

        // Chat
        commands.put("cat", new Cat());

        // Essentials
        commands.put("help", new Help());
        commands.put("ping", new Ping());
        commands.put("stats", new Stats());
        commands.put("info", new Info());
        commands.put("userinfo", new UserInfo());

        // ETC
        commands.put("bug", new Bug());
        commands.put("botstats", new BotStats());
        commands.put("uptime", new Uptime());
        commands.put("speedtest", new SpeedTest());
        commands.put("rand6", new Rand6());
        commands.put("log", new Log());
        commands.put("cmdlog", new CmdLog());


        // Settings
        commands.put("botmsg", new BotMessage());
        commands.put("autorole", new AutoRole());
        commands.put("permlvl", new PermLvls());
        commands.put("prefix", new Prefix());
        commands.put("joinmsg", new ServerJoinMessage());
        commands.put("leavemsg", new ServerLeftMessage());
        commands.put("settings", new it.degouw.bot.commands.settings.Settings());

        // Music
        commands.put("music", new Music());
        commands.put("m", new Music());




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
