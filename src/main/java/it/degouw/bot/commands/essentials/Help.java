package it.degouw.bot.commands.essentials;

import it.degouw.bot.Main;
import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.commands.IPrivateCommand;
import it.degouw.bot.permissions.SSSS;
import it.degouw.bot.reference.CommandType;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.reference.STATIC;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Help implements ICommand, IGuildCommand, IPrivateCommand {

    private String getPermEmoji(Perm perm) {
        switch (perm) {
            case BOT_OWNER: return ":no_entry:";
            case SERVER_OWNER: return ":o:";
            case OWNER: return ":small_red_triangle_down:";
            case ADMIN: return ":a:";
            case MODERATOR: return ":m:";
            case MEMBER: return ":white_check_mark:";
            default: return ":white_small_square:";
        }
    }

    EmbedBuilder eb = new EmbedBuilder();


    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (args.length > 0) {
            if (Main.commands.containsKey(args[0]))
                if (Main.commands.get(args[0]).help() != null)
                    event.getChannel().sendMessage(
                            eb.setColor(new Color(22, 138, 233)).setDescription(Main.commands.get(args[0]).help()).build()
                    ).queue();
                else
                    event.getChannel().sendMessage(
                            eb.setColor(Color.red).setDescription(":warning:  There are currently no information for the command  *" + args[0] + "* !").build()
                    ).queue();
            else
                event.getChannel().sendMessage(
                        eb.setColor(Color.red).setDescription(":warning:  The command list does not contains information for the command *" + args[0] + "* !").build()
                ).queue();
            return;
        }

        if (event.getChannelType().equals(ChannelType.TEXT))
            event.getMessage().delete().queue();

        Map<String, String> cmds = new TreeMap<>();
        Main.commands.forEach((s, command) -> cmds.put(s, command.description()));

        StringBuilder ciams = new StringBuilder();

        String[] ignorers = {"bpoll", "test", "r", "c", "bj", "ttt", "userinfo", "dev", "nudge", "poll", "moveall", "purge", "info", "suggestion", "r6", };
        Arrays.stream(ignorers).forEach(s -> cmds.remove(s));


        try {

            PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
            pc.sendMessage(":clipboard:  __**COMMAD LIST**__  :clipboard: \n\n" +
                    "If you want a full list of commands with description, please take a look there:\n" +
                    ":point_right:   **http://zekrosbot.zekro.de**\n\n" +
                    "***Legend:***\n" +
                    "  :white_small_square:  -  Usable for everyone\n" +
                    "  :white_check_mark:  -  Usable for Members: `" + Arrays.toString(SSSS.getMemberRoles(event.getGuild())).replace("[", "").replace("]", "") + "`\n" +
                    "  :m:  -  Usable for Moderators: `" + Arrays.toString(SSSS.getModeratorRoles(event.getGuild())).replace("[", "").replace("]", "") + "`\n" +
                    "  :a:  -  Usable for Admins `" + Arrays.toString(SSSS.getAdminRoles(event.getGuild())).replace("[", "").replace("]", "") + "`\n" +
                    "  :small_red_triangle_down:  -  Usable for Co-Owners: `" + Arrays.toString(SSSS.getOwnerRoles(event.getGuild())).replace("[", "").replace("]", "") + "`\n" +
                    "  :o:  -  Only for owner of the server\n" +
                    "  :no_entry:  -  Only for BotOwner\n" +
                    "\n\n___").queue();

            ciams.delete(0, ciams.length());
            ciams.append("**" + CommandType.ADMINISTRATION + "**\n");
            cmds.keySet().stream()
                    .filter(s -> Main.commands.get(s).commandType().equals(CommandType.ADMINISTRATION))
                    .forEach(s1 -> ciams.append(
                            getPermEmoji(Main.commands.get(s1).permission()) + "  **" + s1 + "**   -   `" + cmds.get(s1) + "`\n"
                    ));
            pc.sendMessage(new EmbedBuilder().setColor(new Color(134, 255, 0)).setDescription(ciams.toString()).build()).queue();

            ciams.delete(0, ciams.length());
            ciams.append("**" + CommandType.CHATUTILS + "**\n");
            cmds.keySet().stream()
                    .filter(s -> Main.commands.get(s).commandType().equals(CommandType.CHATUTILS))
                    .forEach(s1 -> ciams.append(
                            getPermEmoji(Main.commands.get(s1).permission()) + "  **" + s1 + "**   -   `" + cmds.get(s1) + "`\n"
                    ));
            pc.sendMessage(new EmbedBuilder().setColor(new Color(255, 97, 0)).setDescription(ciams.toString()).build()).queue();

            ciams.delete(0, ciams.length());
            ciams.append("**" + CommandType.ESSENTIALS + "**\n");
            cmds.keySet().stream()
                    .filter(s -> Main.commands.get(s).commandType().equals(CommandType.ESSENTIALS))
                    .forEach(s1 -> ciams.append(
                            getPermEmoji(Main.commands.get(s1).permission()) + "  **" + s1 + "**   -   `" + cmds.get(s1) + "`\n"
                    ));
            pc.sendMessage(new EmbedBuilder().setColor(new Color(255, 0, 213)).setDescription(ciams.toString()).build()).queue();

            ciams.delete(0, ciams.length());
            ciams.append("**" + CommandType.ETC + "**\n");
            cmds.keySet().stream()
                    .filter(s -> Main.commands.get(s).commandType().equals(CommandType.ETC))
                    .forEach(s1 -> ciams.append(
                            getPermEmoji(Main.commands.get(s1).permission()) + "  **" + s1 + "**   -   `" + cmds.get(s1) + "`\n"
                    ));
            pc.sendMessage(new EmbedBuilder().setColor(new Color(39, 0, 255)).setDescription(ciams.toString()).build()).queue();

            ciams.delete(0, ciams.length());
            ciams.append("**" + CommandType.GUILDADMIN + "**\n");
            cmds.keySet().stream()
                    .filter(s -> Main.commands.get(s).commandType().equals(CommandType.GUILDADMIN))
                    .forEach(s1 -> ciams.append(
                            getPermEmoji(Main.commands.get(s1).permission()) + "  **" + s1 + "**   -   `" + cmds.get(s1) + "`\n"
                    ));
            pc.sendMessage(new EmbedBuilder().setColor(new Color(0, 233, 255)).setDescription(ciams.toString()).build()).queue();

            ciams.delete(0, ciams.length());
            ciams.append("**" + CommandType.MUSIC + "**\n");
            cmds.keySet().stream()
                    .filter(s -> Main.commands.get(s).commandType().equals(CommandType.MUSIC))
                    .forEach(s1 -> ciams.append(
                            getPermEmoji(Main.commands.get(s1).permission()) + "  **" + s1 + "**   -   `" + cmds.get(s1) + "`\n"
                    ));
            pc.sendMessage(new EmbedBuilder().setColor(new Color(0, 255, 126)).setDescription(ciams.toString()).build()).queue();

            ciams.delete(0, ciams.length());
            ciams.append("**" + CommandType.SETTINGS + "**\n");
            cmds.keySet().stream()
                    .filter(s -> Main.commands.get(s).commandType().equals(CommandType.SETTINGS))
                    .forEach(s1 -> ciams.append(
                            getPermEmoji(Main.commands.get(s1).permission()) + "  **" + s1 + "**   -   `" + cmds.get(s1) + "`\n"
                    ));
            pc.sendMessage(new EmbedBuilder().setColor(new Color(255, 233, 0)).setDescription(ciams.toString()).build()).queue();

        } catch (Exception e) {
            e.printStackTrace();
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
        return "\"Ich brauch keine Hilfe...\" :D";
    }

    @Override
    public CommandType commandType() {
        return CommandType.ESSENTIALS;
    }

    @Override
    public Perm permission() {
        return Perm.DEFAULT;
    }
}
