package it.degouw.bot.commands.guildAdministration;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.commands.IPrivateCommand;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.permissions.SSSS;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.reference.STATIC;
import it.degouw.bot.util.Messages;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.List;

public class VoiceKick implements ICommand, IGuildCommand {
    public static HashMap<Member, VoiceChannel> kicks = new HashMap<>();

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {

        if (!PermissionHandler.check(Perm.MODERATOR, event)) return false;

        if (args.length < 1) {
            event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("Usage").setDescription(
                    "**-vkick <@user> (<timeout in seconds>)**  -  `Kick someone out of the voice channel (if timeout is entered, the kicked user cant rejoin the channel in timeout period)`\n" +
                            "**-vkick channel <channel name>**  -  `Set the voice kick channel`"
            ).build()).queue();
            return false;
        }
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        if (args[0].toLowerCase().equals("channel")) {
            if (PermissionHandler.check(Perm.ADMIN, event))
            if (args.length > 1) {

                StringBuilder sb = new StringBuilder();
                Arrays.stream(args).skip(1).forEach(s -> sb.append(" " + s));
                String vc = sb.toString().substring(1);
                if (event.getGuild().getVoiceChannelsByName(vc, true).size() > 0) {
                    SSSS.setVoiceKickChannel(vc, event.getGuild());
                    event.getTextChannel().sendMessage(Messages.success().setDescription("Successfully set voice kick channel to `" + vc + "`").build()).queue();
                    return;
                } else {
                    event.getTextChannel().sendMessage(Messages.error().setDescription("Please enter a valid voice channel existing on this guild.").build()).queue();
                    return;
                }

            } else {
                event.getTextChannel().sendMessage(Messages.error().setDescription("Please enter a valid voice channel existing on this guild.").build()).queue();
                return;
            }
        }

        if (event.getMessage().getMentionedUsers().size() > 0) {

            int timeout;
            try {
                StringBuilder sb = new StringBuilder();
                Arrays.stream(args).forEach(s -> sb.append(" " + s));
                timeout = Integer.parseInt(sb.toString().substring(1).replace("@" + event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0)).getEffectiveName(), "").replaceAll(" ", ""));
            } catch (Exception e) {
                timeout = 0;
            }

            List<VoiceChannel> vc = event.getGuild().getVoiceChannelsByName(SSSS.getVoiceKickChannel(event.getGuild()), true);

            if (vc.size() == 0) {
                event.getTextChannel().sendMessage(Messages.error().setDescription("There is no voice channel set or it is no more existent on this guild.\nPlease set a valid voice channel with `-vkick channel <channel name>`.").build()).queue();
                return;
            }

            Member victim = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
            VoiceChannel current = event.getMember().getVoiceState().getChannel();

            if (current.equals(vc.get(0))){
                event.getTextChannel().sendMessage(Messages.error().setDescription("You cant kick a user when he is in the designated kick channel").build()).queue();
                return;
            }

            event.getGuild().getController().moveVoiceMember(victim, vc.get(0)).queue();

            if (timeout > 0) {

                kicks.put(victim, current);

                event.getTextChannel().sendMessage(new EmbedBuilder()
                        .setColor(new Color(0xFF0036))
                        .setDescription(":boot:  " + event.getAuthor().getAsMention() + " (*" + event.getMember().getRoles().get(0).getName() + "*) kicked " + victim.getAsMention() +
                                " out of the voice channel `" + current.getName() + "` with a rejoin timeout of **" + timeout + " seconds**.")
                        .build()
                ).queue();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        kicks.remove(event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0)));
                        victim.getUser().openPrivateChannel().complete().sendMessage("Your voice kick timeout has expired. You can now rejoin the channel `" + current.getName() + "`.").queue();
                    }
                }, timeout * 1000);
            } else {
                event.getTextChannel().sendMessage(new EmbedBuilder()
                        .setColor(new Color(0xFF0036))
                        .setDescription(":boot:  " + event.getAuthor().getAsMention() + " (*" + event.getMember().getRoles().get(0).getName() + "*) kicked " + victim.getAsMention() +
                                " out of the voice channel `" + current.getName() + "`.")
                        .build()
                ).queue();
            }

        }


    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: \n" +
                "**-vkick <@user> (<timeout in seconds>)**  -  `Kick someone out of the voice channel (if timeout is entered, the kicked user cant rejoin the channel in timeout period)`\n" +
                "**-vkick channel <channel name>**  -  `Set the voice kick channel`";
    }

    @Override
    public String description() {
        return "Kick people out of your voice channel";
    }

    @Override
    public String commandType() {
        return STATIC.CMDTYPE.guildadmin;
    }

    @Override
    public Perm permission() {
        return Perm.MODERATOR;
    }
}
