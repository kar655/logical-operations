package logicalOperations;

public abstract class Operation extends Expression {

    protected Expression left;
    protected Expression right;

    public Operation(Expression left, Expression right) {
        this.left = left;
        this.right = right;

        usedVariables.addAll(left.usedVariables);
        usedVariables.addAll(right.usedVariables);
    }

    protected abstract String operatorToString();

    @Override
    public String toString() {
        String leftStr, rightStr;

        if (left.priority() < priority())
            leftStr = "(" + left + ")";
        else
            leftStr = "" + left;

        if (right.priority() <= priority())
            rightStr = "(" + right + ")";
        else
            rightStr = "" + right;

        return leftStr + operatorToString() + rightStr;
    }
}
