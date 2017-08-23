package it.degouw.bot.commands.settings;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.permissions.SSSS;
import it.degouw.bot.reference.CommandType;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.util.Messages;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

public class ServerLeftMessage implements ICommand, IGuildCommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {

        if (!PermissionHandler.check(this.permission(), event)) { return false; }

        if (args.length < 1) {
            event.getTextChannel().sendMessage(Messages.error().setDescription(help()).build()).queue();
            return false;
        }

        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        StringBuilder sb = new StringBuilder();
        Arrays.stream(args).forEach(s -> sb.append(s + " "));

        SSSS.setServerLeaveMessage(sb.toString().substring(0, sb.toString().length() - 1), event.getGuild());
        event.getTextChannel().sendMessage(Messages.success().setDescription("Server leave message successfully changed to `" + sb.toString().substring(0, sb.toString().length() - 1) + "`.").build()).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: -leavemsg <message>\n" +
                "Set message to `OFF` to disable server leave message.\n" +
                "Use `[USER]` to mention leaved user and `[GUILD]` to enter guild name in message.";
    }

    @Override
    public String description() {
        return "Set the message when a guild member leaves the guild.";
    }

    @Override
    public CommandType commandType() {
        return CommandType.SETTINGS;
    }

    @Override
    public Perm permission() {
        return Perm.OWNER;
    }
}
