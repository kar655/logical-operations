package logicalOperations;

public enum Priorities {
    EXPRESSION(1000),
    OR(10),
    XOR(50),
    AND(100);
    
    private final int priority;
    
    Priorities(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
