//Nolan Amblard did this
import com.sun.prism.paint.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] message = e.getMessage().getContentRaw().split(" ");
        if(message.length == 1 && message[0].equalsIgnoreCase("-help")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Moderator Commands");
            eb.addField("-ban @member [optional reason]", "This command bans a user from the server.", true);
            eb.addField("-tempban @member (duration) [optional reason]", "This command bans a user from the server for a set time.", true);
            eb.addField("-kick @member [optional reason]", "This command kicks a user from the server.", true);
            eb.setColor(Color.BLUE);
            e.getChannel().sendMessage(eb.build()).queue();
        }
    }
}