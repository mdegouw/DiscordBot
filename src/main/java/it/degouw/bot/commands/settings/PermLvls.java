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

public class PermLvls implements ICommand, IGuildCommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {

        if (!PermissionHandler.check(this.permission(), event)) { return false; }

        if (args.length < 2) {
            event.getTextChannel().sendMessage(Messages.error().setDescription(help()).build()).queue();
            return false;
        }

        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        StringBuilder sb = new StringBuilder();
        Arrays.asList(args).subList(1, args.length).forEach(s -> sb.append(s + " "));

        switch (Perm.valueOf(args[0].toUpperCase())) {
            case MEMBER:
                SSSS.setMemberRoles(sb.toString().substring(0, sb.toString().length() - 1), event.getGuild());
                event.getTextChannel().sendMessage(Messages.success().setDescription("Successfully set roles `" + sb.toString().substring(0, sb.toString().length() - 1) + "` to permission level `" + args[0] + "`").build()).queue();
                break;

            case MODERATOR:
                SSSS.setModeratorRoles(sb.toString().substring(0, sb.toString().length() - 1), event.getGuild());
                event.getTextChannel().sendMessage(Messages.success().setDescription("Successfully set roles `" + sb.toString().substring(0, sb.toString().length() - 1) + "` to permission level `" + args[0] + "`").build()).queue();
                break;

            case ADMIN:
                SSSS.setAdminRoles(sb.toString().substring(0, sb.toString().length() - 1), event.getGuild());
                event.getTextChannel().sendMessage(Messages.success().setDescription("Successfully set roles `" + sb.toString().substring(0, sb.toString().length() - 1) + "` to permission level `" + args[0] + "`").build()).queue();
                break;

            case OWNER:
                SSSS.setOwnerRoles(sb.toString().substring(0, sb.toString().length() - 1), event.getGuild());
                event.getTextChannel().sendMessage(Messages.success().setDescription("Successfully set roles `" + sb.toString().substring(0, sb.toString().length() - 1) + "` to permission level `" + args[0] + "`").build()).queue();
                break;

            default:
                event.getTextChannel().sendMessage(Messages.error().setDescription(help()).build()).queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: -permlvl <lvl 1/2> <role1>, <role2>, ...";
    }

    @Override
    public String description() {
        return "Set roles for permission levels.";
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
