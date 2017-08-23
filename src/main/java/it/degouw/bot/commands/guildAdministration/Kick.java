package it.degouw.bot.commands.guildAdministration;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.reference.CommandType;
import it.degouw.bot.reference.Perm;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

public class Kick implements ICommand, IGuildCommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {

        if (!PermissionHandler.check(this.permission(), event)) { return false; }

        if (args.length < 1) {
            event.getChannel().sendMessage(help()).queue();
            return false;
        }

        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        User traitor = event.getMessage().getMentionedUsers().get(0);

        StringBuilder argsString = new StringBuilder();
        Arrays.stream(args).forEach(s -> argsString.append(" " + s));
        String reason = argsString.toString().replace("@" + event.getGuild().getMember(traitor).getEffectiveName(), "").substring(1);

        event.getTextChannel().sendMessage(
                ":small_red_triangle_down:  " + event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0)).getAsMention() + " got kicked by " + event.getAuthor().getAsMention() + " (" + event.getMember().getRoles().get(0).getName() + ").\n\n" +
                        "Reason: " + reason
        ).queue();

        PrivateChannel pc = event.getMessage().getMentionedUsers().get(0).openPrivateChannel().complete();
        pc.sendMessage(
                "Sorry, you got kicked from Server " + event.getGuild().getName() + " by " + event.getAuthor().getAsMention() + " (" + event.getMember().getRoles().get(0).getName() + ").\n\n" +
                        "Reason: " + reason
        ).queue();


        event.getGuild().getController().kick(
                event.getGuild().getMember(
                        event.getMessage().getMentionedUsers().get(0)
                )
        ).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "sd";
    }

    @Override
    public String description() {
        return "Kick a member from the server.";
    }

    @Override
    public CommandType commandType() {
        return CommandType.GUILDADMIN;
    }

    @Override
    public Perm permission() {
        return Perm.ADMIN;
    }
}
