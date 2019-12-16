//Lawrence Zhang
package Bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.*;

//Based on Noah Liu's Discord Bot for implementation of commands and getting user input for ease of use
public interface Commands {

    //Runner method for each command
    void execute(List<String> args, MessageReceivedEvent event);

    //Returns the keyword that is required to call the specific command
    String getKeyword();

}