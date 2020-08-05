package logicalOperations;

import java.util.HashMap;

public class False extends Expression {

    private static final False instance = new False();

    public static False getInstance() {
        return instance;
    }

    private False() {

    }

    @Override
    public boolean evaluate(HashMap<String, Boolean> values) {
        return false;
    }

    @Override
    public boolean isTautology() {
        return false;
    }

    @Override
    public String toString() {
        return ConstantSymbol.FAL.getSymbol();
    }

    @Override
    public Expression neg() {
        return True.getInstance();
    }

    @Override
    protected Expression orReversed(Expression expression) {
        return expression;
    }

    @Override
    public Expression or(Expression expression) {
        return expression;
    }

    @Override
    protected Expression andReversed(Expression expression) {
        return getInstance();
    }

    @Override
    public Expression and(Expression expression) {
        return getInstance();
    }

    @Override
    public Expression xor(Expression expression) {
        return expression.xorFalse(this);
    }

    @Override
    protected Expression xorTrue(Expression expression) {
        return True.getInstance();
    }

    @Override
    protected Expression xorFalse(Expression expression) {
        return getInstance();
    }
}
