//Lawrence Zhang
package Commands;

import Bot.Commands;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.*;

public class SetTimer implements Commands {

    //Execution of the message
    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {

        Message m = event.getMessage();
        User author = m.getAuthor();
        String[] content = m.getContentRaw().toLowerCase().split(" ");
        MessageChannel channel = event.getChannel();
        MessageHistory history = channel.getHistory();

        try {
            if (content.length == 2) {
                String[] times = content[1].split(":");

                double startTime = System.currentTimeMillis();
                double timerTime = getTotalTime(times);
                double currentTime = System.currentTimeMillis() - startTime;
                boolean timerOn = true;

                while (timerOn) {
                    //history.get
                    channel.sendMessage("Time: " + currentTime).queue();
                }
            }
            else if (content.length > 2) {

            }
        }
        catch (Exception e) {
            channel.sendMessage("The operation includes invalid symbols.").queue();
            channel.sendMessage("The error received is: " + e.getMessage()).queue();
        }
    }

    @Override
    public String getKeyword() {
        return "SetTimer";
    }

    private double getTotalTime(String[] times) {
        double time = 0;

        int multiplier = (int) Math.pow(60, (times.length - 1));

        for (String s : times) {
            time += Double.parseDouble(s) * multiplier;
            multiplier /= 60;
        }

        return time;
    }
}