import java.util.ArrayList;
import java.util.Stack;

//Created by Raunakk Chandhoke
//DO NOT RUN
public class Calculus {
    static Stack<Function> stack;
    private static ArrayList<String> variables = null;
    private static ArrayList<Double> values = null;

    //Arbitrary Constructor
    public Calculus() {

    }

    //Returns the value the variable is set to
    public static double getValue(String variable) {
        for(int i = 0; i < getVariables().size(); i++) {
            if(getVariables().get(i).equals(variable))
                return getValues().get(i);
        }
        return 0;
    }

    //Returns length of the gradient of a function, as a function
    public static FunctionExpression lengthOfGradient(FunctionExpresssion funcExp) {
        ArrayList<FunctionExpression> derivatives = getDerivatives(funcExp);
        FunctionExpression grad = null;
        for(int i = 0; i < derivatives.size(); i++) {
            FunctionExpression x = FunctionExpression.exp(derivatives.get(i), 2.0);
            if(grad == null)
                grad = x;
            else
                grad = FunctionExpression.sum(grad, x);
        }
        return grad;
    }

    //Returns a list of all partial derivatives
    public static ArrayList<FunctionExpression> getDerivatives(FunctionExpression funcExp) {
        ArrayList<FunctionExpression> f = new ArrayList<FunctionExpression>();
        ArrayList<String> var = Calculus.getVariables;
        for(int i = 0; i < var.size(); i++) {
            FunctionExpression temp = computeDerivative(var.get(i), funcExp);
            f.add(FunctionExpression.simplifyExpression(temp));
        }
        return f;
    }
}
