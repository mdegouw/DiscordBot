package it.degouw.bot.commands.guildAdministration;

import com.sun.istack.internal.NotNull;
import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.reference.CommandType;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.util.Messages;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;

public class Spacer implements ICommand, IGuildCommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {

        if (!PermissionHandler.check(this.permission(), event)) { return false; }

        if (args.length < 1) {
            event.getChannel().sendMessage(Messages.error().setDescription(help()).build()).queue();
            return false;
        }

        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        switch (args[0]) {

            case "add":

                if (!event.getMember().getVoiceState().inVoiceChannel()) {
                    event.getTextChannel().sendMessage(Messages.error().setDescription("You need to be in a voice channel to add a spacer.").build()).queue();
                    return;
                }

                Channel vc = event.getGuild().getController().createVoiceChannel("-------------------------").complete();
                event.getGuild().getController().modifyVoiceChannelPositions().selectPosition(vc.getPosition()).moveTo(event.getMember().getVoiceState().getChannel().getPosition() + 1).queue();

                break;

        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }


    @Override
    public String help() {
        return "Help";
    }

    @Override
    public String description() {
        return "Create spacer voice channels";
    }

    @Override
    public CommandType commandType() {
        return CommandType.GUILDADMIN;
    }

    @Override
    public Perm permission() {
        return Perm.MODERATOR;
    }
}
