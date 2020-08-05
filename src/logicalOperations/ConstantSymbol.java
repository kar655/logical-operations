package logicalOperations;

public enum ConstantSymbol {
    FAL("F", false),
    TRU("T", true);

    private final String symbol;
    private final boolean value;

    ConstantSymbol(String symbol, boolean isTrue) {
        this.symbol = symbol;
        this.value = isTrue;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean getValue() {
        return value;
    }

    public static boolean isSymbol(String input) {
        for (ConstantSymbol symbol : values())
            if (symbol.getSymbol().equals(input))
                return true;

        return false;
    }
}
