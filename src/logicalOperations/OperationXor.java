package logicalOperations;

public class OperationXor extends Operation {

    public OperationXor(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected int priority() {
        return Priorities.XOR.getPriority();
    }

    @Override
    protected String operatorToString() {
        return "^";
    }

    @Override
    public boolean evaluate(boolean... values) {
        return left.evaluate(values) != right.evaluate(values);
    }
}
