package main;

import main.Calculus;

import java.util.ArrayList;

public class CalculusRunner {
    public static FunctionExpression equationExample1() {
        FunctionExpression test1 = FunctionExpression.exp(new Function("y", "y", 0), 1);
        FunctionExpression test2 = FunctionExpression.exp(new Function("x", "x", 0), 1);
        return FunctionExpression.product(test1, test2);
    }
    public static void derivative(FunctionExpression funcExp) {
        ArrayList<String> variables = new ArrayList<String>();
        ArrayList<Double> values = new ArrayList<Double>();
        values.add(2.0);
        variables.add("x");
        values.add(3.0);
        variables.add("y");
        Calculus.setVariables(variables);
        Calculus.setValues(values);
        FunctionExpression f = funcExp;
        f = FunctionExpression.simplifyExpression(f);
        System.out.println("Function evaluated: " + Calculus.compute(f));
        FunctionExpression firstDerivativeX = Calculus.computeDerivative("x", f);
        if(firstDerivativeX != null) {
            firstDerivativeX = FunctionExpression.simplifyExpression(firstDerivativeX);
            System.out.println("d/dx = " + Calculus.compute(firstDerivativeX));
        }
    }

    public static void main(String[] args) {
        System.out.println("Abhi");
        Calculus test = new Calculus();
        ArrayList<String> variables = new ArrayList<String>();
        ArrayList<Double> values = new ArrayList<Double>();
        values.add(2.0);
        variables.add("x");
        values.add(3.0);
        variables.add("y");
        test.setVariables(variables);
        test.setValues(values);
        FunctionExpression f = equationExample1();
        f = FunctionExpression.simplifyExpression(f);
        System.out.println("Function evaluated: " + test.compute(f));
        FunctionExpression firstDerivativeX = test.computeDerivative("x", f);
        if(firstDerivativeX != null) {
            firstDerivativeX = FunctionExpression.simplifyExpression(firstDerivativeX);
            System.out.println("d/dx = " + test.compute(firstDerivativeX));
        }

    }
}
