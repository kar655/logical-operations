package logicalOperations;

public enum Priorities {

    EXPRESSION(1000),
    NEG(500),
    OR(10),
    XOR(50),
    AND(100),
    IMPLY(5),
    EQUIV(1);

    private final int priority;


    Priorities(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
