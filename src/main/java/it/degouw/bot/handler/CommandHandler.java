package it.degouw.bot.handler;


import it.degouw.bot.Main;
import it.degouw.bot.commands.IPrivateCommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.commands.etc.BotStats;
import it.degouw.bot.reference.STATIC;
import it.degouw.bot.util.BotUtils;
import it.degouw.bot.util.CommandParser;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class CommandHandler {

    public static void handleCommand(CommandParser.CommandContainer cmd) throws ParseException, IOException {

        if (Main.commands.containsKey(cmd.invoke)) {
            if (cmd.event.getChannelType() == ChannelType.TEXT){
                if (Main.commands.get(cmd.invoke) instanceof IGuildCommand) {
                    boolean safe = Main.commands.get(cmd.invoke).called(cmd.args, cmd.event);
                    if (safe){
                        Main.commands.get(cmd.invoke).action(cmd.args, cmd.event);
                        Main.commands.get(cmd.invoke).executed(safe, cmd.event);
                        if (STATIC.commandConsoleOutout)
                            System.out.println(BotUtils.getCurrentSystemTime() + " [INFO] [Commands]: Command `" + cmd.event.getMessage().getContent() + "` was executed by `" + cmd.event.getAuthor() + "` (" + cmd.event.getGuild().getName() + ")!");
                        ArrayList<String> list = new ArrayList<String>();
                        list.add(cmd.event.getGuild().getId());
                        list.add(BotUtils.getCurrentSystemTime());
                        list.add(cmd.event.getMember().getEffectiveName());
                        list.add(cmd.event.getMessage().getContent());
                        STATIC.cmdLog.add(list);
                        addGuildToLogFile(cmd.event);
                        BotStats.commandsExecuted++;
                    } else {
                        Main.commands.get(cmd.invoke).executed(safe, cmd.event);
                    }
                }
            } else if (cmd.event.getChannelType() == ChannelType.PRIVATE) {
                if (Main.commands.get(cmd.invoke) instanceof IPrivateCommand) {
                    boolean safe = Main.commands.get(cmd.invoke).called(cmd.args, cmd.event);
                    if (safe){
                        Main.commands.get(cmd.invoke).action(cmd.args, cmd.event);
                        Main.commands.get(cmd.invoke).executed(safe, cmd.event);
                        if (STATIC.commandConsoleOutout)
                            System.out.println(BotUtils.getCurrentSystemTime() + " [INFO] [Commands]: Command `" + cmd.event.getMessage().getContent() + "` was executed by `" + cmd.event.getAuthor() + "` (" + cmd.event.getChannel() + ")!");
                        ArrayList<String> list = new ArrayList<String>();
                        list.add(cmd.event.getChannel().getId());
                        list.add(BotUtils.getCurrentSystemTime());
                        list.add(cmd.event.getAuthor().getName());
                        list.add(cmd.event.getMessage().getContent());
                        STATIC.cmdLog.add(list);
                        addPrivateToLogFile(cmd.event);
                        BotStats.commandsExecuted++;
                    } else {
                        Main.commands.get(cmd.invoke).executed(safe, cmd.event);
                    }
                }
            }
        }
    }

    private static void addGuildToLogFile(MessageReceivedEvent event) throws IOException {

        File logFile = new File("CMDLOG.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true));

        if (!logFile.exists())
            logFile.createNewFile();

        bw.write(String.format("%s [%s (%s)] [%s (%s)] '%s'\n",
                BotUtils.getCurrentSystemTime(),
                event.getGuild().getName(),
                event.getGuild().getId(),
                event.getAuthor().getName(),
                event.getAuthor().getId(),
                event.getMessage().getContent()));
        bw.close();
    }

    private static void addPrivateToLogFile(MessageReceivedEvent event) throws IOException {

        File logFile = new File("CMDLOG.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true));

        if (!logFile.exists())
            logFile.createNewFile();

        bw.write(String.format("%s [%s (%s)] [%s (%s)] '%s'\n",
                BotUtils.getCurrentSystemTime(),
                "Private Channel",
                event.getChannel().getId(),
                event.getAuthor().getName(),
                event.getAuthor().getId(),
                event.getMessage().getContent()));
        bw.close();
    }

}
