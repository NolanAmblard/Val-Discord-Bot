//Lawrence Zhang
package Commands;

import Bot.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class WordDictionary implements Commands {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        Message m = event.getMessage();
        String[] content = m.getContentRaw().toLowerCase().split(" ");
        MessageChannel channel = event.getChannel();
        EmbedBuilder embedBuilder = new EmbedBuilder();

        try {
            channel.sendMessage("Work in Progress!").queue();
        }
        catch (Exception e) {
            channel.sendMessage("Sorry, that's either not a word or an invalid input.").queue();
        }
    }

    @Override
    public String getKeyword() {
        return "Define";
    }
}
