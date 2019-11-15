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


}
