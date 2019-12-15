package Commands;

//File that takes in the expression by the user and finds its derivative
//Created by Raunakk Chandhoke, but relies on Lawrence Zhang's expression parser
//for his calculate command to read in expressions
import Bot.Commands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.*;
public class CalculusRunner implements Commands {
     @Override
    public void execute(List<String> args, MessageReceivedEvent event) {

        Message m = event.getMessage();
        String[] content = m.getContentRaw().substring(0, m.getContentRaw().indexOf(";")).toLowerCase().split(" ");
        String[] rest = m.getContentRaw().substring(m.getContentRaw().indexOf(";") + 2).toLowerCase().split(" ");
        ArrayList<Double> values = new ArrayList<>();
        for(int i = 0; i < rest.length - 1; i++) {
            values.add(Double.parseDouble(rest[i]));
        }
        MessageChannel channel = event.getChannel();
        JDA jda = event.getJDA();
        User val = jda.getSelfUser();
        String wrt = m.getContentRaw().substring(m.getContentRaw().length() - 1).toLowerCase();

        try {

            //If the content doesn't contain any spaces
            //append the string that contains the expression
            if (content.length == 2) {
                String expression = content[1];

                
                channel.sendMessage("" + expressionSolver(channel, val, expression, values, wrt)).queue();
            }

            //Else, if the content contains spaces
            //append each section of the expression into a string
            else if (content.length > 2) {
                StringBuilder expression = new StringBuilder();

                for (int i = 1; i < content.length; i++) {
                    expression.append(content[i]);
                }

                channel.sendMessage("" + expressionSolver(channel, val, expression.toString(), values, wrt)).queue();
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
        return "Derivative";
    }

    //Calculate method for basic operator functions
    //Addition
    //Subtraction
    //Division
    //Multiplication
    //Powers
    private static FunctionExpression derivative(FunctionExpression left, String operator, FunctionExpression right) {
        FunctionExpression result = new FunctionExpression(new Function(operator));

        switch (operator) {
            case "^":
                result = result.exp(left, right);
                break;
            case "*":
                result = result.product(left, right);
                break;
            case "/":
                result = result.div(left, right);
                break;
            case "+":
                result = result.sum(left, right);
                break;
            case "-":
                result = result.sub(left, right);
                break;
        }

        return result;
    }

    //Order of Operations method for operator precedence
    private static int orderOfOperations(String operator) {
        switch (operator) {
            case "(":
            case ")":
                return 0;
            case "^":
                return 1;
            case "*":
            case "/":
                return 2;
            case "+":
            case "-":
                return 3;
        }

        return -1;
    }

    //Expression Solver method for simplifying a String expression into a double
    private String expressionSolver(MessageChannel channel, User self, String expression, ArrayList<Double> values, String wrt) {

        boolean invalidCharacters = false;

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

        Stack<FunctionExpression> numbers = new Stack<>();
        Stack<String> operators = new Stack<>();
        ArrayList<String> variables = new ArrayList<>();
        
        for (int i = 0; i < characters.length; i++) {

            //Troubleshooting
            if (i % 2 == 0) {
                System.out.println(numbers);
                System.out.println(operators);
            }

            //If the character in the Array is a space, we skip it
            if (characters[i] == ' ') {
                continue;
            }

            //Checks if the character is a number, negative sign, or decimal point
            if ((characters[i] == '-' && (i == 0 || (i >= 2 && Arrays.asList('^', '*', '/', '+', '-', '(').contains(characters[i - 2]))) && (i < characters.length - 2 && (characters[i + 2] >= '0' || characters[i + 2] <= '9'))) || (characters[i] == '.') || (characters[i] >= '0' && characters[i] <= '9')) {
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

                numbers.push(new FunctionExpression(new Function("const", "/", Double.parseDouble(temp.toString()))));
            }
            
            else if(characters[i] >= 97 && characters[i] <= 122) {
                for(int j = 0; j < variables.size(); j++) {
                    if(Character.toString(characters[i]).equalsIgnoreCase(variables.get(j)))
                        break;
                    else
                        variables.add(Character.toString(characters[i]));
                }
                
                numbers.push(new FunctionExpression(new Function(Character.toString(characters[i]))));
                
            }

            else if (Character.toString(characters[i]).equalsIgnoreCase("a")) {
                StringBuilder temp = new StringBuilder();

                int place;

                for (place = i; place < i + 12; place += 2) {
                    temp.append(characters[place]);
                }

                place -= 2;

                if (temp.toString().equalsIgnoreCase("answer")) {
                    MessageHistory history = new MessageHistory(channel);
                    List<Message> messages = history.retrievePast(10).complete();

                    i = place;

                    String message = null;

                    for (int j = 0; j < messages.size(); j++) {
                        if (messages.get(j).getAuthor().getId().equalsIgnoreCase(self.getId())) {
                            String messageRaw = history.getMessageById(messages.get(j).getId()).toString();

                            message = messageRaw.substring(messageRaw.indexOf(":", 2) + 1, messageRaw.lastIndexOf("("));

                            numbers.push(new FunctionExpression(new Function(message.toString())));

                            break;
                        }
                    }

                    if (message == null) {
                        channel.sendMessage("There are invalid characters in the message.").queue();

                        invalidCharacters = true;
                    }
                }
            }

            if (invalidCharacters) {
                break;
            }

            //If the character is an open parentheses, we add it to the operator stack
            else if (characters[i] == '(') {
                operators.push(Character.toString(characters[i]));
            }

            //If the character is a closed parentheses, we pop off each operator in the operator stack until we reach an open parentheses
            //Pop off the first 2 numbers in the value stack and evaluate everything until you reach the open parentheses
            //add the result back into the number stack
            else if (characters[i] == ')') {
                while (!(operators.peek().equals('('))) {
                    String operator = operators.pop();
                    FunctionExpression num2 = numbers.pop();
                    FunctionExpression num1 = numbers.pop();

                    numbers.push(derivative(num1, operator, num2));
                }

                operators.pop();
            }

            //If the character is an operator, then check its precedence with orderOfOperations with the operator on the top of the operator stack
            //If it has greater precedence, then use it to evaluate the expression with the top two numbers of the value stack
            //Then push the result back onto the value stack
            else if (Arrays.asList('^', '*', '/', '+', '-').contains(characters[i])) {
                while (!operators.isEmpty() && !(operators.peek().equals('(')) && orderOfOperations(operators.peek()) <= orderOfOperations(Character.toString(characters[i]))) {
                    String operator = operators.pop();
                    FunctionExpression num2 = numbers.pop();
                    FunctionExpression num1 = numbers.pop();

                    numbers.push(derivative(num1, operator, num2));
                }

                operators.push(Character.toString(characters[i]));
            }
        }

        //If there are any more expressions left
        //Pop off the first operator and the two numbers of their respective stacks and evaluate them
        while (!operators.isEmpty()) {
            String operator = operators.pop();
            FunctionExpression num2 = numbers.pop();
            FunctionExpression num1 = numbers.pop();

            numbers.push(derivative(num1, operator, num2));
        }

        //Return whatever is left in the value stack (which will be the result of your completely evaluated expression)
        Collections.sort(variables);
        Calculus.setVariables(variables);
        Calculus.setValues(values);
        FunctionExpression f = numbers.pop();
        f = FunctionExpression.simplifyExpression(f);
        FunctionExpression derivative = Calculus.computeDerivative(wrt, f);
        if(derivative != null) {
            derivative = FunctionExpression.simplifyExpression(derivative);
            return "" + Calculus.compute(derivative);
        }
        return "Your derivative does not exist.";
    }
  


}
