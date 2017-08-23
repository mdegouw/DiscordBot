package it.degouw.bot.listeners;

import it.degouw.bot.Main;
import it.degouw.bot.commands.essentials.Ping;
import it.degouw.bot.commands.etc.BotStats;
import it.degouw.bot.commands.guildAdministration.BlackList;
import it.degouw.bot.handler.CommandHandler;
import it.degouw.bot.permissions.SSSS;
import it.degouw.bot.reference.STATIC;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        BotStats.messagesProcessed++;
        Ping.setInputTime(new Date().getTime());

        if (event.getChannelType().equals(ChannelType.TEXT)) {
            if (event.getMessage().getContent().startsWith(SSSS.getPrefix(event.getGuild())) && event.getAuthor().getName() != event.getJDA().getSelfUser().getName()) {
                if (!BlackList.check(event.getAuthor(), event.getGuild())) {
                    try {
                        CommandHandler.handleCommand(Main.parser.parse(event.getMessage().getContent(), event));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (event.getChannelType().equals(ChannelType.PRIVATE)) {

            if (event.getMessage().getContent().equalsIgnoreCase("-disable")) {

                try {
                    new File("SERVER_SETTINGS/no_update_info").createNewFile();
                    event.getChannel().sendMessage(new EmbedBuilder()
                            .setColor(Color.red)
                            .setDescription("You disabled update notifications.\n" +
                                    "Now, you wont get automatically notified if there are new versions of the bot available.")
                            .setFooter("Re-enable this function with enetring '-enable'.", null)
                            .build()).queue();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

            if (event.getMessage().getContent().equalsIgnoreCase("-enable")) {


                File f = new File("SERVER_SETTINGS/no_update_info");
                if (f.exists())
                    f.delete();

                event.getChannel().sendMessage(new EmbedBuilder()
                        .setColor(Color.green)
                        .setDescription("You re-enabled update notification.")
                        .setFooter("Disable this function with enetring '-disable'.", null)
                        .build()).queue();

                return;
            }

            if (event.getMessage().getContent().startsWith(STATIC.PRIVATE_PREFIX) && event.getAuthor().getName() != event.getJDA().getSelfUser().getName()) {
                try {
                    CommandHandler.handleCommand(Main.parser.parse(event.getMessage().getContent(), event));
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (!event.getMessage().getContent().startsWith(STATIC.PRIVATE_PREFIX) && event.getAuthor().getName() != event.getJDA().getSelfUser().getName()) {
                event.getPrivateChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setTitle("Error").setDescription(
                        String.format(":worried:     **Unknown Command**\n\nIn private chat use the following prefix: `%s`\n\nExample: `%shelp`",
                                STATIC.PRIVATE_PREFIX, STATIC.PRIVATE_PREFIX)
                ).build()).queue();
            }
        }
    }
}
