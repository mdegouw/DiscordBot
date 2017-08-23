package it.degouw.bot.commands.settings;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.permissions.SSSS;
import it.degouw.bot.reference.CommandType;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.util.Messages;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class AutoRole implements ICommand, IGuildCommand {

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
        List<Role> autoRole = event.getGuild().getRolesByName(sb.toString().substring(0, sb.length() - 1), true);

        if (event.getMessage().getMentionedRoles().size() > 0) {
            SSSS.setAutoRole(event.getMessage().getMentionedRoles().get(0).getName(), event.getGuild());
            event.getTextChannel().sendMessage(Messages.success().setDescription("Successfully set autorole to `" + event.getMessage().getMentionedRoles().get(0).getName() + "`.").build()).queue();
        } else if (autoRole.size() > 0) {
            SSSS.setAutoRole(autoRole.get(0).getName(), event.getGuild());
            event.getTextChannel().sendMessage(Messages.success().setDescription("Successfully set autorole to `" + autoRole.get(0).getName() + "`.").build()).queue();
        } else {
            SSSS.setAutoRole("", event.getGuild());
            event.getTextChannel().sendMessage(Messages.success().setDescription("Successfully deactivated autorole.").build()).queue();
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: -autorole <role name or role as mention>\n*Hint: Set role to a role that does not exist on the server to disable this function.*";
    }

    @Override
    public String description() {
        return "Set the role members get automatically assigned by joining the server.";
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