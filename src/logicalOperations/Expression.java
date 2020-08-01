package logicalOperations;

import java.security.spec.NamedParameterSpec;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public abstract class Expression implements Iterable<HashMap<String, Boolean>> {

    protected HashSet<String> usedVariables = new HashSet<>();


    public abstract boolean evaluate(HashMap<String, Boolean> values)
            throws VariableNoValue;

    public boolean isTautology() {
        for (HashMap<String, Boolean> state : this)
            if (!evaluate(state))
                return false;

        return true;
    }

    public abstract String toString();

    protected int priority() {
        return Priorities.EXPRESSION.getPriority();
    }

    public Expression neg() {
        return new Negation(this);
    }

    protected Expression andReversed(Expression expression) {
        return new OperationAnd(expression, this);
    }

    public Expression and(Expression expression) {
        return expression.andReversed(this);
    }

    protected Expression orReversed(Expression expression) {
        return new OperationOr(expression, this);
    }

    public Expression or(Expression expression) {
        return expression.orReversed(this);
    }

    protected Expression xorReversed(Expression expression) {
        return new OperationXor(expression, this);
    }

    public Expression xor(Expression expression) {
        return expression.xorReversed(this);
    }

    protected Expression xorTrue(Expression expression) {
        return new OperationXor(expression, this);
    }

    protected Expression xorFalse(Expression expression) {
        return new OperationXor(expression, this);
    }

    public Expression imply(Expression expression) {
        return new Implication(this, expression);
    }

    @Override
    public Iterator<HashMap<String, Boolean>> iterator() {
        return new ExpressionIterator(usedVariables);
    }
}
