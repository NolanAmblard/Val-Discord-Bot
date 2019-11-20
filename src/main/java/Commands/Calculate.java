//Lawrence Zhang
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
                String expression = content[1];

                channel.sendMessage("" + expressionSolver(expression)).queue();
            }
            else if (content.length > 2) {
                StringBuilder expression = new StringBuilder();

                for (int i = 1; i < content.length; i++) {
                    expression.append(content[i]);
                }

                channel.sendMessage("" + expressionSolver(expression.toString())).queue();
            }
        }
        catch (Exception e) {
            channel.sendMessage("The operation includes invalid symbols.").queue();
            channel.sendMessage("The error received is: " + e.getMessage()).queue();
        }
    }

    @Override
    public String getKeyword() {
        return "Calculate";
    }

    private static double calculate(double num1, String operator, double num2) {
        double answer = 0;

        switch (operator) {
            case "^":
                answer = Math.pow(num1, num2);
                break;
            case "%":
                answer = num1 % num2;
                break;
            case "*":
                answer = num1 * num2;
                break;
            case "/":
                if (num2 != 0) {
                    answer = num1 / num2;
                }
                break;
            case "+":
                answer = num1 + num2;
                break;
            case "-":
                answer = num1 - num2;
                break;
        }

        return answer;
    }

    private static int orderOfOperations(String operator) {
        switch (operator) {
            case "(":
            case ")":
                return 0;
            case "^":
                return 1;
            case "%":
                return 2;
            case "*":
            case "/":
                return 3;
            case "+":
            case "-":
                return 4;
        }

        return -1;
    }

    private static double expressionSolver(String expression) {
        String[] content = expression.split(" ");

        StringBuilder expressionBuilder = new StringBuilder();

        for (String s : content) {
            expressionBuilder.append(s);
        }

        expression = expressionBuilder.toString();

        expressionBuilder = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            if (i > 0) {
                expressionBuilder.append(" ");
            }

            expressionBuilder.append(expression.charAt(i));
        }

        char[] characters = expressionBuilder.toString().toCharArray();

        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < characters.length; i++) {

            if (characters[i] == ' ') {
                continue;
            }

            if ((characters[i] == '.') || (characters[i] >= '0' && characters[i] <= '9')) {
                StringBuilder temp = new StringBuilder();

                while (i < characters.length && ((characters[i] == '.') || (characters[i] >= '0' && characters[i] <= '9'))) {
                    temp.append(characters[i]);
                    i += 2;
                }

                i -= 2;

                numbers.push(Double.parseDouble(temp.toString()));
            }
            else if (characters[i] == '(') {
                operators.push(characters[i]);
            }
            else if (characters[i] == ')') {
                while (operators.peek() != '(') {
                    char operator = operators.pop();
                    double num2 = numbers.pop();
                    double num1 = numbers.pop();

                    numbers.push(calculate(num1, Character.toString(operator), num2));
                }

                operators.pop();
            }
            else if (Arrays.asList('^', '%', '*', '/', '+', '-').contains(characters[i])) {
                while (!operators.isEmpty() && orderOfOperations(Character.toString(operators.peek())) >= orderOfOperations(Character.toString(characters[i]))) {
                    char operator = operators.pop();
                    double num2 = numbers.pop();
                    double num1 = numbers.pop();

                    numbers.push(calculate(num1, Character.toString(operator), num2));
                }

                operators.push(characters[i]);
            }
        }

        while (!operators.isEmpty()) {
            char operator = operators.pop();
            double num2 = numbers.pop();
            double num1 = numbers.pop();

            numbers.push(calculate(num1, Character.toString(operator), num2));
        }

        return numbers.pop();
    }
}
