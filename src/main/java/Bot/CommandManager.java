package Bot;

import java.util.*;
import java.util.regex.Pattern;

import Commands.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class CommandManager {

    private Map<String, Commands> commands = new HashMap<>();

    CommandManager() {

        //LawrenceZhang
        addCommand(new Calculate());
        //NolanAmblard
        addCommand(new Help());
        addCommand(new UserInfo());
    }

    //Add Command method which adds a command to the commands map
    private void addCommand(Commands command) {
        if (!commands.containsKey(command.getKeyword().toLowerCase())) {
            commands.put(command.getKeyword().toLowerCase(), command);
        }
    }

    //Get Commands method which gets all values of the commands map
    //Not in use right now
    public Collection<Commands> getCommands() {
        return commands.values();
    }

    //Get Command method which gets the requested String command
    //Not in use right now
    public Commands getCommand(@NotNull String command) {
        return commands.get(command);
    }

    //Run Command method for running a command when the keyword for the command is called
    void runCommand(MessageReceivedEvent event) {

        //The prefix we are using is a "-"
        //The user starts with the prefix and then adds the keyword for a specific command in order to call it
        String prefix = "-";

        //Removes the prefix from the inputted String and splits what is after the keyword
        String[] split = event.getMessage().getContentRaw().replaceFirst("(?i)" + Pattern.quote(prefix), "").split("\\s+");

        String keyword = split[0].toLowerCase();

        //Checking if the commands map contains the keyword/command
        if (commands.containsKey(keyword)) {
            List<String> args = Arrays.asList(split).subList(1, split.length);

            event.getChannel().sendTyping().queue();
            commands.get(keyword).execute(args, event);
        }
    }
}