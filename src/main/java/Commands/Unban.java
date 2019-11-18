package Commands;
//Nolan Amblard did this
import Bot.Commands;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;

public class Unban implements Commands {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if (message.length == 1) {
            event.getChannel().sendMessage("Please include the person you want to unban's tag.");
        }
        if (message.length == 2) {
            String username = message[1];
            event.getGuild().getMemberByTag(username).ban(0);
            event.getChannel().sendMessage("User " + username + " has been unbanned.");
        }
    }

    @Override
    public String getKeyword() {
        return "Unban";
    }
}
