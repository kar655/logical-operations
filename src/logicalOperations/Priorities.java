package logicalOperations;

public enum Priorities {
    EXPRESSION(1000);
    
    private final int priority;
    
    Priorities(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
