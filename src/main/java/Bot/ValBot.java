//Lawrence Zhang
package Bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.FileInputStream;
import java.util.Properties;

//Based on Noah Liu's Discord Bot for implementation of commands and getting user input for ease of use
public class ValBot extends ListenerAdapter {

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("config.properties");
        properties.load(fileInputStream);

        CommandManager cm = new CommandManager();

        JDA bot = JDABuilder.createDefault(properties.getProperty("token")).addEventListeners(new Listener(cm)).build();
    }
}