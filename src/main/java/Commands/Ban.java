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
            event.getChannel().sendMessage("Please include the person you want to ban's tag.").queue();
        }
        if (message.length == 2) {
            //Bans a user
            String username = message[1];
            event.getChannel().sendMessage("User " + event.getMessage().getMentionedUsers().get(0).getName() + " has been banned.").queue();
            event.getMessage().getMentionedMembers().get(0).ban(1).queue();
        }

        if (message.length == 3) {
            //Bans a user, includes a reason
            String username = message[1];
            String reason = message[2];
            event.getChannel().sendMessage("User " + event.getMessage().getMentionedUsers().get(0).getName() + " has been banned. Reason: " + reason).queue();
            event.getMessage().getMentionedMembers().get(0).ban(1, reason).queue();
        }
    }

    @Override
    public String getKeyword() {
        return "Ban";
    }
}
