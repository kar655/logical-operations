package logicalOperations;

import java.util.HashMap;

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
        return OperationSymbols.AND.getSymbol();
    }

    @Override
    public boolean evaluate(HashMap<String, Boolean> values) {
        return left.evaluate(values) && right.evaluate(values);
    }
}
