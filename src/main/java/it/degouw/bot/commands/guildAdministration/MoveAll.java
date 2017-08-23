package it.degouw.bot.commands.guildAdministration;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.reference.CommandType;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.util.Messages;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;

public class MoveAll implements ICommand, IGuildCommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {

        if (!PermissionHandler.check(this.permission(), event)) { return false; }

        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        try {

            StringBuilder sb = new StringBuilder();
            Arrays.stream(args).forEach(s -> sb.append(s + " "));
            String vcName = sb.toString().substring(0, sb.toString().length() - 1);

            final VoiceChannel vc = event.getGuild().getVoiceChannels().stream()
                    .filter(voiceChannel -> voiceChannel.getName().toLowerCase().contains(vcName.toLowerCase()))
                    .findFirst().get();

            if (event.getMember().getVoiceState().inVoiceChannel()) {
                if (!event.getMember().getVoiceState().getChannel().equals(vc)) {

                    int membersInChannel = event.getMember().getVoiceState().getChannel().getMembers().size();
                    String VCfrom = event.getMember().getVoiceState().getChannel().getName();
                    String VCto = vc.getName();

                    event.getMember().getVoiceState().getChannel().getMembers()
                            .forEach(member -> event.getGuild().getController().moveVoiceMember(member, vc).queue());

                    event.getMessage().delete().queue();
                    Message msg = event.getTextChannel().sendMessage(new EmbedBuilder().setColor(new Color(0, 169, 255))
                            .setDescription("Moved **" + membersInChannel + "** members from `" + VCfrom + "` to `" + VCto + "`.")
                            .build()).complete();
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            msg.delete().queue();
                        }
                    }, 5000);

                } else {
                    event.getTextChannel().sendMessage(
                            Messages.error().setDescription("You don't need to move everyone in the same channel you are still in...").build()
                    ).queue();
                }
            } else
                event.getTextChannel().sendMessage(
                        Messages.error().setDescription("You need to be in a voice channel to use this command!").build()
                ).queue();

        } catch (NoSuchElementException e) {
            event.getTextChannel().sendMessage(
                    Messages.error().setDescription("Please enter a valid voice channel existing on this guild!").build()
            ).queue();
        } catch (Exception e) {
            event.getTextChannel().sendMessage(
                    Messages.error()
                            .addField("Error Message", e.getMessage(), false)
                            .build()
            ).queue();
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: -moveall <voicechannel (can also just be a part of a voice channels name)>";
    }

    @Override
    public String description() {
        return "Move everyone in your channel to another channel.";
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
