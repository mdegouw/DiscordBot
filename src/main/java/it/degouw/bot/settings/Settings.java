package it.degouw.bot.settings;

import com.moandjiezana.toml.Toml;
import it.degouw.bot.reference.STATIC;

import java.io.*;

public class Settings {

    private static File sfile = new File("SETTINGS.txt");
    private static Toml toml;

    public static class SCONT {
        static final String TOKEN = "TOKEN";
        static final String PREFIX = "CMD_PREFIX";
        static final String PRIVATE_PREFIX = "PRIVATE_PREFIX";
        static final String CUSTOM_MESSAGE = "CUSTOM_PLAYING_MESSAGE";
        static final String WARFRAME_ALERTS_TEXTCHANNEL = "WARFRAME_ALERTS_TEXTCHANNEL";
        static final String WARFRAME_ALERTS_REFRESHTIME = "WARFRAME_ALERTS_REFRESHTIME";
        static final String DOCID_WARFRAME_ALERTS_FILTER = "DOCID_WARFRAME_ALERTS_FILTER";
        static final String DOCID_JOKES = "DOCID_JOKES";
        static final String COMMAND_CONSOLE_OUTPUT = "COMMAND_CONSOLE_OUTPUT";
        static final String KICK_VOICE_CHANNEL = "KICK_VOICE_CHANNEL";
        static final String TTT_SERVER_IP = "TTT_SERVER_IP";
        static final String TTT_SERVER_PORT = "TTT_SERVER_PORT";
        static final String UPDATE_INFO = "UPDATE_INFO";
        static final String MUSIC_CHANNEL = "MUSIC_CHANNEL";
        static final String GUILD_JOIN_ROLE = "GUILD_JOIN_ROLE";
        static final String MUSIC_COMMANDS_ONLY_IN_MUSIC_CHANNEL = "MUSIC_COMMANDS_ONLY_IN_MUSIC_CHANNEL";
        static final String MUSIC_VOLUME = "MUSIC_VOLUME";
        static final String DISCORD_JOIN_MESSAGE = "DISCORD_JOIN_MESSAGE";
        static final String BOT_OWNER_ID = "BOT_OWNER_ID";
        static final String MUSIC_BUFFER = "MUSIC_BUFFER";
        static final String OWNER_ROLES = "OWNER_ROLES";
        static final String ADMIN_ROLES = "ADMIN_ROLES";
        static final String MODERATOR_ROLES = "MODERATOR_ROLES";
        static final String MEMBER_ROLES = "MEMBER_ROLES";
    }

    public static boolean testForToken() { return (toml.getString(SCONT.TOKEN).length() > 0);}

    public static void loadSettings() throws IOException {

        if (!sfile.exists()) {

            BufferedWriter br = new BufferedWriter(new FileWriter(sfile));

            br.write(

                    "#########################\n" +
                            "# SETTINGS FILE FOR BOT #\n" +
                            "#  PLEASE DON'T DELETE  #\n" +
                            "#########################\n" +
                            "\n" +
                            "# GENERAL SERVER SETTINGS #\n" +
                            "\n" +
                            "    # Enter here your Discord API Token you'll get from here: https://discordapp.com/developers/applications/me\n" +
                            "        TOKEN = \"\"\n" +
                            "    # Prefix to send bot commands (like -help or ~help or .help, what ever you want)\n" +
                            "        CMD_PREFIX = \"-\"\n" +
                            "        PRIVATE_PREFIX \".\"\n" +
                            "    # Bot owners user ID\n" +
                            "        BOT_OWNER_ID = 0\n" +
                            "    # Custom message shown as \"Now Playing: ...\" text\n" +
                            "        CUSTOM_PLAYING_MESSAGE = \"zekro.de\"\n" +
                            "    # Log entered command in console of the bot\n" +
                            "        COMMAND_CONSOLE_OUTPUT = true\n" +
                            "    # Automatically check for updates and inform you if there is a new update available\n" +
                            "        UPDATE_INFO = true\n" +
                            "    # Only allow members to use music commands in music channel\n" +
                            "        MUSIC_COMMANDS_ONLY_IN_MUSIC_CHANNEL = false\n" +
                            "    # Volume in percent (0 - 100, 0 -> Disabled -> 100% volume)\n" +
                            "        MUSIC_VOLUME = 0\n" +
                            "    # Music buffer in milliseconds\n" +
                            "        MUSIC_BUFFER = 5000" +
                            "\n" +
                            "# PERMISSION SETTINGS #\n" +
                            "\n" +
                            "    # Any role can execute the commands of the lower rank\n" +
                            "    # List roles that can use Owner Commands\n" +
                            "        OWNER_ROLES = \"Owner\"\n" +
                            "    # List roles that can use Admin Commands\n" +
                            "        ADMIN_ROLES = \"Admin\"\n" +
                            "    # List roles that can use Moderator Commands\n" +
                            "        MODERATOR_ROLES = \"Moderator, Bot Commander\"\n" +
                            "    # List roles that can use Member Commands\n" +
                            "        MEMBER_ROLES = \"Member\"\n" +
                            "    # Automatically assign that role to joined users (Let free for nothing)\n" +
                            "        GUILD_JOIN_ROLE = \"Member\"\n" +
                            "\n" +
                            "# SOURCES SETTINGS (GOOGLE DOCS) #\n" +
                            "\n" +
                            "    # Create a Google Docs Doc for this, publish it and enter doc id here\n" +
                            "        DOCID_WARFRAME_ALERTS_FILTER = \"\"\n" +
                            "    # Same here as above\n" +
                            "        DOCID_JOKES = \"\"\n" +
                            "\n" +
                            "# CHANNEL SETTINGS #\n" +
                            "\n" +
                            "    # Music channel for displaying now playing info\n" +
                            "        MUSIC_CHANNEL = \"music\"\n" +
                            "    # Alternative voice channel vor vkicks\n" +
                            "        KICK_VOICE_CHANNEL = \"Lobby\"\n" +
                            "\n" +
                            "# WARFRAME ALERTS SETTINGS #\n" +
                            "\n" +
                            "    # Refresh of alerts list in seconds\n" +
                            "        WARFRAME_ALERTS_REFRESHTIME = 10\n" +
                            "    # Text channel for warframe alerts list\n" +
                            "        WARFRAME_ALERTS_TEXTCHANNEL = \"\"\n" +
                            "\n" +
                            "\n" +
                            "# MESSAGE SETTINGS #\n" +
                            "\n" +
                            "       # Message, that appears, if user joins discord first time\n" +
                            "       # \"[USER]\" stand for the @user - mention\n" +
                            "       # \"[GUILD]\" stand for the guild name\n" +
                            "           DISCORD_JOIN_MESSAGE = \":heart: Hey, [USER]! Welcome on the [GUILD]! :heart:\"\n"

            );

            br.close();

        } else {

            toml = new Toml().read(new FileInputStream(sfile));

            STATIC.TOKEN = toml.getString(SCONT.TOKEN);
            STATIC.PREFIX = toml.getString(SCONT.PREFIX);
            STATIC.PRIVATE_PREFIX = toml.getString(SCONT.PRIVATE_PREFIX);
            STATIC.CUSTOM_MESSAGE = toml.getString(SCONT.CUSTOM_MESSAGE);
            STATIC.warframeAlertsChannel = toml.getString(SCONT.WARFRAME_ALERTS_TEXTCHANNEL);
            STATIC.refreshTime = Math.toIntExact(toml.getLong(SCONT.WARFRAME_ALERTS_REFRESHTIME));
            STATIC.commandConsoleOutout = toml.getBoolean(SCONT.COMMAND_CONSOLE_OUTPUT);
            STATIC.KICK_VOICE_CHANNEL = toml.getString(SCONT.KICK_VOICE_CHANNEL);
            STATIC.autoUpdate = toml.getBoolean(SCONT.UPDATE_INFO);
            STATIC.musicChannel = toml.getString(SCONT.MUSIC_CHANNEL);
            STATIC.guildJoinRole = toml.getString(SCONT.GUILD_JOIN_ROLE);
            STATIC.musicCommandsOnlyInMusicChannel = toml.getBoolean(SCONT.MUSIC_COMMANDS_ONLY_IN_MUSIC_CHANNEL);
            STATIC.music_volume = Math.toIntExact(toml.getLong(SCONT.MUSIC_VOLUME));
            STATIC.discordJoinMessage = toml.getString(SCONT.DISCORD_JOIN_MESSAGE);
            STATIC.BOT_OWNER_ID = toml.getLong(SCONT.BOT_OWNER_ID);
            STATIC.MUSIC_BUFFER = Math.toIntExact(toml.getLong(SCONT.MUSIC_BUFFER));
            STATIC.OwnerPerms = toml.getString(SCONT.OWNER_ROLES).split(", ");
            STATIC.AdminPerms = toml.getString(SCONT.ADMIN_ROLES).split(", ");
            STATIC.ModeratorPerms = toml.getString(SCONT.MODERATOR_ROLES).split(", ");
            STATIC.MemberPerms = toml.getString(SCONT.MEMBER_ROLES).split(", ");
        }
    }

}
