//LawrenceZhang
package Commands;

import Bot.Commands;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.*;

public class Calculate implements Commands {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {

        Message m = event.getMessage();
        String[] content = m.getContentRaw().toLowerCase().split(" ");
        MessageChannel channel = event.getChannel();

        try {
            if (content.length == 2) {
                String equation = content[1];

                boolean operandFound = false;
                int i = 0;

                while (!operandFound && i < equation.length() - 1) {
                    try {
                        if (!equation.substring(i, i + 1).equals(".")) {
                            double temp = Double.parseDouble(equation.substring(i, i + 1));
                        }
                    }
                    catch (NullPointerException | NumberFormatException e) {
                        operandFound = true;
                        equation = equation.substring(0, i) + " " + equation.substring(i, i + 1) + " " + equation.substring(i + 1);
                    }
                    i++;
                }

                StringTokenizer token = new StringTokenizer(equation);

                double num1 = Double.parseDouble(token.nextToken());
                String operator = token.nextToken();
                double num2 = Double.parseDouble(token.nextToken());

                channel.sendMessage("" + calculate(num1, operator, num2)).queue();
            }
            else if (content.length > 2) {
                double num1 = Double.parseDouble(content[1]);
                String operator = content[2];
                double num2 = Double.parseDouble(content[3]);

                channel.sendMessage("" + calculate(num1, operator, num2)).queue();
            }
        }
        catch (Exception e) {
            channel.sendMessage("The operation includes invalid symbols.").queue();
        }
    }

    @Override
    public String getKeyword() {
        return "Calculate";
    }

    private static double calculate(double num1, String operator, double num2) {
        double answer = 0;

        switch (operator) {
            case "+":
                answer = num1 + num2;
                break;
            case "-":
                answer = num1 - num2;
                break;
            case "*":
                answer = num1 * num2;
                break;
            case "/":
                answer = num1 / num2;
                break;
            case "%":
                answer = num1 % num2;
                break;
            case "^":
                answer = Math.pow(num1, num2);
                break;
        }

        return answer;
    }
}
