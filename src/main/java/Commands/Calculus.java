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

    //Evaluates a FunctionExpression at a point
    public static double compute(FunctionExpression funcExp) {
        if(funcExp == null)
            return 0;
        if(variables == null) {
            System.out.println("error: variables haven't been set yet");
            return 0;
        }
        Stack<Function> temp = stack;
        stack = new Stack<Function>();
        computeRecursion(funcExp);
        double o = stack.pop().evaluate();
        stack = temp;
        return o;
    }

    public void printStack() {
        if(stack.empty())
            return;
        System.out.println("Printing Stack");
        for(int i = 0; i < stack.size(); i++)
            stack.elementAt(i).print();
    }

    //Returns the derivative of a FunctionExpression as a FunctionExpression w.r.t a specified variable
    public static FunctionExpression computeDerivative(String wrt, FunctionExpression funcExp) {
        if(funcExp == null)
            return null;
        FunctionExpression output = null;
        if(funcExp.function.expression.equals("+") || funcExp.function.expression.equals("-")) {
            FunctionExpression temp = new FunctionExpression(new Function(new String(funcExp.function.expression)));
            FunctionExpression derivLeft = computeDerivative(wrt, funcExp.left);
            FunctionExpression derivRight = computeDerivative(wrt, funcExp.right);
            if(derivLeft != null)
                temp.addLeft(derivLeft);
            if(derivRight != null)
                temo.addRight(derivRight);
            if(derivLeft == null && derivRight == null)
                return null;
            if(derivLeft == null && derivRight != null)
                temp = derivRight;
            if(derivLeft != null && derivRight == null)
                temp = derivLeft;
            output = temp;
        }
        else if(funcExp.function.expression.equals("/"))
            output = computeDerivative(wrt, FunctionExpression.product(funcExp.left, FunctionExpression.exp(funcExp.right, -1)));
        else if(funcExp.function.expression.equals("*")) {
            FunctionExpression temp1 = new FunctionExpression(new Function("+"));
            FunctionExpression tempPart1 = new FunctionExpression(new Function("*"));
            FunctionExpression tempPart2 = new FunctionExpression(new Function("*"));
            FunctionExpression derivLeft = computeDerivative(wrt, funcExp.left);
            FunctionExpression derivRight = computeDerivative(wrt, funcExp.right);
            FunctionExpression copyLeft = null;
            FunctionExpression copyRight = null;
            if(funcExp.left != null)
                copyLeft = funcExp.left.copy();
            if(funcExp.right != null)
                copyRight = funcExp.right.copy();
            if(derivLeft == null)
                tempPart1 = null;
            else {
                tempPart1.addRight(copyRight);
                tempPart1.addLeft(derivLeft);
                temp1.addLeft(tempPart1);
            }
            if(derivRight == null)
                tempPart2 = null;
            else {
                tempPart2.addLeft(copyLeft);
                tempPart2.addRight(derivRight);
                temp1.addRight(tempPart2);
            }
            if(derivLeft == null && derivRight == null)
                return null;
            if(derivLeft == null)
                temp1 = tempPart2;
            if(derivRight == null)
                temp1 = tempPart1;
            output = temp1;
        }
        else if(funcExp.function.expression.equals("^")) {
            if(funcExp.right.function.expression.equals("const")) {
                    if(funcExp.right.function.value == 0)
                        return null;
                    FunctionExpression muliEx = FunctionExpression.exp(funcExp.left.copy(), new Function("const", "/", funcExp.right.function.value - 1));
                    FunctionExpression exp = FunctionExpression.prod(muliEx, new Function("const", "/", funcExp.right.function.value)));
                    FunctionExpression f = computeDerivative(wrt, funcExp.left);
                    if(f != null)
                        output = FunctionExpression.product(exp, f);
                    else
                        return null;
            }
        }
        else if(!ifOperator(funcExp.function.expression))
            return out = derive(wrt, funcExp);
        return FunctionExpression.simplifyNode(output);
    }    
}
