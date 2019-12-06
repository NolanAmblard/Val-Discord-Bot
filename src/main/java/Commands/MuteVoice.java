package Commands;
//Nolan Amblard did this
import Bot.Commands;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;

public class MuteVoice implements Commands {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");

        try {
            if (message.length == 1) {
                event.getChannel().sendMessage("Please include the person you want to mute's tag.").queue();
            }

            if (message.length == 2) {
                try {
                    //Mutes a user
                    //String username = message[1];
                    User user = event.getMessage().getMentionedUsers().get(0);
                    event.getGuild().getMember(user).mute(true).queue();
                    event.getChannel().sendMessage("User " + user.getName() + " has been muted.").queue();
                }
                catch (IndexOutOfBoundsException e) {
                    event.getChannel().sendMessage("The requested user does not exist.").queue();
                }
            }

            if (message.length == 3) {
                try {
                    //Mutes a user for the specified reason
                    //String username = message[1];
                    String reason = message[2];
                    User user = event.getMessage().getMentionedUsers().get(0);
                    event.getGuild().getMember(user).mute(true).queue();
                    event.getChannel().sendMessage("User " + user.getName() + " has been muted. Reason: " + reason).queue();
                }
                catch (IndexOutOfBoundsException e) {
                    event.getChannel().sendMessage("The requested user does not exist.").queue();
                }
            }
        }
        catch (IllegalStateException e) {
            event.getChannel().sendMessage("The requested user is not currently in a voice channel.").queue();
        }
    }

    @Override
    public String getKeyword() {
        return "MuteVoice";
    }
}
