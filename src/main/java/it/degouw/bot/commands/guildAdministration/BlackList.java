package it.degouw.bot.commands.guildAdministration;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.permissions.SSSS;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.reference.STATIC;
import it.degouw.bot.util.Messages;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

public class BlackList implements ICommand, IGuildCommand{

    public static boolean check(User user, Guild guild) {
        List<String> blackList = SSSS.getBlacklist(guild);
        if (blackList.contains(user.getId())) {
            user.openPrivateChannel().complete().sendMessage(Messages.error().setDescription("Sorry but you are currently not allowed to use this bot's commands!\nMessage a support or the server owner to remove you from blacklist.").build()).queue();
            return true;
        }
        return false;
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {

        if (!PermissionHandler.check(Perm.ADMIN, event)) { return false; }

        if (args.length < 1) {
            event.getChannel().sendMessage(this.help()).queue();
            return false;
        }

        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        List<String> blackList = SSSS.getBlacklist(event.getGuild());
        User victim;


        if (args.length > 0) {
            if (event.getMessage().getMentionedUsers().size() > 0)
                victim = event.getMessage().getMentionedUsers().get(0);
            else {
                event.getTextChannel().sendMessage(Messages.error().setDescription("Please mention a valid user to blacklist.").build()).queue();
                return;
            }
            if (blackList.contains(victim.getId())) {
                blackList.remove(victim.getId());
                event.getTextChannel().sendMessage(Messages.success().setDescription("Successfully removed " + victim.getAsMention() + " from blacklist.").build()).queue();
            } else {
                blackList.add(victim.getId());
                event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.orange).setDescription(event.getAuthor().getAsMention() + " added " + victim.getAsMention() + " to blacklist.").build()).queue();
            }
            System.out.println(blackList.size());
            SSSS.setBlacklist(blackList, event.getGuild());
        } else {
            String out = blackList.stream().map(id -> ":white_small_square:   " + event.getGuild().getMemberById(id).getEffectiveName()).collect(Collectors.joining("\n"));
            event.getTextChannel().sendMessage(new EmbedBuilder().setDescription("**BLACKLISTED MEMBERS**\n\n" + out).build()).queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "**USAGE: **\n`-blacklist <@menrion>`";
    }

    @Override
    public String description() {
        return "Disallow users to use the bots commands.";
    }

    @Override
    public String commandType() {
        return STATIC.CMDTYPE.guildadmin;
    }

    @Override
    public Perm permission() {
        return Perm.ADMIN;
    }
}
