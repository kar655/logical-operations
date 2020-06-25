package logicalOperations.constants;

import logicalOperations.Expression;

public class False extends Expression {

    private static final False instance = new False();

    public static False getInstance() {
        return instance;
    }

    private False() {

    }

    @Override
    public boolean evaluate(boolean... values) {
        return false;
    }

    @Override
    public String toString() {
        return "F";
    }
}
