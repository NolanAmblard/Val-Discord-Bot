package Bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.*;

public interface Commands {

    void execute(List<String> args, MessageReceivedEvent event);

    String getKeyword();

}
