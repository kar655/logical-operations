package logicalOperations;

public enum ConstantSymbol {
    FAL("F"),
    TRU("T");

    private final String symbol;

    ConstantSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static boolean isSymbol(String input) {
        for (ConstantSymbol symbol : values())
            if (symbol.getSymbol().equals(input))
                return true;

        return false;
    }

    public static Expression getExpression(String symbol) {
        if (FAL.getSymbol().equals(symbol))
            return False.getInstance();
        else if (TRU.getSymbol().equals(symbol))
            return True.getInstance();
        else
            throw new SymbolNotFound(symbol);
    }
}
