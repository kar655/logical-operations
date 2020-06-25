package logicalOperations;

public class Negation extends Expression {

    private Expression expression;

    public Negation(Expression expression) {
        this.expression = expression;
    }

    @Override
    public boolean evaluate(boolean... values) {
        return !expression.evaluate(values);
    }

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
