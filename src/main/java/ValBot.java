import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ValBot extends ListenerAdapter {

    public static void main(String[] args) throws Exception {

        JDA bot = new JDABuilder("NjM2NjU5MTQ5ODk1MzY4NzQ2.Xb-IlQ.0bNZnwVhsQjTwuRNpKIH8u-Ej4I").addEventListeners(new ValBot()).build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message m = event.getMessage();
        String content = m.getContentRaw();
        MessageChannel channel = event.getChannel();

        if (content.equals("Who is Val")) {
            channel.sendMessage("Mikey Z").queue();
        }
    }
}
