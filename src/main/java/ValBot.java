import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.io.*;
import java.util.StringTokenizer;

public class ValBot extends ListenerAdapter {

    public static void main(String[] args) throws Exception {

        JDA bot = new JDABuilder("NjM2NjU5MTQ5ODk1MzY4NzQ2.Xb-IlQ.0bNZnwVhsQjTwuRNpKIH8u-Ej4I").addEventListeners(new ValBot()).build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message m = event.getMessage();
        String content = m.getContentRaw();
        MessageChannel channel = event.getChannel();

        if (content.equals("Who is Val")) {
            channel.sendMessage("Mikey Z").queue();
        }

        try {
            if (content.substring(0, 10).toLowerCase().equals("-calculate")) {
                String equation = content.substring(11);
                StringTokenizer token = new StringTokenizer(equation);

                double num1 = Integer.parseInt(token.nextToken());
                String operator = token.nextToken();
                double num2 = Integer.parseInt(token.nextToken());

                channel.sendMessage("" + Calculate((double) num1, operator, (double) num2)).queue();
            }
        }
        catch (Exception e) {
            channel.sendMessage("The operation includes invalid symbols.");
        }
    }

    public static double Calculate(double num1, String operator, double num2) {
        double answer = 0;

        if (operator.equals("+")) {
            answer = num1 + num2;
        }
        else if (operator.equals("-")) {
            answer = num1 - num2;
        }
        else if (operator.equals("*")) {
            answer = num1 * num2;
        }
        else if (operator.equals("/")) {
            answer = num1 / num2;
        }
        else if (operator.equals("%")) {
            answer = num1 % num2;
        }
        else if (operator.equals("^")) {
            answer = Math.pow(num1, num2);
        }

        return answer;
    }


}
