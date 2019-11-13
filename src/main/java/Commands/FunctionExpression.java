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
        while(printByLevelRecursion(this, 0, i)) {
            i++;
            System.out.println("\nLevel " + i);
        }
    }

    //Print the tree out via recursion
    public boolean printByLevelRecursion(FunctionExpression funcExp, int j, int k) {
        if(funcExp == null)
            return false;
        if(j == k) {
            funcExp.function.print();
            return true;
        }
        else {
            boolean temp1 = printByLevelRecursion(funcExp.left, j + 1, k);
            boolean temp2 = printByLevelRecursion(funcExp.right, j + 1, k);
            if(temp1 || temp2)
                return true;
            else
                return false;
        }
    }
}
