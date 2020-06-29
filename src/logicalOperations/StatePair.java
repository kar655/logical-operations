package logicalOperations;

public class StatePair {

    private final String name;
    private boolean value;

    public StatePair(String name, boolean value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public boolean getValue() {
        return value;
    }

    public void changeValue() {
        value = !value;
    }
}
