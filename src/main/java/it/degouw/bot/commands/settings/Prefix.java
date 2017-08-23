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

public class Prefix implements ICommand, IGuildCommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {

        if (!PermissionHandler.check(this.permission(), event)) { return false; }

        if (args.length < 1) {
            event.getTextChannel().sendMessage(Messages.error().setDescription(":warning: Please enter a valid prefix!").build()).queue();
            return false;
        }

        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        SSSS.setPrefix(args[0], event.getGuild());
        event.getTextChannel().sendMessage(Messages.success().setDescription("Prefix successfully changed to `" + args[0] + "`.").build()).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: -prefix <new prefix>";
    }

    @Override
    public String description() {
        return "Set the command prefix for this server";
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
