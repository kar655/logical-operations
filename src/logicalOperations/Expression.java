package logicalOperations;

public abstract class Expression {

    public abstract boolean evaluate(boolean... values);

    public abstract String toString();

    protected int priority() {
        return Priorities.EXPRESSION.getPriority();
    }
}
