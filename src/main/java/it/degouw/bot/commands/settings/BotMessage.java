package it.degouw.bot.commands.settings;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IPrivateCommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.reference.CommandType;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.reference.STATIC;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BotMessage implements ICommand, IGuildCommand, IPrivateCommand {

    private static boolean custom = false;
    private static int members = 0;

    private static void count() {
        members++;
    }

    public static void setSupplyingMessage(JDA jda) {

        if (!custom) {
            jda.getGuilds().forEach(g -> g.getMembers().forEach(m -> count()));
            jda.getPresence().setGame(Game.of("Supplying " + members + " users" + " | -help | v." + STATIC.VERSION));
            members = 0;
        }
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if (!PermissionHandler.check(this.permission(), event)) return false;
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        if (args.length < 1) {
            custom = false;
            setSupplyingMessage(event.getJDA());
            event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.ORANGE).setDescription("Successfully reset bot message to the default!").build()).queue();
            return;
        }

        custom = true;

        String messageString = Arrays.stream(args).collect(Collectors.joining(" ")).substring(0);
        event.getJDA().getPresence().setGame(Game.of(messageString + " | -help | v." + STATIC.VERSION));
        event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.GREEN).setDescription("Successfully set bot message to `" + messageString + "`!").build()).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: -botmsg <message>";
    }

    @Override
    public String description() {
        return "Set the \"Playing ...\" message of the bot.";
    }

    @Override
    public CommandType commandType() {
        return CommandType.SETTINGS;
    }

    @Override
    public Perm permission() {
        return Perm.BOT_OWNER;
    }



}
