//Lawrence Zhang
package Bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.*;

public interface Commands {

    //Runner method for each command
    void execute(List<String> args, MessageReceivedEvent event);

    //Returns the keyword that is required to call the specific command
    String getKeyword();

}