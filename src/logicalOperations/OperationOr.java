package logicalOperations;

public class OperationOr extends Operation {

    public OperationOr(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected int priority() {
        return Priorities.OR.getPriority();
    }

    @Override
    protected String operatorToString() {
        return "|";
    }

    @Override
    public boolean evaluate(boolean... values) {
        return left.evaluate(values) || right.evaluate(values);
    }
}
