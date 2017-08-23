package it.degouw.bot.listeners;

import it.degouw.bot.commands.settings.BotMessage;
import it.degouw.bot.update.UpdateClient;
import it.degouw.bot.handler.StartArgumentHandler;
import it.degouw.bot.permissions.SSSS;
import it.degouw.bot.reference.STATIC;
import it.degouw.bot.util.Logger;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ReadyListener  extends ListenerAdapter{
    static ReadyEvent readyEvent;

    private static void handleStartArgs() {

        String[] args = StartArgumentHandler.args;

        if (args.length > 0) {
            switch (args[0]) {
                case "-restart":
                    for (Guild g : readyEvent.getJDA().getGuilds()) {
                        g.getPublicChannel().sendMessage(
                                ":ok_hand:  Bot successfully restarted!"
                        ).queue();
                    }
                    break;

                case "-update":
                    for (Guild g : readyEvent.getJDA().getGuilds()) {
                        g.getPublicChannel().sendMessage(
                                ":ok_hand:  Bot successfully updated to version v." + STATIC.VERSION + "!\n\n" +
                                        "**Changelogs:** http://github.zekro.de/DiscordBot/blob/master/README.md#latest-changelogs\n" +
                                        "Github Repository: http://github.zekro.de/DiscordBot"
                        ).queue();
                    }
                    break;
            }
        }

    }

    @Override
    public void onReady(ReadyEvent event) {

        readyEvent = event;

        SSSS.checkFolders(event.getJDA().getGuilds());

        StringBuilder sb = new StringBuilder();
        event.getJDA().getGuilds().forEach(guild -> sb.append("|  - \"" + guild.getName() + "\" - {@" + guild.getOwner().getUser().getName() + "#" + guild.getOwner().getUser().getDiscriminator() + "} - [" + guild.getId() + "] \n"));

        System.out.println(String.format(
                "\n\n" +
                        "#------------------------------------------------------------------------- - - -  -  -  -   -\n" +
                        "| %s - v.%s (JDA: v.%s)\n" +
                        "#------------------------------------------------------------------------- - - -  -  -  -   -\n" +
                        "| Running on %s guilds: \n" +
                        "%s" +
                        "#------------------------------------------------------------------------- - - -  -  -  -   -\n\n",
                Logger.Cyan + Logger.Bold + "TestBot" + Logger.Reset, STATIC.VERSION, "3.2.0_242", event.getJDA().getGuilds().size(), sb.toString()));

        if (STATIC.BOT_OWNER_ID == 0) {
            Logger.ERROR(
                    "#######################################################\n" +
                            "# PLEASE INSERT YOUR DISCORD USER ID IN SETTINGS.TXT  #\n" +
                            "# AS ENTRY 'BOT_OWNER_ID' TO SPECIFY THAT YOU ARE THE #\n" +
                            "# BOTS OWNER!                                         #\n" +
                            "#######################################################"
            );
        }

        BotMessage.setSupplyingMessage(event.getJDA());

        //TODO: Warframe Alerts time stuff

        STATIC.lastRestart = new Date();

        handleStartArgs();

        if (STATIC.enableWarframeAlerts && !System.getProperty("os.name").contains("Windows")) {
            System.out.println("[INFO] BotUtils: " + System.getProperty("os.name") + " detected - enabled warframe alerts.");
        }

        if (STATIC.autoUpdate) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    UpdateClient.checkIfUpdate(event.getJDA());
                }
            }, 0, 10 * 60 * 1000);

        }

        //TODO: Load Polls
    }

}
