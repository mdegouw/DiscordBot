package commands.administration;

import commands.Command;
import core.SSSS;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.STATICS;

/**
 * Created by Ringo Hoffmann on 20.02.2017.
 */

public class testCMD implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        System.out.println("LVL1: " + SSSS.getPERMROLES_1(event.getGuild()) + "\nLVL2: " + SSSS.getPERMROLES_2(event.getGuild()));
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
        return null;
    }

    @Override
    public String commandType() {
        return STATICS.CMDTYPE.administration;
    }
}
