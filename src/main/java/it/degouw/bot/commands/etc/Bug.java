package it.degouw.bot.commands.etc;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.commands.IPrivateCommand;
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
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class Bug implements ICommand, IGuildCommand, IPrivateCommand {

    public static User AUTHOR;
    public static Message MESSAGE;
    public static EmbedBuilder FINAL_MESSAGE;
    public static MessageChannel CHANNEL;
    public static Timer TIMER = new Timer();


    public static void sendConfMessage() {
        MESSAGE.getChannel().sendMessage(Messages.success().setDescription("Submit sucesfully send!").build()).queue();
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

            CHANNEL = event.getChannel();

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

            MESSAGE = event.getChannel().sendMessage(new EmbedBuilder()
                    .setTitle("MESSAGE PREVIEW", null)
                    .addField("Title", title, true)
                    .addField("Type", type, true)
                    .addField("Message", message, false)
                    .setFooter("Click on the reaction \uD83D\uDC4D to send the message. Else just dont klick ;)", null)
                    .build()
            ).complete();
            MESSAGE.addReaction("\uD83D\uDC4D").queue();
            AUTHOR = event.getMessage().getAuthor();

            FINAL_MESSAGE = new EmbedBuilder()
                    .addField("Message ID", event.getMessage().getId(), false)
                    .addField("Author", event.getAuthor().getName(), false)
                    .addField("Title", title, true)
                    .addField("Type", type, true)
                    .addField("Message", message, false);

            TIMER = new Timer();
            TIMER.schedule(new TimerTask() {
                @Override
                public void run() {
                    AUTHOR = null;
                    MESSAGE = null;
                    FINAL_MESSAGE = null;
                    CHANNEL = null;
                    TIMER = null;
                    event.getChannel().sendMessage(new EmbedBuilder().setDescription("Confirmation time expired.").build()).queue();
                }
            }, 20000);

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
    public String commandType() {
        return STATIC.CMDTYPE.etc;
    }

    @Override
    public Perm permission() {
        return Perm.DEFAULT;
    }
}
