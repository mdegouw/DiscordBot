package it.degouw.bot.listeners;

import it.degouw.bot.commands.etc.Bug;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        //TODO: Setup Voteing
        //Vote3.handleReaction(event);

        try {
            Bug.BugReport report = Bug.reports.get(event.getMessageId());
            if (report != null) {

                if (event.getUser().equals(report.getAuthor())) {
                    report.sendConfMessage();
                    report.sendReport(report.getChannel());
                    report.getTimer().cancel();

                    // TEST: Push report to a channel on my Guild
                    report.sendReport(event.getJDA().getTextChannelById("349661947454816256"));
                    Bug.reports.remove(event.getMessageId());
                }
            }
        } catch (Exception e) {}
    }
}
