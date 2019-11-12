package Bot;

import java.util.*;
import java.util.regex.Pattern;

import Commands.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class CommandManager {

    private Map<String, Commands> commands = new HashMap<>();

    CommandManager() {
        //TODO: Nolan is working on this
        //addCommand(new HelpCommand(this));

        addCommand(new Calculate());
    }

    private void addCommand(Commands command) {
        if (!commands.containsKey(command.getKeyword().toLowerCase())) {
            commands.put(command.getKeyword().toLowerCase(), command);
        }
    }

    public Collection<Commands> getCommands() {
        return commands.values();
    }

    public Commands getCommand(@NotNull String command) {
        return commands.get(command);
    }

    void runCommand(MessageReceivedEvent event) {
        String prefix = "-";

        String[] split = event.getMessage().getContentRaw().replaceFirst("(?i)" + Pattern.quote(prefix), "").split("\\s+");

        String keyword = split[0].toLowerCase();

        if (commands.containsKey(keyword)) {
            List<String> args = Arrays.asList(split).subList(1, split.length);

            event.getChannel().sendTyping().queue();
            commands.get(keyword).execute(args, event);
        }
    }
}
