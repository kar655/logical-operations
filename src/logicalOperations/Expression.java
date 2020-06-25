package logicalOperations;

public abstract class Expression {

    public abstract boolean evaluate(boolean... values);

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
        return expression.orReversed(expression);
    }

    protected Expression xorReversed(Expression expression) {
        return new OperationXor(expression, this);
    }

    public Expression xor(Expression expression) {
        return expression.xor(this);
    }

    protected Expression xorTrue(Expression expression) {
        return new OperationXor(expression, this);
    }

    protected Expression xorFalse(Expression expression) {
        return new OperationXor(expression, this);
    }
}
