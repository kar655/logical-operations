package logicalOperations.constants;

import logicalOperations.Expression;

public class True extends Expression {

    private static final True instance = new True();

    public static True getInstance() {
        return instance;
    }

    private True() {

    }

    @Override
    public boolean evaluate(boolean... values) {
        return true;
    }

    @Override
    public String toString() {
        return "T";
    }
}
