package logicalOperations;

public enum OperationSymbols {
    FAL("F"),
    TRU("T"),
    NEG("~"),
    OR("|"),
    XOR("^"),
    AND("&"),
    IMPLY("->");

    private final String symbol;


    OperationSymbols(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
