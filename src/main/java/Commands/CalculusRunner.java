public class CalculusRunner {
    public static FunctionExpression equationExample1() {
        FunctionExpression test1 = FunctionExpression.exp(new Function("y", "y", 0), 1);
        FunctionExpression test2 = FunctionExpression.exp(new Function("x", "x", 0), 1);
        return FunctionExpression.product(test1, test2);
    }
    public static void derivative(FunctionExpression funcExp) {
        ArrayList<String> variables = new ArrayList<String>();
        ArrayList<Double> values = new ArrayList<Double>();
        values.add(2);
        variables.add("x");
        values.add(3);
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
        derivative(equationExample1());
    }
}
