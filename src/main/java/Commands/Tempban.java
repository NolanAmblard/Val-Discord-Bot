package Commands;
//Nolan Amblard did this
import Bot.Commands;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;

public class Tempban implements Commands {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if (message.length == 1) {
            event.getChannel().sendMessage("Please include the person you want to ban's tag.").queue();
        }
        if (message.length == 2) {
            event.getChannel().sendMessage("Please include the duration you want to ban the user for (whole number of days).").queue();
        }

        if (message.length == 3) {
            String username = message[1];
            int duration = Integer.parseInt(message[2]);
            event.getGuild().getMemberByTag(username).ban(duration).queue();
            event.getChannel().sendMessage("User " + username + " has been banned for " + duration + " days.").queue();
        }

        if (message.length == 4) {
            String username = message[1];
            int duration = Integer.parseInt(message[2]);
            String reason = message[3];
            event.getGuild().getMemberByTag(username).ban(duration, reason).queue();
            event.getChannel().sendMessage("User " + username + " has been banned for " + duration + " days. Reason: " + reason).queue();
        }
    }

    @Override
    public String getKeyword() {
        return "Tempban";
    }
}
