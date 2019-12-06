package main;

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
    public static FunctionExpression lengthOfGradient(FunctionExpression funcExp) {
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
        ArrayList<String> var = Calculus.getVariables();
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
            stack.elementAt(i).printVars();
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
                temp.addRight(derivRight);
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
                    FunctionExpression exp = FunctionExpression.product(muliEx, new Function("const", "/", funcExp.right.function.value));
                    FunctionExpression f = computeDerivative(wrt, funcExp.left);
                    if(f != null)
                        output = FunctionExpression.product(exp, f);
                    else
                        return null;
            }
        }
        else if(!ifOperator(funcExp.function.expression))
            return output = derive(wrt, funcExp);
        return FunctionExpression.simplifyNode(output);
    }

    //Adds derivatives of basic functions to a table by appending functions
    public static FunctionExpression derive(String wrt, FunctionExpression funcExp) {
        if(funcExp.function == null)
            return null;
        if(funcExp.function.compose == null && !(funcExp.function.var.equals(wrt)))
            return null;
        FunctionExpression out = null;
        if(funcExp.function.expression.equals("sin")) {
            FunctionExpression temp = funcExp.copy();
            temp.function.expression = "cos";
            out = temp;
        }
        else if(funcExp.function.expression.equals("cos")) {
            FunctionExpression temp = funcExp.copy();
            temp.function.expression = "sin";
            out = FunctionExpression.product(temp, -1.0);
        }
        else if(funcExp.function.expression.equals("ln")) {
            FunctionExpression temp = funcExp.copy();
            temp.function.expression = "" + funcExp.function.var;
            out = FunctionExpression.exp(temp, -1.0);
        }
        else if(wrt.equals(funcExp.function.var)) {
            FunctionExpression temp = new FunctionExpression(new Function("const", "/", 1.0));
            out = temp;
        }
        else if(funcExp.function.expression.equals("const"))
            return null;
        //Deals with functions in the form of f(g(x))
        if(funcExp.function.compose != null) {
            FunctionExpression temp = computeDerivative(wrt, funcExp.function.compose);
            if(temp == null)
                return null;
            out = FunctionExpression.product(out, temp);
        }
        return FunctionExpression.simplifyNode(out);
    }
    //Recursively performs operations, in order to parse in functions with multiple operations required
    private static void computeRecursion(FunctionExpression funcExp) {
        if(funcExp != null) {
            computeRecursion(funcExp.left);
            computeRecursion(funcExp.right);
            if(!ifOperator(funcExp.function.expression))
                stack.push(funcExp.function);
            else {
                Function x = stack.pop();
                Function y = stack.pop();
                stack.push(new Function("const", "/", compute(funcExp.function.expression, x, y)));
            }
        }
    }

    //does an operation involving two functions
    private static double compute(String op, Function x, Function y) {
        switch(op.charAt(0)) {
            case '+': return x.evaluate() + y.evaluate();
            case '-': return x.evaluate() - y.evaluate();
            case '*': return x.evaluate() * y.evaluate();
            case '/': return x.evaluate()/y.evaluate();
            case '^': return Math.pow(x.evaluate(), y.evaluate());
        }
        return 0;
    }

    //method seen a couple times from earlier, it returns whether or not something is an operator
    private static boolean ifOperator(String x) {
        return (x.equals("+") || x.equals("-") || x.equals("*") || x.equals("/") || x.equals("^"));
    }

    //getter and setter methods that could be useful
    public static ArrayList<String> getVariables() {
        return variables;
    }

    public static void setVariables(ArrayList<String> variables) {
        Calculus.variables = variables;
    }

    public static ArrayList<Double> getValues() {
        return values;
    }

    public static void setValues(ArrayList<Double> values) {
        Calculus.values = values;
    }

    public static void setValue(String variable, double value) {
        for(int i = 0; i < getVariables().size(); i++) {
            if(getVariables().get(i).equals(variable))
                values.set(i, value);
        }
    }

    //isValid method
    public boolean isValid(String x) {
        for(int i = 0; i < getVariables().size(); i++) {
            if(getVariables().get(i).equals(x))
                return true;
        }
        return false;
    }
}
