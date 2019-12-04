//Nolan Amblard did this
package Commands;

import Bot.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ServerInfo implements Commands {
    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String [] message = event.getMessage().getContentRaw().split(" ");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        try {
            if(message.length == 1) {
                //Shows info on the server in which the command is sent
                EmbedBuilder eb = new EmbedBuilder();

                eb.setTitle(event.getGuild().getName());
                eb.setThumbnail(event.getGuild().getIconUrl());
                eb.addField("Owner: ", event.getGuild().getOwner().getNickname(), true);
                eb.addField("Date Created: ", event.getGuild()., true);
                eb.addField("Members: ", event.getGuild().getMembers().size() + " Members", false);
                eb.addField("Channels: ", event.getGuild().getChannels().size() + " Channels", true);
                eb.addField("Roles: ", event.getGuild().getRoles().size() + " Roles", true);
                eb.setFooter("Request was made at " + formatter.format(date), event.getGuild().getIconUrl());
                eb.setColor(Color.CYAN);
                event.getChannel().sendMessage(eb.build()).queue();
            }
        }
        catch (NullPointerException e) {
            event.getChannel().sendMessage("An error occurred.").queue();
        }
    }

    @Override
    public String getKeyword() {
        return "ServerInfo";
    }
}
