//Created by Raunakk Chandhoke
//This is able to handle more complex functions and can do operations with them
public class FunctionExpression {
    //For performing operations with functions
    FunctionExpression left;
    FunctionExpression right;
    Function function;

    //Constructor
    public FunctionExpression(Function function) {
        this.function = function;
        this.left = null;
        this.right = null;
    }

    //Copy the original function
    public FunctionExpression copy() {
        Function f = this.function.copy();
        FunctionExpression temp = new FunctionExpression(f);
        if(left != null)
            temp.addLeft(left.copy());
        if(right != null)
            temp.addRight(right.copy());
        return temp;
    }

    //Print out the tree that comprises the whole function by tree level
    public void printByLevel() {
        int i = 0;
        System.out.println("Level " + i);
        while(printByLevelRecur(this, 0, i)) {
            i++;
            System.out.println("\nLevel " + i);
        }
    }

    //Print the tree out via recursion
    public boolean printByLevelRecur(FunctionExpression funcExp, int j, int k) {
        if(funcExp == null)
            return false;
        if(j == k) {
            funcExp.function.print();
            return true;
        }
        else {
            boolean temp = printByLevelRecur(funcExp.left, j + 1, k);
            boolean temp1 = printByLevelRecur(funcExp.right, j + 1, k);
            if(temp || temp1)
                return true;
            else
                return false;
        }
    }

    //Set the left and right functions
    public void addLeft(FunctionExpression left) {
        this.left = left;
    }
    public void addRight(FunctionExpression right) {
        this.right = right;
    }

   //Operations with functions
    public static FunctionExpression sum(Function x, Function y) {
        FunctionExpression sum = new FunctionExpression(new Function("+"));
        sum.addLeft(new FunctionExpression(x));
        sum.addRight(new FunctionExpression(y));
        return sum;
    }

    public static FunctionExpression sum(Function x, FunctionExpression y) {
        FunctionExpression sum = new FunctionExpression(new Function("+"));
        sum.addLeft(new FunctionExpression(x));
        sum.addRight(y);
        return sum;
    }

    public static FunctionExpression sum(FunctionExpression x, Function y) {
        FunctionExpression sum = new FunctionExpression(new Function("+"));
        sum.addRight(new FunctionExpression(y));
        sum.addLeft(x);
        return sum;
    }

    public static FunctionExpression sum(FunctionExpression x, FunctionExpression y) {
        FunctionExpression sum = new FunctionExpression(new Function("+"));
        sum.addLeft(x);
        sum.addRight(y);
        return sum;
    }

    public static FunctionExpression sum(FunctionExpression x, double y) {
        FunctionExpression sum = new FunctionExpression(new Function("+"));
        sum.addLeft(x);
        sum.addRight(new FunctionExpression(new Function("const", "/", y)));
        return sum;
    }

    public static FunctionExpression sum(Function x, double y) {
        FunctionExpression sum = new FunctionExpression(new Function("+"));
        sum.addLeft(new FunctionExpression(x));
        sum.addRight(new FunctionExpression(new Function("const", "/", y)));
        return sum;
    }

    public static FunctionExpression sum(double x, Function y) {
        FunctionExpression sum = new FunctionExpression(new Function("+"));
        sum.addRight(new FunctionExpression(x));
        sum.addLeft(new FunctionExpression(new Function("const", "/", y)));
        return sum;
    }

    public static FunctionExpression sum(double x, FunctionExpression y) {
        FunctionExpression sum = new FunctionExpression(new Function("+"));
        sum.addRight(y);
        sum.addLeft(new FunctionExpression(new Function("const", "/", y)));
        return sum;
    }

    public static FunctionExpression sum(double x, double y) {
        FunctionExpression sum = new FunctionExpression(new Function("+"));
        sum.addLeft(new FunctionExpression(new Function("const", "/", x)));
        sum.addRight(new FunctionExpression(new Function("const", "/", y)));
        return sum;
    }

    public static FunctionExpression sub(FunctionExpression x, double y) {
        FunctionExpression sub = new FunctionExpression(new Function("-"));
        sub.addLeft(x);
        sub.addRight(new FunctionExpression(new Function("const". "/", y)));
        return sub;
    }

    public static FunctionExpression sub(FunctionExpression x, FunctionExpression y) {
        FunctionExpression sub = new FunctionExpression(new Function("-"));
        sub.addLeft(x);
        sub.addRight(y);
        return sub;
    }

    public static FunctionExpression sub(Function x, FunctionExpression y) {
        FunctionExpression sub = new FunctionExpression(new Function("-"));
        sub.addLeft(new FunctionExpression(x));
        sub.addRight(y);
        return sub;
    }

    public static FunctionExpression sub(Function x, Function y) {
        FunctionExpression sub = new FunctionExpression(new Function("-"));
        sub.addLeft(new FunctionExpression(x));
        sub.addRight(new FunctionExpression(y));
        return sub;
    }

    public static FunctionExpression sub(double x, double y) {
        FunctionExpression sub = new FunctionExpression(new Function("-"));
        sub.addLeft(new FunctionExpression(new Function("const", "/", x)));
        sub.addRight(new FunctionExpression(new Function("const", "/", y)));
        return sum;
    }

    public static FunctionExpression sub(double x, Function y) {
        FunctionExpression sub = new FunctionExpression(new Function("-"));
        sub.addLeft(new FunctionExpression(new Function("const", "/", x)));
        sub.addRight(new FunctionExpression(y));
        return sub;
    }

    public static FunctionExpression sub(double x, FunctionExpression y) {
        FunctionExpression sub = new FunctionExpression(new Function("-"));
        sub.addLeft(new FunctionExpression(new Function("const", "/", x)));
        sub.addRight(y);
        return sub;
    }

    public static FunctionExpression sub(Function x, double y) {
        FunctionExpression sub = new FunctionExpression(new Function("-"));
        sub.addLeft(new FunctionExpression(x));
        sub.addRight(new FunctionExpression(new Function("const", "/", y)));
        return sub;
    }

    public static FunctionExpression sub(FunctionExpression x, Function y) {
        FunctionExpression sub = new FunctionExpression(new Function("-"));
        sub.addLeft(x);
        sub.addRight(new FunctionExpression(y));
        return sub;
    }

    public static FunctionExpression div(Function x, Function y) {
        FunctionExpression div = new FunctionExpression(new Function("/"));
        div.addLeft(new FunctionExpression(x));
        div.addRight(new FunctionExpression(y));
        return div;
    }

    public static FunctionExpression div(FunctionExpression x, FunctionExpression y) {
        FunctionExpression div = new FunctionExpression(new Function("/"));
        div.addLeft(x);
        div.addRight(y);
        return div;
    }

    public static FunctionExpression div(double x, double y) {
        FunctionExpression div = new FunctionExpression(new Function("/"));
        div.addLeft(new FunctionExpression(new Function("const", "/", x)));
        div.addRight(new FunctionExpression(new Function("const", "/", y)));
        return div;
    }

    public static FunctionExpression div(Function x, FunctionExpression y) {
        FunctionExpression div = new FunctionExpression(new Function("/"));
        div.addLeft(new FunctionExpression(x));
        div.addRight(y);
        return div;
    }

    public static FunctionExpression div(FunctionExpression x, Function y) {
        FunctionExpression div = new FunctionExpression(new Function("/"));
        div.addLeft(x);
        div.addRight(new FunctionExpression(y));
        return div;
    }

    public static FunctionExpression div(double x, Function y) {
        FunctionExpression div = new FunctionExpression(new Function("/"));
        div.addLeft(new FunctionExpression(new Function("const", "/", x)));
        div.addRight(new FunctionExpression(y));
        return div;
    }

    public static FunctionExpression div(double x, FunctionExpression y) {
        FunctionExpression div = new FunctionExpression(new Function("/"));
        div.addLeft(new FunctionExpression(new Function("const", "/", x)));
        div.addRight(y);
        return div;
    }

    public static FunctionExpression div(Function x, double y) {
        FunctionExpression div = new FunctionExpression(new Function("/"));
        div.addLeft(new FunctionExpression(x));
        div.addRight(new FunctionExpression(new Function("const", "/", y)));
        return div;
    }

    public static FunctionExpression div(FunctionExpression x, double y) {
        FunctionExpression div = new FunctionExpression(new Function("/"));
        div.addLeft(x);
        div.addRight(new FunctionExpression(new Function("const", "/", y)));
        return div;
    }

    public static FunctionExpression product(Function x, Function y) {
        FunctionExpression prod = new FunctionExpression(new Function("*"));
        prod.addLeft(new FunctionExpression(x));
        prod.addRight(new FunctionExpression(y));
        return prod;
    }

    public static FunctionExpression product(FunctionExpression x, FunctionExpression y) {
        FunctionExpression prod = new FunctionExpression(new Function("*"));
        prod.addLeft(x);
        prod.addRight(y);
        return prod;
    }

    public static FunctionExpression product(double x, double y) {
        FunctionExpression prod = new FunctionExpression(new Function("*"));
        prod.addLeft(new FunctionExpression(new Function("const", "/", x)));
        prod.addRight(new FunctionExpression(new Function("const", "/", y)));
        return prod;
    }

    public static FunctionExpression product(Function x, FunctionExpression y) {
        FunctionExpression prod = new FunctionExpression(new Function("*"));
        prod.addLeft(new FunctionExpression(x));
        prod.addRight(y);
        return prod;
    }

    public static FunctionExpression product(FunctionExpression x, Function y) {
        FunctionExpression prod = new FunctionExpression(new Function("*"));
        prod.addLeft(x);
        prod.addRight(new FunctionExpression(y));
        return prod;
    }

    public static FunctionExpression product(double x, Function y) {
        FunctionExpression prod = new FunctionExpression(new Function("*"));
        prod.addLeft(new FunctionExpression(new Function("const", "/", x)));
        prod.addRight(new FunctionExpression(y));
        return product;
    }

    public static FunctionExpression product(double x, FunctionExpression y) {
        FunctionExpression prod = new FunctionExpression(new Function("*"));
        prod.addLeft(new FunctionExpression(new Function("const", "/", x)));
        prod.addRight(y);
        return prod;
    }

    public static FunctionExpression product(Function x, double y) {
        FunctionExpression prod = new FunctionExpression(new Function("*"));
        prod.addLeft(new FunctionExpression(x));
        prod.addRight(new FunctionExpression(new Function("const", "/", y)));
        return prod;
    }

    public static FunctionExpression product(FunctionExpression x, double y) {
        FunctionExpression prod = new FunctionExpression(new Function("*"));
        prod.addLeft(x);
        prod.addRight(new FunctionExpression(new Function("const", "/", y)));
        return prod;
    }

    public static FunctionExpression exp(Function x, Function y) {
        FunctionExpression exp = new FunctionExpression(new Function("^"));
        exp.addLeft(new FunctionExpression(x));
        exp.addRight(new FunctionExpression(y));
        return exp;
    }

    public static FunctionExpression exp(FunctionExpression x, FunctionExpression y) {
        FunctionExpression exp = new FunctionExpression(new Function("^"));
        exp.addLeft(x);
        exp.addRight(y);
        return exp;
    }

    public static FunctionExpression exp(double x, double y) {
        FunctionExpression exp = new FunctionExpression(new Function("^"));
        exp.addLeft(new FunctionExpression(new Function("const", "/", x)));
        exp.addRight(new FunctionExpression(new Function("const", "/", y)));
        return exp;
    }

    public static FunctionExpression exp(Function x, FunctionExpression y) {
        FunctionExpression exp = new FunctionExpression(new Function("^"));
        exp.addLeft(new FunctionExpression(x));
        exp.addRight(y);
        return exp;
    }

    public static FunctionExpression exp(FunctionExpression x, Function y) {
        FunctionExpression exp = new FunctionExpression(new Function("^"));
        exp.addLeft(x);
        exp.addRight(new FunctionExpression(y));
        return exp;
    }

    public static FunctionExpression exp(double x, Function y) {
        FunctionExpression exp = new FunctionExpression(new Function("^"));
        exp.addLeft(new FunctionExpression(new Function("const", "/", x)));
        exp.addRight(new FunctionExpression(y));
        return exp;
    }

    public static FunctionExpression exp(double x, FunctionExpression y) {
        FunctionExpression exp = new FunctionExpression(new Function("^"));
        exp.addLeft(new FunctionExpression(new Function("const", "/", x)));
        exp.addRight(y);
        return exp;
    }

    public static FunctionExpression exp(Function x, double y) {
        FunctionExpression exp = new FunctionExpression(new Function("^"));
        exp.addLeft(new FunctionExpression(x));
        div.addRight(new FunctionExpression(new Function("const", "/", y)));
        return exp;
    }

    public static FunctionExpression exp(FunctionExpression x, double y) {
        FunctionExpression exp = new FunctionExpression(new Function("^"));
        exp.addLeft(x);
        exp.addRight(new FunctionExpression(new Function("const", "/", y)));
        return exp;
    }

    //Does basic simplification of a specific part of an expression
    public static FunctionExpression simplifyNode(FunctionExpression funcExp) {
        if(funcExp == null)
            return null;
        if(funcExp.function.expression.equals("*")) {
            if(funcExp.left.function.expression.equals("const") && funcExp.left.function.value == 1)
                return funcExp.right;
            if(funcExp.right.function.expression.equals("const") && funcExp.right.function.value == 1)
                return funcExp.left;
        }
        if(funcExp.function.expression.equals("^")) {
            if(funcExp.right.function.expression.equals("const") && funcExp.right.function.value == 1)
                return funcExp.left;
        }
        if(funcExp.function.expression.equals("+") || funcExp.function.expression.equals("-")) {
            if(funcExp.left.function.expression.equals("const") && funcExp.left.function.value == 0)
                return funcExp.right;
            if(funcExp.right.function.expression.equals("const") && funcExp.right.function.value == 0)
                return funcExp.left;
        }
        return funcExp;
    }
    
    //Simplifies the expression as a whole
    public static FunctionExpression simplifyExpression(FunctionExpression funcExp) {
        if(funcExp == null)
            return null;
        funcExp.left = simplifyExpression(funcExp.left);
        funcExp.right = simplifyExpression(funcExp.right);
        return simplifyNode(funcExp);
    }
}
