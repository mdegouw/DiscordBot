package it.degouw.bot.commands.administration;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.commands.IPrivateCommand;
import it.degouw.bot.commands.update.UpdateClient;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.reference.STATIC;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;

public class Update implements ICommand, IPrivateCommand, IGuildCommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {

        if (!PermissionHandler.isBotOwner(event.getAuthor(), event.getChannel())) { return false; }

        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        UpdateClient.manualCheck(event.getMessage().getTextChannel());

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: -UpdateClient";
    }

    @Override
    public String description() {
        return "UpdateClient discord bot.";
    }

    @Override
    public String commandType() {
        return STATIC.CMDTYPE.administration;
    }

    @Override
    public Perm permission() {
        return Perm.BOT_OWNER;
    }
}