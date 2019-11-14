package Commands;
//Nolan Amblard did this
import Bot.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Help implements Commands {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String [] message = event.getMessage().getContentRaw().split(" ");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        Date date = new Date();
        if(message.length == 1 && message[0].equalsIgnoreCase("-userinfo") ) {
            //event.getChannel().sendMessage
        }
        else if(message.length == 2 && message[0].equalsIgnoreCase("-userinfo")){
            String userName = message[1];
            User user = event.getGuild().getMembersByName(userName, true).get(0).getUser();
            String avatar = user.getAvatarUrl();
            EmbedBuilder ae2 = new EmbedBuilder();

            ae2.setTitle(userName + "'s Profile");
            ae2.setThumbnail(avatar);
            ae2.addField("User name: ", event.getGuild().getMembersByName(userName, true).get(0).getAsMention(), true);
            ae2.addField("Online Status: ", event.getGuild().getMembersByName(userName, true).get(0).getOnlineStatus().toString(), true);
            ae2.setFooter("Request was made at " + formatter.format(date), event.getGuild().getIconUrl());
            ae2.setColor(Color.BLUE);
            event.getChannel().sendMessage(ae2.build()).queue();
        }

    }

    @Override
    public String getKeyword() {
        return "UserInfo";
    }
}
