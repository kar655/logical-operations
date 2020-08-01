package logicalOperations;

import java.util.HashMap;

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
        return OperationSymbols.OR.getSymbol();
    }

    @Override
    public boolean evaluate(HashMap<String, Boolean> values) {
        return left.evaluate(values) || right.evaluate(values);
    }
}
