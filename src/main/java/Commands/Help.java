//Nolan Amblard did this
package Commands;

import Bot.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;

public class Help implements Commands {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        //shows all commands and how to use them
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Moderator Commands");
        eb.addField("-ban @member [optional reason]", "This command bans a user from the server.", false);
        eb.addField("-kick @member [optional reason]", "This command kicks a user from the server.", false);
        eb.addField("-clear (number messages)", "This command clears 'n' number of messages from a channel.", false);
        eb.addField("-mute @member [optional reason]", "This command mutes a user.", false);
        eb.addField("-unmute @member", "This command unmutes a user.", false);
        eb.addField("-clear (number messages)", "This command clears 'n' number of messages from a channel.", false);
        eb.addField("-serverinfo", "This command sends information about the server to a channel.", false);
        eb.addField("-userinfo (@member)", "This command sends information about the user to a channel.", false);
        eb.addField("-unmutevoice @member", "This command unmutes a user in all voice channels.", false);
        eb.addField("-kinematics x x x x x", "This command does kinematics for the user.", false);
        eb.setColor(Color.CYAN);
        event.getChannel().sendMessage(eb.build()).queue();
        eb.clearFields();
        eb.setTitle("User Commands");
        eb.addField("-userinfo [optional @member] (no @member = info about person who sent it)", "This command returns info about the user.", false);
        eb.setColor(Color.CYAN);
        event.getChannel().sendMessage(eb.build()).queue();

        eb.clearFields();

        eb.setTitle("Math Commands");
        eb.addField("-calculate (operation)", "This command calculates what an expression evaluates out to be.", false);
        eb.addField("-derivative (function); (x-value) (variable differentiating with respect to)", "This command calculates the derivative of a function evaluated at a specific point.", false);
        eb.setColor(Color.CYAN);
        event.getChannel().sendMessage(eb.build()).queue();

        eb.clearFields();
        eb.setTitle("Misc. Commands");
        eb.addField("-setTimer [time]", "This command sets a timer.", false);
        eb.setColor(Color.CYAN);
        event.getChannel().sendMessage(eb.build()).queue();
    }

    @Override
    public String getKeyword() {
        return "Help";
    }
}
