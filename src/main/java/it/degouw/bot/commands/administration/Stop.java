package it.degouw.bot.commands.administration;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.commands.IPrivateCommand;
import it.degouw.bot.commands.etc.BotStats;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.reference.STATIC;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

public class Stop implements ICommand, IGuildCommand, IPrivateCommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event)
    {

        if (!PermissionHandler.isBotOwner(event.getAuthor(), event.getChannel())) { return false; }

        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        BotStats.save();

        event.getChannel().sendMessage(":warning: :mobile_phone_off:   " + event.getAuthor().getAsMention() + " shut down " + event.getJDA().getSelfUser().getAsMention() + " because of maintenance or an unexpected behavior.").queue();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.exit(0);
            }
        }, 1000);

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: -stop";
    }

    @Override
    public String description() {
        return "Emergency stop the bot.";
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
