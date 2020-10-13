package logicalOperations;

public enum OperationSymbols {

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

    public int getPriority() {
        return Priorities.valueOf(this.name()).getPriority();
    }

    public static boolean isSymbol(String input) {
        for (OperationSymbols symbol : values())
            if (symbol.getSymbol().equals(input))
                return true;

        return false;
    }

    public static OperationSymbols getSymbol(String input) throws SymbolNotFound {
        for (OperationSymbols symbol : values())
            if (symbol.getSymbol().equals(input))
                return symbol;

        throw new SymbolNotFound("Can't parse symbol '" + input + "'");
    }

    public static Expression call(Expression e1, Expression e2, String function)
            throws SymbolNotFound {
        if (function.equals(NEG.getSymbol())) {
            return e1.neg();
        } else if (function.equals(OR.getSymbol())) {
            return e1.or(e2);
        } else if (function.equals(XOR.getSymbol())) {
            return e1.xor(e2);
        } else if (function.equals(AND.getSymbol())) {
            return e1.and(e2);
        } else if (function.equals(IMPLY.getSymbol())) {
            return e1.imply(e2);
        } else {
            throw new SymbolNotFound(function);
        }
    }

    public static void getInformation() {
        for (OperationSymbols symbol : values())
            System.out.println(symbol.name()
                    + " symbol is " + symbol.getSymbol());
    }
}