package it.degouw.bot.listeners;

import it.degouw.bot.commands.chat.Cat;
import it.degouw.bot.commands.settings.BotMessage;
import it.degouw.bot.permissions.SSSS;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class GuildJoinListener extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

        BotMessage.setSupplyingMessage(event.getJDA());

        if (event.getMember().getUser().isBot()) return;

        if (!SSSS.getServerJoinMessage(event.getGuild()).toLowerCase().equals("off")) {
            event.getGuild().getTextChannelsByName("general", true).get(0).sendMessage(
                    SSSS.getServerJoinMessage(event.getGuild()).replace("[USER]", event.getMember().getAsMention()).replace("[GUILD]", event.getGuild().getName())
            ).queue();
        }

        if (!SSSS.getAutoRole(event.getGuild()).equals("")) {

            event.getGuild().getController().addRolesToMember(event.getMember(), event.getGuild().getRolesByName(SSSS.getAutoRole(event.getGuild()), true)).queue();

            PrivateChannel pc = event.getMember().getUser().openPrivateChannel().complete();
            pc.sendMessage(
                    "**Hey,** " + event.getMember().getAsMention() + " **and welcome on the " + event.getGuild().getName() + " Discord server!**   :wave:\n\n" +
                            "You automatically got assigned the server role `" + SSSS.getAutoRole(event.getGuild()) + "` by me.\n\n" +
                            "Now, have a nice day and a lot of fun on the server! ;)"
            ).queue();

            try {
                pc.sendMessage("*And here a nice cat pic. ^^*\n" + Cat.getCat()).queue();
            } catch (Exception e) {
                pc.sendMessage("Ooops. I just wanted to attach a little nice cat pic, but the occurred an unexpected error. :(").queue();
            }
        }
    }

    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {

        BotMessage.setSupplyingMessage(event.getJDA());

        if (event.getMember().getUser().isBot()) return;

        if (!SSSS.getServerLeaveMessage(event.getGuild()).toLowerCase().equals("off")) {
            event.getGuild().getTextChannelsByName("general", true).get(0).sendMessage(
                    SSSS.getServerLeaveMessage(event.getGuild()).replace("[USER]", event.getMember().getAsMention()).replace("[GUILD]", event.getGuild().getName())
            ).queue();
        }

    }


}
