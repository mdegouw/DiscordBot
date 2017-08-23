package it.degouw.bot.commands.administration;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.commands.IPrivateCommand;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.permissions.SSSS;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.reference.STATIC;
import it.degouw.bot.util.Messages;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;

public class Guilds implements ICommand, IGuildCommand, IPrivateCommand{

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {

        if (!PermissionHandler.isBotOwner(event.getAuthor(), event.getChannel())) { return false; }

        if (args.length < 1){
            event.getChannel().sendMessage(this.help());
            return false;
        }

        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        if ((args.length > 1 ? args[0] : "").equalsIgnoreCase("info")) {
            Guild g;
            try {
                g = event.getJDA().getGuildById(args[1]);
            } catch (Exception e) {
                event.getChannel().sendMessage(Messages.error().setDescription("There is no guild with the entered ID.").build()).queue();
                return;
            }

            event.getChannel().sendMessage(new EmbedBuilder()
                    .setTitle("Guild information for guild " + g.getName(), null)
                    .setThumbnail(g.getIconUrl())
                    .setDescription("\n\n")
                    .addField("Name", g.getName(), false)
                    .addField("ID", g.getId(), false)
                    .addField("Owner", g.getOwner().getAsMention(), false)
                    .addField("Members", g.getMembers().size() + " (Online: " + g.getMembers().stream().filter(m -> !m.getOnlineStatus().equals(OnlineStatus.OFFLINE)).count() + ")", false)
                    .addField("Prefix on server", "`" + SSSS.getPrefix(g) + "`", false)
                    .build()
            ).queue();
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("**Running on `" + event.getJDA().getGuilds().size() + "` guilds.**\n\n");
        event.getJDA().getGuilds().stream()
                .sorted(Comparator.comparingInt(s -> s.getMembers().size()))
                .forEach(g -> sb.append(
                        "*[" + g.getMembers().size() + "]*  -  **" + g.getName() + "**  -  `" + g.getId() + "`\n"
                ));

        if (sb.toString().length() > 2000)
            event.getChannel().sendMessage("**Guilds:**\n\n" + sb.toString()).queue();
        else
            event.getChannel().sendMessage(new EmbedBuilder().setTitle("Guilds", null).setDescription(sb.toString()).build()).queue();

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
        return "Get list of guilds bot is running on";
    }

    @Override
    public String commandType() {
        return STATIC.CMDTYPE.administration;
    }

    @Override
    public Perm permission() {
        return Perm.BOT_OWNER;
    }
}
