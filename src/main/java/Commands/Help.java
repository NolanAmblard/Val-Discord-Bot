package Commands;
//Nolan Amblard did this
import Bot.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;

public class Help implements Commands {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Moderator Commands");
            eb.addField("-ban @member [optional reason]", "This command bans a user from the server.", true);
            eb.addField("-tempban @member (duration) [optional reason]", "This command bans a user from the server for a set time.", true);
            eb.addField("-kick @member [optional reason]", "This command kicks a user from the server.", true);
            eb.setColor(Color.BLUE);
            event.getChannel().sendMessage(eb.build()).queue();
    }

    @Override
    public String getKeyword() {
        return "Help";
    }
}