package Commands;
//Nolan Amblard did this
import Bot.Commands;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;

public class Mute implements Commands {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if (message.length == 1) {
            event.getChannel().sendMessage("Please include the person you want to mute's tag.").queue();
        }
        if (message.length == 2) {
            String username = message[1];
            event.getGuild().getMemberByTag(username).mute(true).queue();
            event.getChannel().sendMessage("User " + username + " has been muted.").queue();
        }

        if (message.length == 3) {
            String username = message[1];
            String reason = message[2];
            event.getGuild().getMemberByTag(username).mute(true).queue();
            event.getChannel().sendMessage("User " + username + " has been muted. Reason: " + reason).queue();
        }
    }

    @Override
    public String getKeyword() {
        return "Mute";
    }
}
