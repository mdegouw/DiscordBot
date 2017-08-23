package it.degouw.bot.commands.etc;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.commands.IPrivateCommand;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.reference.CommandType;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.reference.STATIC;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.omg.CORBA.COMM_FAILURE;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Uptime implements ICommand, IGuildCommand, IPrivateCommand {

    private String getTime(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    private String getTimeDiff(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays + " d, " + parseTimeNumbs(diffHours) + " h, " + parseTimeNumbs(diffMinutes) + " min, " + parseTimeNumbs(diffSeconds) + " sec";
    }

    private String parseTimeNumbs(long time) {
        String timeString = time + "";
        if (timeString.length() < 2)
            timeString = "0" + time;
        return timeString;
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {

        if (!PermissionHandler.check(this.permission(), event)) { return false; }

        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        event.getChannel().sendMessage(
                new EmbedBuilder()
                        .setColor(new Color(255, 71,0))
                        .setDescription(":alarm_clock:   **UPTIME**")
                        .addField("Last restart", getTime(STATIC.lastRestart, "dd.MM.yyyy - HH:mm:ss (z)"), false)
                        .addField("Online since", getTimeDiff(new Date(), STATIC.lastRestart), false)
                        .addField("Reconnects since last restart", STATIC.reconnectCount + "", false)
                        .build()
        ).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: -uptime";
    }

    @Override
    public String description() {
        return "Get stats about the uptime of the bot.";
    }

    @Override
    public CommandType commandType() {
        return CommandType.ETC;
    }

    @Override
    public Perm permission() {
        return Perm.DEFAULT;
    }
}
