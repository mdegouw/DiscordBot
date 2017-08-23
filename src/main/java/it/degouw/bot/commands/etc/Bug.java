package it.degouw.bot.commands.etc;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.commands.IPrivateCommand;
import it.degouw.bot.reference.CommandType;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.reference.STATIC;
import it.degouw.bot.util.Messages;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Bug implements ICommand, IGuildCommand, IPrivateCommand {

    public static HashMap<String, BugReport> reports = new HashMap<String, BugReport>();

    public static User AUTHOR;
    public static Message MESSAGE;
    public static EmbedBuilder FINAL_MESSAGE;
    public static MessageChannel CHANNEL;
    public static Timer TIMER = new Timer();


    public static void sendConfMessage() {
        CHANNEL.sendMessage(Messages.success().setDescription("Submit sucesfully send!").build()).queue();
    }


    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {

        if (args.length < 1) {
            event.getChannel().sendMessage(new EmbedBuilder().setDescription(
                    "You can submit a bug report or suggestion right with the command using like this:\n" +
                            "```-bug <message>```\n\n" +
                            "You can use following modifiers to embed better details:\n" +
                            "```-bug <title>\n" +
                            "<bug/gussestion>\n" +
                            "<message>```\n\n" +
                            "Otherwise you can also just use the public **[google sheet](https://s.zekro.de/botsubs)** to submit a bug or suggestion."
            ).build()).queue();
            return false;
        }

        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        try {

        } catch (Exception e) {

            if (args.length < 1) {
                event.getChannel().sendMessage(new EmbedBuilder().setDescription(
                        "If you want to submit a bug or a suggestion, please use the public **[google sheet](https://s.zekro.de/botsubs)**."
                ).build()).queue();
                return;
            }
            event.getChannel().sendMessage(Messages.error().setDescription(
                    "Sorry, the expandet version of this command is only available on the public version of the bot!\n\n" +
                            "If you want to submit a bug or a suggestion, please use the public **[google sheet](https://s.zekro.de/botsubs)**."
            ).build()).queue();
            return;
        }

        if (!(args.length < 1)) {

            String title = "Bug report / Suggestion";
            String type = "unspecified";
            String message = "";

            String argsString = Arrays.stream(args).collect(Collectors.joining(" "));

            title = argsString.split("\n").length > 3 ? argsString.split("\n")[0] : title;
            type = argsString.split("\n").length > 3 ? argsString.split("\n")[1] : type;
            message = argsString.split("\n").length > 3 ? Arrays.stream(argsString.split("\n")).skip(2).collect(Collectors.joining("\n")) : argsString;


            BugReport report = new BugReport(title, type, message, event);

            reports.put(report.getPreviewId(), report);

        }


    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String description() {
        return "Send a bug report or a suggestion";
    }

    @Override
    public CommandType commandType() {
        return CommandType.ETC;
    }

    @Override
    public Perm permission() {
        return Perm.DEFAULT;
    }


    public static class BugReport {
        private String title = "Bug report / Suggestion";
        private String type = "unspecified";
        private String message = "";

        private User AUTHOR;
        private Message PREVIEW_MESSAGE;
        private EmbedBuilder FINAL_MESSAGE;
        private MessageChannel CHANNEL;
        private Timer TIMER = new Timer();
        private MessageReceivedEvent event;


        public BugReport(String title, String type, String message, MessageReceivedEvent event) {
            this.title = title;
            this.type = type;
            this.message = message;
            this.event = event;

            this.CHANNEL = event.getChannel();

            this.PREVIEW_MESSAGE = CHANNEL.sendMessage(new EmbedBuilder()
                    .setTitle("MESSAGE PREVIEW", null)
                    .addField("Title", title, true)
                    .addField("Type", type, true)
                    .addField("Message", message, false)
                    .setFooter("Click on the reaction \uD83D\uDC4D to send the message. Else just dont klick ;)", null)
                    .build()
            ).complete();
            this.PREVIEW_MESSAGE.addReaction("\uD83D\uDC4D").queue();
            this.AUTHOR = event.getMessage().getAuthor();

            this.FINAL_MESSAGE = new EmbedBuilder()
                    .addField("Message ID", event.getMessage().getId(), false)
                    .addField("Author", event.getAuthor().getName(), false)
                    .addField("Title", title, true)
                    .addField("Type", type, true)
                    .addField("Message", message, false);

            this.TIMER = new Timer();
            this.TIMER.schedule(new TimerTask() {
                @Override
                public void run() {
                    AUTHOR = null;
                    MESSAGE = null;
                    FINAL_MESSAGE = null;
                    CHANNEL = null;
                    TIMER = null;
                    CHANNEL.sendMessage(new EmbedBuilder().setDescription("Confirmation time expired.").build()).queue();
                    reports.remove(event.getMessageId());
                }
            }, 20000);

        }

        public void sendConfMessage() {
            this.CHANNEL.sendMessage(Messages.success().setDescription("Submit sucesfully send!").build()).queue();
        }

        public void sendReport(MessageChannel channel) {
            channel.sendMessage(this.FINAL_MESSAGE.build()).queue();
        }


        public Message getPreviewMessage() {
            return PREVIEW_MESSAGE;
        }

        public User getAuthor() {
            return AUTHOR;
        }

        public Timer getTimer() {
            return TIMER;
        }

        public EmbedBuilder getFinalMessage() {
            return FINAL_MESSAGE;
        }

        public MessageChannel getChannel() {
            return CHANNEL;
        }

        public MessageReceivedEvent getEvent() {
            return event;
        }

        public String getPreviewId() {
            return this.PREVIEW_MESSAGE.getId();
        }

    }


}
