package it.degouw.bot.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BotUtils {

    public static String getCurrentSystemTime() {
        DateFormat dateFormat = new SimpleDateFormat("[dd.MM.yyyy - HH:mm:ss]");
        Date date = new Date();

        return dateFormat.format(date);
    }

}
