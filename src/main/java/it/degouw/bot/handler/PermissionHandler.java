package it.degouw.bot.handler;

import it.degouw.bot.permissions.SSSS;
import it.degouw.bot.util.Messages;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.reference.STATIC;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Arrays;

public class PermissionHandler {

    public static boolean isBotOwner(User user, MessageChannel channel) {
        if (STATIC.BOT_OWNER_ID == 0) {
            channel.sendMessage(Messages.error().setDescription("There is no owner ID set in `SETTINGS.txt`.\nIf you are the owner of this bot, please add your Discord user id in the `SETTINGS.txt`!").build()).queue();
            return false;
        }
        if (user.getId().equals(String.valueOf(STATIC.BOT_OWNER_ID)))
            return true;
        channel.sendMessage(Messages.error().setDescription("Only the bots owner (" + user.getJDA().getUserById(STATIC.BOT_OWNER_ID).getAsMention() + ") can use this command.").build()).queue();
        return false;
    }

    public static boolean isServerOwner(MessageReceivedEvent event) {
        if (check(Perm.SERVER_OWNER, event))
            return true;
        event.getChannel().sendMessage(Messages.error().setDescription("Only the server owner (" + event.getGuild().getOwner().getAsMention() + ") can use this command.").build()).queue();
        return false;
    }



    public static Perm getPerm(Member member) {
        if (member.getUser().getId().equals(String.valueOf(STATIC.BOT_OWNER_ID))) {
            return Perm.BOT_OWNER;
        } else if (member.equals(member.getGuild().getOwner())) {
            return Perm.SERVER_OWNER;
        } else if (member.getRoles().stream().anyMatch(role -> Arrays.stream(SSSS.getOwnerRoles(member.getGuild())).anyMatch(s1 -> role.getName().equals(s1)))) {
            return Perm.OWNERS;
        } else if (member.getRoles().stream().anyMatch(role -> Arrays.stream(SSSS.getAdminRoles(member.getGuild())).anyMatch(s1 -> role.getName().equals(s1)))) {
            return Perm.ADMIN;
        } else if (member.getRoles().stream().anyMatch(role -> Arrays.stream(SSSS.getModeratorRoles(member.getGuild())).anyMatch(s1 -> role.getName().equals(s1)))) {
            return Perm.MODERATOR;
        } else if (member.getRoles().stream().anyMatch(role -> Arrays.stream(SSSS.getMemberRoles(member.getGuild())).anyMatch(s1 -> role.getName().equals(s1)))) {
            return Perm.MEMBER;
        }
        return Perm.DEFAULT;
    }

    public static boolean check (Perm required, MessageReceivedEvent event) {
        if (required.getNumVal() > getPerm(event.getMember()).getNumVal()) {
            event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription(
                    "Sorry but you need permission level `" + required + "` or above!\n(Your current permission level is `" + getPerm(event.getMember()) + "`)."
            ).build()).queue();
            return false;
        }
        return true;
    }

}
