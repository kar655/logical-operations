package logicalOperations;

import java.util.ArrayList;
import java.util.HashMap;

public class Variable extends Expression {

    private final String name;
    private static final HashMap<String, Variable> variables = new HashMap<>();

    private Variable(String name) {
        this.name = name;
        usedVariables.add(name);
    }

    public static Variable give(String name) {
        if (!variables.containsKey(name))
            variables.put(name, new Variable(name));

        return variables.get(name);
    }

    /**
     * czy nie lepsze byloby trzymanie tak jak wczesniej tylko
     * dodac hashMape z (nazwa, id)
     * NIE bo jak bede chcial dac i-tej wartosci true to tworze tablice o i elementach
     * a to jest bez sensu dla duzych i
     */

    @Override
    public boolean evaluate(HashMap<String, Boolean> values)
            throws VariableNoValue {

        if (!values.containsKey(name))
            throw new VariableNoValue();

        return values.get(name);
    }

    @Override
    public boolean isTautology() {
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
