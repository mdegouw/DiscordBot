package it.degouw.bot.commands.guildAdministration;

import it.degouw.bot.permissions.SSSS;
import it.degouw.bot.util.Messages;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;

import java.util.List;

public class BlackList {

    public static boolean check(User user, Guild guild) {
        List<String> blackList = SSSS.getBlacklist(guild);
        if (blackList.contains(user.getId())) {
            user.openPrivateChannel().complete().sendMessage(Messages.error().setDescription("Sorry but you are currently not allowed to use this bot's commands!\nMessage a support or the server owner to remove you from blacklist.").build()).queue();
            return true;
        }
        return false;
    }

}
