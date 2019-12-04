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
            //Mutes a user
            //String username = message[1];
            User user = event.getMessage().getMentionedMembers().get(0).getUser();
            event.getGuild().getMember(user).mute(true).queue();
            event.getChannel().sendMessage("User " + event.getMessage().getMentionedMembers().get(0).getNickname() + " has been muted.").queue();
        }

        if (message.length == 3) {
            //Mutes a user for the specified reason
            //String username = message[1];
            String reason = message[2];
            User user = event.getMessage().getMentionedMembers().get(0).getUser();
            event.getGuild().getMember(user).mute(true).queue();
            event.getChannel().sendMessage("User " + event.getMessage().getMentionedMembers().get(0).getNickname() + " has been muted. Reason: " + reason).queue();
        }
    }

    @Override
    public String getKeyword() {
        return "Mute";
    }
}
