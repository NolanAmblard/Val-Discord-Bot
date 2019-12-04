package Commands;
//Nolan Amblard did this
import Bot.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;

public class Kick implements Commands {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if (message.length == 1) {
            event.getChannel().sendMessage("Please include the person you want to kick's tag.").queue();
        }
        if (message.length == 2) {
            //kicks a user
            String username = message[1];
            event.getMessage().getMentionedMembers().get(0).kick().queue();
            event.getChannel().sendMessage("User " + event.getMessage().getMentionedMembers().get(0).getNickname() + " has been kicked.").queue();
        }

        if (message.length == 3) {
            //kicks a user for specified reason
            String reason = message[2];
            event.getMessage().getMentionedMembers().get(0).kick(reason).queue();
            event.getChannel().sendMessage("User " + event.getMessage().getMentionedMembers().get(0).getNickname() + " has been kicked. Reason: " + reason).queue();
        }
    }

    @Override
    public String getKeyword() {
        return "Kick";
    }
}
