package it.degouw.bot.commands.etc;

import com.google.gson.JsonParser;
import it.degouw.bot.commands.ICommand;
import it.degouw.bot.commands.IGuildCommand;
import it.degouw.bot.commands.IPrivateCommand;
import it.degouw.bot.handler.PermissionHandler;
import it.degouw.bot.reference.CommandType;
import it.degouw.bot.reference.Perm;
import it.degouw.bot.util.Messages;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Log implements ICommand, IGuildCommand, IPrivateCommand{

    /**
     * Method by StupPlayer (https://github.com/StupPlayer)
     * @param data
     * @return
     */
    public static String hastePost(String data) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://hastebin.com/documents");

        try {
            post.setEntity(new StringEntity(data));

            HttpResponse response = client.execute(post);
            String result = EntityUtils.toString(response.getEntity());
            return "https://hastebin.com/" + new JsonParser().parse(result).getAsJsonObject().get("key").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Could not post!";
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {

        if (!PermissionHandler.check(this.permission(), event)) { return false; }

        if (args.length < 1) {
            event.getChannel().sendMessage(help()).queue();
            return false;
        }

        return true;
    }


    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        File f = new File("screenlog.0");

        if ((args.length > 0 ? args[0] : "").equalsIgnoreCase("clear")) {


            if (f.exists()) {
                if (f.delete()) {
                    f.createNewFile();
                    event.getChannel().sendMessage(Messages.success().setDescription("Successfully cleared log file.").build()).queue();
                } else {
                    event.getChannel().sendMessage(Messages.error().setDescription("Failed while attempting to clear log file.").build()).queue();
                }
            }
            return;
        }

        if (f.exists()) {

            BufferedReader br = new BufferedReader(new FileReader(f));
            List<String> logLines = new ArrayList<>();
            List<String> outLogLines;
            StringBuilder sb = new StringBuilder();
            //StringBuilder sbFull = new StringBuilder();
            //String shorted = "Unshorted log.";
            //Message msg = event.getTextChannel().sendMessage(new EmbedBuilder().setDescription("Uploading log to hastebin.com ...").build()).complete();

            br.lines().forEach(l -> logLines.add(l));
            //logLines.forEach(l -> sbFull.append(l + "\n"));

            outLogLines = logLines;
            if (logLines.size() > 20) {
                //shorted =   "Log shorted because it is longer than 20 lines.\n" +
                //            "See full log here: " + hastePost(sbFull.toString());
                outLogLines = outLogLines.subList(outLogLines.size() - 20, outLogLines.size());
            }

            outLogLines.forEach(s -> sb.append(s + "\n"));

            //msg.delete().queue();

            event.getChannel().sendMessage(
                    "__**zekroBot `screenlog.0` log**__\n\n" +
                            //"*" + shorted + "*\n\n" +
                            "```" +
                            sb.toString() +
                            "```"
            ).queue();

        } else {

            event.getChannel().sendMessage(Messages.error().setDescription("There is no file `'screenlog.0'` available to get log from.").build()).queue();

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
        return "Show bots log file";
    }


    @Override
    public CommandType commandType() {
        return CommandType.ETC;
    }

    @Override
    public Perm permission() {
        return Perm.BOT_OWNER;
    }
}

