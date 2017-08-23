package it.degouw.bot.reference;

import it.degouw.bot.Main;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import java.util.ArrayList;
import java.util.Date;

public class STATIC {

    public static String TOKEN = "";

    //######### GENERAL BOT SETTINGS #########//

    public static String VERSION = "1.0.0.0";
    public static String THISBUILD = BUILDTYPE.STABLE;

    public static class BUILDTYPE {
        public static final String STABLE = "STABLE";
        public static final String UNSTABLE = "UNSTABLE";
        public static final String UNTESTED = "UNTESTED";
        public static final String TESTING_BUILD = "TESTING_BUILD";
    }

    public static String PREFIX = ".";
    public static String PRIVATE_PREFIX = ".";

    public static String CUSTOM_MESSAGE = "DANKMEMES";

    public static OnlineStatus STATUS = OnlineStatus.ONLINE;

    public static Game GAME = new Game() {
      public String getName() { return CUSTOM_MESSAGE + " | " + PREFIX + "help | v." + VERSION + "_" + THISBUILD; }
      public String getUrl() { return "http://degouw.it"; }
      public GameType getType() { return GameType.DEFAULT; }
    };

    //######### WARFRAME ALERTS SETTINGS #########//

    public static int refreshTime = 10;

    public static String warframeAlertsChannel = "warframealerts";

    public static boolean enableWarframeAlerts = true;



    //######### PERMISSION SETTINGS #########//

    public static String[] MemberPerms = {"Member"};
    public static String[] ModeratorPerms = {"Moderator", "Bot Commander"};
    public static String[] AdminPerms = {"Admin"};
    public static String[] OwnerPerms = {"Owner"};


    public static String guildJoinRole = "";

    //########### OTHER SETTINGS ###########//

    public static String voiceLogChannel = "voicelog";

    public static String musicChannel = "mucke";

    public static boolean commandConsoleOutout = true;

    public static String KICK_VOICE_CHANNEL = "";


    public static boolean autoUpdate = true;

    public static boolean musicCommandsOnlyInMusicChannel = false;

    public static String input;

    public static int music_volume = 0;

    public static String discordJoinMessage = ":heart: Hey, [USER]! Welcome on the [GUILD]! :heart:";

//    public class CMDTYPE {
//        public static final String administration = "Administration";
//        public static final String chatutils = "Chat Utilities";
//        public static final String essentials = "Essentials";
//        public static final String etc = "Etc";
//        public static final String music = "Music";
//        public static final String guildadmin = "Guild Administration";
//        public static final String settings = "SettingsCore";
//    }

    public static Date lastRestart;

    public static int reconnectCount = 0;

    public static ArrayList<ArrayList<String>> cmdLog = new ArrayList<>();

    public static long BOT_OWNER_ID = 0;

    public static int MUSIC_BUFFER = 1000;

}

