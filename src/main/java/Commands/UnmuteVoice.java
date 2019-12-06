package Commands;
//Nolan Amblard did this
import Bot.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;

public class UnmuteVoice implements Commands {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if (message.length == 1) {
            event.getChannel().sendMessage("Please include the person you want to unmute's tag.").queue();
        }
        if (message.length == 2) {
            //unmutes the user mentioned in the message
            //username should be in message[1]
            User user = event.getMessage().getMentionedUsers().get(0);
            event.getGuild().getMember(user).mute(false).queue();
            //event.getMessage().getMentionedUsers().get(0).mute(false).queue();
            event.getChannel().sendMessage("User " + user.getName() + " has been unmuted.").queue();
        }
    }

    @Override
    public String getKeyword() {
        return "UnmuteVoice";
    }
}
