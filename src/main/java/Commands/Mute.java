package Commands;
//Nolan Amblard did this
import Bot.Commands;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;

public class Mute implements Commands {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        try (event.getMessage().getMentionedMembers().get(0).)
        if (message.length == 1) {
            event.getChannel().sendMessage("Please include the person you want to mute's tag.").queue();
        }
        if (message.length == 2) {
            String username = message[1];
            event.getGuild().addRoleToMember(event.getMessage().getMentionedMembers().get(0), (Role) event.getGuild().getRolesByName("MUTED", true));
            event.getChannel().sendMessage("User " + event.getMessage().getMentionedMembers().get(0).getNickname() + " has been muted.").queue();
        }

        if (message.length == 3) {
            String username = message[1];
            String duration = message[2];
            User user = event.getMessage().getMentionedMembers().get(0).getUser();
            event.getGuild().addRoleToMember(event.getMessage().getMentionedMembers().get(0), (Role) event.getGuild().getRolesByName("MUTED", true));
            event.getChannel().sendMessage("User " + event.getMessage().getMentionedMembers().get(0).getNickname() + " has been muted. Reason: " + reason).queue();
        }

        if (message.length == 4) {
            String username = message[1];
            String duration = message[2];
            String reason = message[3];
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
