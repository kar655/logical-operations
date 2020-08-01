package logicalOperations;

import java.util.HashMap;

public class Implication extends Operation {

    public Implication(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected int priority() {
        return Priorities.IMPLY.getPriority();
    }

    @Override
    protected String operatorToString() {
        return "->";
    }

    @Override
    public boolean evaluate(HashMap<String, Boolean> values)
            throws VariableNoValue {
        return !left.evaluate(values) || right.evaluate(values);
    }
}
