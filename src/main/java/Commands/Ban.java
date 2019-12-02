package Commands;
//Nolan Amblard did this
import Bot.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;

public class Ban implements Commands {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if (message.length == 1) {
            event.getChannel().sendMessage("Please include the person you want to ban's tag.");
        }
        if (message.length == 2) {
            String username = message[1];
            event.getMessage().getMentionedMembers().get(0).ban(54750).queue();
            event.getChannel().sendMessage("User " + event.getMessage().getMentionedMembers().get(0).getNickname() + " has been banned.").queue();
        }

        if (message.length == 3) {
            String username = message[1];
            String reason = message[2];
            event.getMessage().getMentionedMembers().get(0).ban(54750, reason).queue();
            event.getChannel().sendMessage("User " + event.getMessage().getMentionedMembers().get(0).getNickname() + " has been banned. Reason: " + reason).queue();
        }
    }

    @Override
    public String getKeyword() {
        return "Ban";
    }
}
