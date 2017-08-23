package it.degouw.bot.commands.guildAdministration;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.reference.CommandType;
import it.degouw.bot.reference.Perm;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.http.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Report implements ICommand, IGuildCommand {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {

        if (!PermissionHandler.check(Perm.DEFAULT, event)) { return false; }

        if (args.length < 2) {
            event.getChannel().sendMessage(help()).queue();
            return false;
        }

        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        List<Member> admins = new ArrayList<>();
        User traitor = event.getMessage().getMentionedUsers().get(0);
        Member author = event.getMember();

        StringBuilder argsString = new StringBuilder();
        Arrays.stream(args).forEach(s -> argsString.append(" " + s));
        String reason = argsString.toString().replace("@" + event.getGuild().getMember(traitor).getEffectiveName(), "").substring(1);

        event.getGuild().getMembers().stream()
                .filter(m -> PermissionHandler.getPerm(m).getNumVal() > Perm.ADMIN.getNumVal())
                .forEach(m -> admins.add(m));

        StringBuilder sendTo = new StringBuilder();
        admins.forEach(m -> sendTo.append(", " + m.getUser().getAsMention()));

        admins.forEach(m -> m.getUser().openPrivateChannel().complete().sendMessage(
                new EmbedBuilder()
                        .setAuthor(m.getEffectiveName() + " submitted a report.", null, m.getUser().getAvatarUrl())
                        .addField("Author", author.getEffectiveName() + " (" + author.getAsMention() + ")", true)
                        .addField("Reported Member", traitor.getName() + " (" + traitor.getAsMention() + ")", true)
                        .addField("Reason", reason, false)
                        .build()
        ).queue());

        author.getUser().openPrivateChannel().complete().sendMessage(
                new EmbedBuilder()
                        .setDescription("Thanks for your report submit.\nYour report got send by direct message to " + sendTo.toString() + ".")
                        .build()
        ).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return  "USAGE:" +
                "**report <@mention> <reason>**  -  `Report someone on the discord by owner and admins`";
    }

    @Override
    public String description() {
        return "Report users on the discord.";
    }

    @Override
    public CommandType commandType() {
        return CommandType.GUILDADMIN;
    }

    @Override
    public Perm permission() {
        return Perm.DEFAULT;
    }
}
