package Commands;
//Nolan Amblard did this
import Bot.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;

public class Clear implements Commands {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if (message.length == 1) {
            event.getChannel().sendMessage("Enter a number of messages to clear after the -clear command `ex: -clear 5`").queue();
        }
        if (message.length == 2) {
            String numdel = message[1];
            TextChannel target = event.getTextChannel();

            MessageHistory history = new MessageHistory(target);
            List<Message> msgs;

            msgs = history.retrievePast(Integer.parseInt(numdel)).complete();
            target.deleteMessages(msgs).queue();
            event.getChannel().sendMessage(numdel + " messages have been cleared.").queue();
        }
    }



    @Override
    public String getKeyword() {
        return "Clear";
    }
}
