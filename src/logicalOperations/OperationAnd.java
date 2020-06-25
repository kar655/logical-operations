package logicalOperations;

public class OperationAnd extends Operation {

    public OperationAnd(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected int priority() {
        return Priorities.AND.getPriority();
    }

    @Override
    protected String operatorToString() {
        return "&";
    }

    @Override
    public boolean evaluate(boolean... values) {
        return left.evaluate(values) && right.evaluate(values);
    }
}
