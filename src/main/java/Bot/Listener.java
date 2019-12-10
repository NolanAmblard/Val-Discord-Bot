//Lawrence Zhang
package Bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {

    private CommandManager cm;

    Listener(CommandManager cm) {
        this.cm = cm;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message m = event.getMessage();
        String content = m.getContentRaw();
        MessageChannel channel = event.getChannel();

        if (content.toLowerCase().contains("michael zheng")) {
            int randomNumber1 = (int) (12 * Math.random());
            double randomNumber2 = Math.random() * 1000;
            double randomNumber3 = Math.random() * 0.0001;
            channel.sendMessage("What's that? Tryhard spotted. Incoming at " + randomNumber1 + " o'clock - watch out Colonel! He's got his GPA of " + randomNumber2 + " out and flexing! Mikey Z coming in hot - estimated time of arrival: " + randomNumber3 + " seconds. Run before he asks you about your grades!").queue();
        }

        if (content.equalsIgnoreCase("Who is Val")) {
            channel.sendMessage("Mikey Z").queue();
        }

        if (content.equalsIgnoreCase("Who is Sal")) {
            channel.sendMessage("Teresa Luo").queue();
        }

        if (content.equalsIgnoreCase("Who is Sweaty")) {
            channel.sendMessage("Nolan Amblard").queue();
        }

        if (content.equalsIgnoreCase("Who is the secret Val")) {
            channel.sendMessage("Raunakk Chandhoke").queue();
        }

        if (content.equalsIgnoreCase("Who is Anti-Val")) {
            channel.sendMessage("Lawrence Zhang").queue();
        }
        else if (content.equalsIgnoreCase("Who is Trash")) {
            channel.sendMessage("Lawrence Zhang").queue();
        }
        else if (content.equalsIgnoreCase("Who is a Bot")) {
            channel.sendMessage("Lawrence Zhang").queue();
        }
        else if (content.equalsIgnoreCase("Who has no Brain")) {
            channel.sendMessage("Do you even need to ask?").queue();
        }
        else if (content.equalsIgnoreCase("Who has the worst grades in the world")) {
            channel.sendMessage("Bruh").queue();
        }

        if (!event.getAuthor().isBot() && content.startsWith("-")) {
            cm.runCommand(event);
        }
    }
}