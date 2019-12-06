//Lawrence Zhang
package Commands;

import Bot.Commands;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.*;

public class Calculate implements Commands {

    //Execution of the message
    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {

        Message m = event.getMessage();
        String[] content = m.getContentRaw().toLowerCase().split(" ");
        MessageChannel channel = event.getChannel();

        try {

            //If the content doesn't contain any spaces
            //append the string that contains the expression
            if (content.length == 2) {
                String expression = content[1];

                channel.sendMessage("" + expressionSolver(expression)).queue();
            }

            //Else, if the content contains spaces
            //append each section of the expression into a string
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

    //Returns the keyword to the Command Manager so it can run the method when this string is used
    @Override
    public String getKeyword() {
        return "Calculate";
    }

    //Calculate method for basic operator functions
    //Addition
    //Subtraction
    //Division
    //Multiplication
    //Powers
    //May want to implement square roots, Trigonometry functions, etc. later
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

    //Order of Operations method for operator precedence
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

    //Expression Solver method for simplifying a String expression into a double
    private static double expressionSolver(String expression) {

        //Get the content
        String[] content = expression.split(" ");

        StringBuilder expressionBuilder = new StringBuilder();

        for (String s : content) {
            expressionBuilder.append(s);
        }

        expression = expressionBuilder.toString();

        expressionBuilder = new StringBuilder();

        //Spaces out the expressionBuilder so that each character is separated by a String
        for (int i = 0; i < expression.length(); i++) {

            if (i > 0) {
                expressionBuilder.append(" ");
            }

            expressionBuilder.append(expression.charAt(i));
        }

        //Converting the expressionBuilder to a Character Array so that we can read in each individual operator and number
        char[] characters = expressionBuilder.toString().toCharArray();

        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < characters.length; i++) {

            //Troubleshooting
//            if (i % 2 == 0) {
//                System.out.println(numbers);
//                System.out.println(operators);
//            }

            //If the character in the Array is a space, we skip it
            if (characters[i] == ' ') {
                continue;
            }

            //Checks if the character is a number, negative sign, or decimal point
            if ((characters[i] == '-' && (i == 0 || (i >= 2 && Arrays.asList('^', '%', '*', '/', '+', '-', '(').contains(characters[i - 2]))) && (i < characters.length - 2 && (characters[i + 2] >= '0' || characters[i + 2] <= '9'))) || (characters[i] == '.') || (characters[i] >= '0' && characters[i] <= '9')) {
                StringBuilder temp = new StringBuilder();

                //Incrementing i by 2 because every other position in the Array will be a space
                //In case the character is a negative sign, we want to account for that only once
                //Because a negative sign can only be at the front of the number, we don't want to include this in the while loop or it might append a minus operator
                if (characters[i] == '-') {
                    temp.append(characters[i]);
                    i += 2;
                }

                while (i < characters.length && ((characters[i] == '.') || (characters[i] >= '0' && characters[i] <= '9'))) {
                    temp.append(characters[i]);
                    i += 2;
                }

                //Decrement i so that it is back in the correct position (would be one position more because of the way the while loop is constructed
                i -= 2;

                numbers.push(Double.parseDouble(temp.toString()));
            }

            //If the character is an open parentheses, we add it to the operator stack
            else if (characters[i] == '(') {
                operators.push(characters[i]);
            }

            //If the character is a closed parentheses, we pop off each operator in the operator stack until we reach an open parentheses
            //Pop off the first 2 numbers in the value stack and evaluate everything until you reach the open parentheses
            //add the result back into the number stack
            else if (characters[i] == ')') {
                while (operators.peek() != '(') {
                    char operator = operators.pop();
                    double num2 = numbers.pop();
                    double num1 = numbers.pop();

                    numbers.push(calculate(num1, Character.toString(operator), num2));
                }

                operators.pop();
            }

            //If the character is an operator, then check its precedence with orderOfOperations with the operator on the top of the operator stack
            //If it has greater precedence, then use it to evaluate the expression with the top two numbers of the value stack
            //Then push the result back onto the value stack
            else if (Arrays.asList('^', '%', '*', '/', '+', '-').contains(characters[i])) {
                while (!operators.isEmpty() && operators.peek() != '(' && orderOfOperations(Character.toString(operators.peek())) <= orderOfOperations(Character.toString(characters[i]))) {
                    char operator = operators.pop();
                    double num2 = numbers.pop();
                    double num1 = numbers.pop();

                    numbers.push(calculate(num1, Character.toString(operator), num2));
                }

                operators.push(characters[i]);
            }
        }

        //If there are any more expressions left
        //Pop off the first operator and the two numbers of their respective stacks and evaluate them
        while (!operators.isEmpty()) {
            char operator = operators.pop();
            double num2 = numbers.pop();
            double num1 = numbers.pop();

            numbers.push(calculate(num1, Character.toString(operator), num2));
        }

        //Return whatever is left in the value stack (which will be the result of your completely evaluated expression)
        return numbers.pop();
    }
}