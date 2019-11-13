public class Function {
    String var;
    String expression;
    double value;
    //For composition functions, in the form f(g(x))
    FunctionExpression compose = null;

    //Constructors
    public Function(String expression, String var, double value) {
        this.var = var;
        this.expression = expression;
        this.value = value;
    }
    public Function(String expression) {
        this.expression = expression;
    }

    //Copies the original function
    public Function copy() {
        Function temp = new Function(expression);
        temp.value = value;
        temp.var = var;
        if(compose != null)
            temp.compose = compose.copy;
        return temp;
    }

    //Constructor for compose variable
    public void compose(FunctionExpression f) {
        compose = f;
    }

    //Print out all the variables
    public void printVars() {
        System.out.println("Function: " + expression);
        System.out.println("Variable: " + var);
        System.out.println("Value: " + value);
        if(compose != null)
            System.out.println("Compose: " + compose)
    }

    //Evaluates functions; how to add more possible functions
    public double evaluate() {
        double value = 0;
        if(var.equals("/"))
            value = this.value;
        else
            value = Calculus.getValue(var);
        if(compose != null)
            value = Calculus.compute(compose);
        if(expression.equals("const"))
            return this.value;
        if(expression.equals("sin"))
            return Math.sin(value);
        if(expression.equals("cos"))
            return Math.cos(value);
        if(expression.equals("ln"))
            return Math.log(value);
        if(expression.equals(variable))
            return value;
        return 0;
    }

}
