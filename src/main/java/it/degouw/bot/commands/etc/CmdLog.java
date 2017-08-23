package it.degouw.bot.commands.etc;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.commands.IPrivateCommand;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.reference.CommandType;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.reference.STATIC;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CmdLog implements ICommand, IGuildCommand, IPrivateCommand {

    private String saveCut(String input) {
        System.out.println(input);
        if (input.length() > 1900)
            return input.substring(0, 1900);
        return input;
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {

        if (!PermissionHandler.check(this.permission(), event)) { return false; }

        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        if (args.length < 1) {

            StringBuilder sb = new StringBuilder();

            List<ArrayList<String>> loglist = STATIC.cmdLog;

            if (loglist.size() > 50)
                loglist = loglist.subList(loglist.size() - 50, loglist.size());

            loglist.stream()
                    .filter(strings -> strings.get(0).equals(event.getGuild().getId()))
                    .forEach(s -> sb.append(
                            "`" + s.get(1) + "`  **[" + s.get(2) + "]**  \"" + s.get(3) + "\"\n"
                    ));

            event.getTextChannel().sendMessage(
                    "**Last 50 Commands:**\n\n" + saveCut(sb.toString())
            ).queue();

        } else if (args[0].toLowerCase().equals("all")) {

            StringBuilder sb = new StringBuilder();

            List<ArrayList<String>> loglist = STATIC.cmdLog;

            if (loglist.size() > 50)
                loglist = loglist.subList(loglist.size() - 50, loglist.size());

            loglist.stream()
                    .forEach(s -> sb.append(
                            "`" + s.get(1) + "`  **[" + s.get(2) + "]**  \"" + s.get(3) + "\"\n"
                    ));

            event.getTextChannel().sendMessage(
                    "**Last 50 Commands (all guilds):** \n\n" + saveCut(sb.toString())
            ).queue();
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return  "USAGE:\n" +
                "**cmdlog**  -  `Show the last 50 commands executed on the guild`\n" +
                "**cmdlog all**  -  `Show the last 50 commands executed on every guild`";
    }

    @Override
    public String description() {
        return "Show the last 50 commands executed on the guild / on all guilds.";
    }

    @Override
    public CommandType commandType() {
        return CommandType.ETC;
    }

    @Override
    public Perm permission() {
        return Perm.DEFAULT;
    }
}
