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

        if (content.equals("Who is Val")) {
            channel.sendMessage("Mikey Z").queue();
        }

        if (!event.getAuthor().isBot() && content.startsWith("-")) {
            cm.runCommand(event);
        }
    }
}
