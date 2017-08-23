package it.degouw.bot.commands.essentials;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.reference.CommandType;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.reference.STATIC;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

public class UserInfo implements ICommand, IGuildCommand {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {

        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        Member memb;

        if (args.length > 0) {
            memb = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
        } else {
            memb = event.getMember();
        }

        String NAME = memb.getEffectiveName();
        String TAG = memb.getUser().getName() + "#" + memb.getUser().getDiscriminator();
        String GUILD_JOIN_DATE = memb.getJoinDate().format(DateTimeFormatter.RFC_1123_DATE_TIME);
        String DISCORD_JOINED_DATE = memb.getUser().getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME);
        String ID = memb.getUser().getId();
        String STATUS = memb.getOnlineStatus().getKey();
        String ROLES = "";
        String GAME;
        String AVATAR = memb.getUser().getAvatarUrl();

        try {
            GAME = memb.getGame().getName();
        } catch (Exception e) {
            GAME = "-/-";
        }

        for ( Role r : memb.getRoles() ) {
            ROLES += r.getName() + ", ";
        }
        if (ROLES.length() > 0)
            ROLES = ROLES.substring(0, ROLES.length()-2);
        else
            ROLES = "No roles on this server.";

        if (AVATAR == null) {
            AVATAR = "No Avatar";
        }

        EmbedBuilder em = new EmbedBuilder().setColor(Color.GREEN);
        em.setDescription(":spy:   **User information for " + memb.getUser().getName() + ":**")
                .addField("Name / Nickname", NAME, false)
                .addField("User Tag", TAG, false)
                .addField("ID", ID, false)
                .addField("Current Status", STATUS, false)
                .addField("Current Game", GAME, false)
                .addField("Roles", ROLES, false)
                .addField("Guild Permission Level", PermissionHandler.getPerm(memb) + "", false)
                .addField("Guild Joined", GUILD_JOIN_DATE, false)
                .addField("Discord Joined", DISCORD_JOINED_DATE, false)
                .addField("Avatar-URL", AVATAR, false);

        if (AVATAR != "No Avatar") {
            em.setThumbnail(AVATAR);
        }

        event.getTextChannel().sendMessage(
                em.build()
        ).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: -userinfo <@user>";
    }

    @Override
    public String description() {
        return "Get some info about a user";
    }

    @Override
    public CommandType commandType() {
        return CommandType.ESSENTIALS;
    }

    @Override
    public Perm permission() {
        return Perm.DEFAULT;
    }

}
