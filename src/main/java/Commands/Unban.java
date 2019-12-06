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
            event.getChannel().sendMessage("Please include the person's tag you want to unban.").queue();
        }
        if (message.length == 2) {
            //Unbans the user mentioned in the message
           event.getMessage().getMentionedMembers().get(0).ban(0).queue();
           event.getChannel().sendMessage("User " + event.getMessage().getMentionedMembers().get(0).getNickname() + " has been unbanned.").queue();
        }
    }

    @Override
    public String getKeyword() {
        return "Unban";
    }
}
