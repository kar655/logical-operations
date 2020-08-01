package logicalOperations;

import java.util.*;

public class ExpressionIterator implements Iterator<HashMap<String, Boolean>> {

    private HashMap<String, Boolean> currentStates = new HashMap<>();
    private Stack<StatePair> statePairs = new Stack<>();
    private final int states;
    private int currentState = 0;

    public ExpressionIterator(HashSet<String> usedVariables) {
        states = (int) Math.pow(2, usedVariables.size());

        for (String name : usedVariables) {
            statePairs.add(new StatePair(name, false));
            currentStates.put(name, false);
        }
    }

    private void changeState(StatePair statePair) {
        statePair.changeValue();
        currentStates.put(statePair.getName(), statePair.getValue());
    }

    private void nextState() {
        LinkedList<StatePair> removedPairs = new LinkedList<>();

        // remove till first false is found
        while (statePairs.peek().getValue()) {
            removedPairs.addLast(statePairs.pop());
        }

        StatePair temporary = statePairs.pop();
        changeState(temporary);
        statePairs.add(temporary);

        while (!removedPairs.isEmpty()) {
            changeState(removedPairs.getFirst());
            statePairs.add(removedPairs.pollFirst());
        }
    }

    @Override
    public boolean hasNext() {
        return currentState < states;
    }

    @Override
    public HashMap<String, Boolean> next() {
        if (states == 1)
            return new HashMap<>();
        else if (currentState++ != 0)
            nextState();

        return currentStates;
    }
}
