package it.degouw.bot.commands.chat;

import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.reference.STATIC;
import it.degouw.bot.util.Messages;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class Cat implements ICommand, IGuildCommand {
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    Timer spamTimer = new Timer();

    public void action(String[] args, MessageReceivedEvent event) {

        switch (args.length > 0 ? args[0].toLowerCase() : "") {

            case "spam":

                int delay;
                try {
                    delay = Integer.parseInt(args[1]);
                    if (delay < 5) {
                        delay = 5;
                        event.getTextChannel().sendMessage(Messages.error().setDescription("The minumum time period is 5 seconds because of spam protection. ;)").build()).queue();
                    }
                } catch (Exception e) {
                    delay = 10;
                }

                AtomicInteger counter = new AtomicInteger();
                spamTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        counter.getAndAdd(1);
                        event.getTextChannel().sendMessage("Cat number: `[" + counter.get() + "]`\n" + getCat()).queue();
                        if (counter.get() > 99) {
                            spamTimer.cancel();
                            spamTimer = new Timer();
                        }
                    }
                }, 0, delay * 1000);

                break;

            case "s":
            case "stop":

                spamTimer.cancel();
                spamTimer = new Timer();

                break;

            default:
                event.getTextChannel().sendMessage(getCat()).queue();
                event.getMessage().delete().queue();
                break;

        }

    }

    public void executed(boolean success, MessageReceivedEvent event) {

    }

    public String help() {
        return "USAGE:\n" +
                "**cat**  -  `Returns a cute cat picture`\n" +
                "**cat spam <period in secs>**  -  `Spams cat pictures automatically`\n" +
                "**cat stop**  -  `Stops the cat spam`";
    }

    @Override
    public String description() {
        return "=^..^=";
    }

    @Override
    public String commandType() {
        return STATIC.CMDTYPE.chatutils;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            JSONObject json = new JSONObject(readAll(rd));
            return json;
        } finally {
            is.close();
        }
    }

    public static String getCat() {

        String outputMessage = null; //Might be redudant
        JSONObject json = null; //Might be redudant
        try {
            json = readJsonFromUrl("http://random.cat/meow");
            outputMessage = json.getString("file");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return outputMessage;
    }


    @Override
    public Perm permission() {
        return Perm.DEFAULT;
    }
}
