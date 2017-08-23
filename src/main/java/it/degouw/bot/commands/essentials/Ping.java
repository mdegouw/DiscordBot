package it.degouw.bot.commands.essentials;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IPrivateCommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.reference.CommandType;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.reference.STATIC;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Date;

public class Ping implements ICommand, IPrivateCommand, IGuildCommand {

    private final String HELP = "USAGE: .ping";

    private static long inputTime;

    public static void setInputTime(long inputTimeLong) { inputTime = inputTimeLong; }

    private Color getColorByPing(long ping) {
        if (ping < 100)
                return Color.cyan;
        if (ping < 400)
            return Color.green;
        if (ping < 700)
            return Color.yellow;
        if (ping < 1000)
            return Color.orange;
        return Color.red;
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) { return true; }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        long processing = new Date().getTime() - inputTime;
        long ping = event.getJDA().getPing();
        event.getChannel().sendMessage(new EmbedBuilder().setColor(getColorByPing(processing + ping)).setDescription(
                String.format(":ping_pong:   **Pong!**\n\nThe bot took `%s` milliseconds to response.\nIt took `%s` milliseconds to parse the command and the ping took `%s` milliseconds.",
                        processing + ping, processing, ping)
        ).build()).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public String description() {return "pong!"; }

    @Override
    public CommandType commandType() {
        return CommandType.ESSENTIALS;
    }

    @Override
    public Perm permission() { return Perm.DEFAULT; }

}
