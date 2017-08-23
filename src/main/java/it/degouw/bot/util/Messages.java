package it.degouw.bot.util;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;

public class Messages {

    public static final EmbedBuilder success() { return new EmbedBuilder().setColor(Color.green); }

    public static final EmbedBuilder error() { return new EmbedBuilder().setColor(Color.red); }


}
