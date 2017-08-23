package it.degouw.bot.listeners;

import it.degouw.bot.reference.STATIC;
import net.dv8tion.jda.core.events.ReconnectedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReconnectListener extends ListenerAdapter {

    @Override
    public void onReconnect(ReconnectedEvent event) {

        System.out.println("[INFO] RECONNECT");

        STATIC.reconnectCount++;

    }
}
