package it.degouw.bot.listeners;

import it.degouw.bot.commands.guildAdministration.VoiceKick;
import it.degouw.bot.permissions.SSSS;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class VoiceKickListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {

        if (VoiceKick.kicks.containsKey(event.getMember()) && event.getChannelJoined().equals(VoiceKick.kicks.get(event.getMember()))) {
            event.getGuild().getController().moveVoiceMember(event.getMember(), event.getGuild().getVoiceChannelsByName(SSSS.getVoiceKickChannel(event.getGuild()), true).get(0)).queue();
            event.getMember().getUser().openPrivateChannel().complete().sendMessage(
                    "Sorry, but you are blocked for this channel because of a voice kick!"
            ).complete();
        }

    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {

        if (VoiceKick.kicks.containsKey(event.getMember()) && event.getChannelJoined().equals(VoiceKick.kicks.get(event.getMember()))) {
            event.getGuild().getController().moveVoiceMember(event.getMember(), event.getChannelLeft()).queue();
            event.getMember().getUser().openPrivateChannel().complete().sendMessage(
                    "Sorry, but you are blocked for this channel because of a voice kick!"
            ).complete();
        }

    }

}
