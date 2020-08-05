package logicalOperations;

import java.util.HashMap;

public class True extends Expression {

    private static final True instance = new True();

    public static True getInstance() {
        return instance;
    }

    private True() {

    }

    @Override
    public boolean evaluate(HashMap<String, Boolean> values) {
        return true;
    }

    @Override
    public boolean isTautology() {
        return true;
    }

    @Override
    public String toString() {
        return ConstantSymbol.TRU.getSymbol();
    }

    @Override
    public Expression neg() {
        return False.getInstance();
    }

    @Override
    protected Expression orReversed(Expression expression) {
        return getInstance();
    }

    @Override
    public Expression or(Expression expression) {
        return getInstance();
    }

    @Override
    protected Expression andReversed(Expression expression) {
        return expression;
    }

    @Override
    public Expression and(Expression expression) {
        return expression;
    }

    @Override
    public Expression xor(Expression expression) {
        return expression.xorTrue(this);
    }

    @Override
    protected Expression xorTrue(Expression expression) {
        return False.getInstance();
    }

    @Override
    protected Expression xorFalse(Expression expression) {
        return getInstance();
    }
}
