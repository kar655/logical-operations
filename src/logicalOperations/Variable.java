package logicalOperations;

import java.util.ArrayList;

public class Variable extends Expression {

    private final int id;
    private static final ArrayList<Variable> variables = new ArrayList<>();

    private Variable(int id) {
        this.id = id;
    }

    public static Variable give(int i) {
        if (i >= variables.size()) {
            variables.add(new Variable(i));
        }

        return variables.get(i);
    }

    @Override
    public boolean evaluate(boolean... values) {
        return values[id];
    }

    @Override
    public String toString() {
        return "x" + id;
    }
}
