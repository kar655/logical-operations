package logicalOperations;

import java.util.HashMap;

public class Equivalence extends Operation {

    public Equivalence(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected int priority() {
        return Priorities.EQUIV.getPriority();
    }

    @Override
    protected String operatorToString() {
        return OperationSymbols.EQUIV.getSymbol();
    }

    @Override
    public boolean evaluate(HashMap<String, Boolean> values)
            throws VariableNoValue {
        return left.evaluate(values) == right.evaluate(values);
    }
}
