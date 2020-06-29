package logicalOperations;

import java.util.HashMap;

public class Negation extends Expression {

    private Expression expression;

    public Negation(Expression expression) {
        this.expression = expression;
    }

    @Override
    public boolean evaluate(HashMap<String, Boolean> values) {
        return !expression.evaluate(values);
    }

//    @Override
//    public boolean isTautology() {
//        for (HashMap<String, Boolean> state : this) {
//            if (evaluate(state))
//                return false;
//        }
//
//        return true;
//    }

    @Override
    public String toString() {
        if (expression.priority() == Priorities.EXPRESSION.getPriority())
            return "~" + expression;
        else
            return "~(" + expression + ")";
    }

    @Override
    public Expression neg() {
        return expression;
    }
}
