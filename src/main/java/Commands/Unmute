package Commands;
//Nolan Amblard did this
import Bot.Commands;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Unmute implements Commands {

    private int counter = 0;

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if (message.length == 1) {
            event.getChannel().sendMessage("Please include the person you want to unmute's tag.").queue();
        }
        if (message.length == 2) {
            //String username = message[1];
            Member target = event.getMessage().getMentionedMembers().get(0);
            Role muted = target.getGuild().getRolesByName("Muted", true).get(0);
            event.getGuild().removeRoleFromMember(target, muted).queue();
            event.getChannel().sendMessage("User " + event.getMessage().getMentionedMembers().get(0).getNickname() + " has been unmuted.").queue();
        }
    }

    @Override
    public String getKeyword() {
        return "Unmute";
    }
}
