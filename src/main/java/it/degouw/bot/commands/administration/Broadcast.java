package it.degouw.bot.commands.administration;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.reference.CommandType;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.reference.STATIC;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Broadcast implements ICommand, IGuildCommand {

        @Override
        public boolean called(String[] args, MessageReceivedEvent event) {

            if (!PermissionHandler.check(this.permission(), event)) { return false; }

            if (args.length < 1) {
                event.getChannel().sendMessage(this.help()).queue();
                return false;
            }
            return true;
        }

        @Override
        public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

            //TODO: Fix broadcast

            String argsString = Arrays.stream(args).collect(Collectors.joining(" "));

            List<Guild> guilds = event.getJDA().getGuilds();

            guilds.stream().forEach(guild -> guild.getPublicChannel().sendMessage(
                    new EmbedBuilder()
                            .setColor(Color.CYAN)
                            .setAuthor("\uD83D\uDCE2  Broadcast message by Spectre", null, event.getAuthor().getAvatarUrl())
                            .setDescription(argsString)
                            .build()
                    ).queue()
            );


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
            return "Send a message to all servers general chats";
        }

        @Override
        public CommandType commandType() {
            return CommandType.ESSENTIALS;
        }

        @Override
        public Perm permission() {
            return Perm.BOT_OWNER;
        }
}
