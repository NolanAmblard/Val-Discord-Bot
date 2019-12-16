//Lawrence Zhang
package Bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

//Based on Noah Liu's Discord Bot for implementation of commands and getting user input for ease of use
public class ValBot extends ListenerAdapter {

    private ValBot() {
        CommandManager cm = new CommandManager();
        Listener listener = new Listener(cm);

        try {
            new DefaultShardManagerBuilder().setToken("NjM2NjU5MTQ5ODk1MzY4NzQ2.Xb-IlQ.0bNZnwVhsQjTwuRNpKIH8u-Ej4I").addEventListeners(listener).build();
        }
        catch (Exception e) {
            e.getMessage();
        }
    }
    public static void main(String[] args) throws Exception {

        JDA bot = new JDABuilder("NjM2NjU5MTQ5ODk1MzY4NzQ2.Xb-IlQ.0bNZnwVhsQjTwuRNpKIH8u-Ej4I").addEventListeners(new ValBot()).build();
    }
}