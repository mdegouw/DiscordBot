package it.degouw.bot.listeners;

import it.degouw.bot.commands.etc.Bug;
import it.degouw.bot.commands.etc.Bug2;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        //TODO: Setup Voteing
        //Vote3.handleReaction(event);

        try {
            Bug2.BugReport report = Bug2.reports.get(event.getMessageId().toString());
            if (report != null) {

                if (event.getUser().equals(report.getAuthor())) {
                    report.sendConfMessage();
                    report.sendReport(report.getChannel());
                    report.sendReport(event.getJDA().getTextChannelById("349661947454816256"));
                    report.getTimer().cancel();
                    Bug2.reports.remove(event.getMessageId());
                }
            }
        } catch (Exception e) {}


        try {
            if (event.getMessageId().equals(Bug.MESSAGE.getId()) && event.getUser().equals(Bug.AUTHOR)) {
                Bug.CHANNEL.sendMessage(Bug.FINAL_MESSAGE.build()).queue();
                Bug.sendConfMessage();
                MessageChannel bugReport = event.getJDA().getTextChannelById("349661947454816256");

                bugReport.sendMessage(Bug.FINAL_MESSAGE.build()).queue();
                bugReport.sendMessage(event.getMessageId()).queue();
                bugReport.sendMessage(Bug.MESSAGE.getId()).queue();


                Bug.AUTHOR = null;
                Bug.MESSAGE = null;
                Bug.FINAL_MESSAGE = null;
                Bug.CHANNEL = null;
                Bug.TIMER.cancel();
                Bug.TIMER = null;
            }
        } catch (Exception e) {}
    }
}
