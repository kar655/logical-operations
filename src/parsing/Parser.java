package parsing;

import logicalOperations.*;

import java.util.*;

public class Parser {

    private int line = 0;
    private Expression lastExpression = null;

    private void errorMessage() {
        System.err.println("Error in line " + line);
    }

    private boolean checkParentheses(String[] instructions) {
        int parentheses = 0;

        for (String instruction : instructions) {
            if (instruction.equals("("))
                parentheses++;
            else if (instruction.equals(")"))
                if (--parentheses < 0)
                    return false;
        }

        return parentheses == 0;
    }

    private void parseLineArguments(String[] instructions,
                                    ArrayList<LineArguments> lineArguments) {
        for (String instruction : instructions) {
            if (instruction.equals("("))
                lineArguments.add(LineArguments.PARENTHESES_OPEN);
            else if (instruction.equals(")"))
                lineArguments.add(LineArguments.PARENTHESES_CLOSED);
            else if (instruction.equals(OperationSymbols.NEG.getSymbol()))
                lineArguments.add(LineArguments.NEGATION);
            else if (OperationSymbols.isSymbol(instruction))
                lineArguments.add(LineArguments.OPERATION);
            else
                lineArguments.add(LineArguments.VARIABLE);
        }
    }

    private void checkLineArguments(ArrayList<LineArguments> lineArguments,
                                    String string) {
        for (int i = 0; i < lineArguments.size(); i++) {
            if (lineArguments.get(i) == LineArguments.NEGATION) {
                if (i == lineArguments.size() - 1
                        || lineArguments.get(i + 1) == LineArguments.OPERATION
                        || lineArguments.get(i + 1) == LineArguments.NEGATION
                        || lineArguments.get(i + 1) ==
                        LineArguments.PARENTHESES_CLOSED)
                    throw new NegationError(string);
            } else if (lineArguments.get(i) == LineArguments.OPERATION) {
                if (i == 0
                        || i == lineArguments.size() - 1
                        ||
                        (lineArguments.get(i - 1) != LineArguments.VARIABLE
                                && lineArguments.get(i - 1)
                                != LineArguments.PARENTHESES_CLOSED)
                        ||
                        (lineArguments.get(i + 1) != LineArguments.VARIABLE
                                && lineArguments.get(i + 1)
                                != LineArguments.PARENTHESES_OPEN)
                                && lineArguments.get(i + 1)
                                != LineArguments.NEGATION)
                    throw new OperationError(string);
            } else if (lineArguments.get(i) == LineArguments.VARIABLE) {
                if (i > 0
                        && (lineArguments.get(i - 1) == LineArguments.VARIABLE
                        || lineArguments.get(i - 1) == LineArguments.PARENTHESES_CLOSED)
                        || i < lineArguments.size() - 1
                        && (lineArguments.get(i + 1) == LineArguments.VARIABLE
                        || lineArguments.get(i + 1) == LineArguments.PARENTHESES_OPEN))
                    throw new VariableError(string);
            }
        }
    }

    public Expression parserHelper(String string) {
        String[] instructions = string.split("\\s+");

        if (!checkParentheses(instructions))
            throw new ParenthesesError(string);

        Stack<Expression> expressions = new Stack<>();
        Stack<OperationSymbols> symbols = new Stack<>();
        Stack<Boolean> parenthesesNeg = new Stack<>();
        ArrayList<LineArguments> lineArguments = new ArrayList<>();

        parseLineArguments(instructions, lineArguments);
        checkLineArguments(lineArguments, string);

        Expression temp;
        boolean previousNegation = false;

        // assuming high priority of negation
        for (String instruction : instructions) {
            if (previousNegation && instruction.equals("(")) {
                previousNegation = false;
                symbols.push(null);
                parenthesesNeg.push(true);
            } else if (previousNegation) {
                previousNegation = false;
                if (instruction.equals("_"))
                    expressions.push(lastExpression.neg());
                else if (ConstantSymbol.isSymbol(instruction))
                    expressions.push(
                            ConstantSymbol.getExpression(instruction).neg());
                else
                    expressions.push(Variable.give(instruction).neg());
            } else if (instruction.equals(")")) {
                while (symbols.peek() != null) {
                    temp = expressions.pop();
                    expressions.push(OperationSymbols.call(
                            expressions.pop(),
                            temp,
                            symbols.pop().getSymbol()));
                }
                symbols.pop();

                if (parenthesesNeg.pop())
                    expressions.push(expressions.pop().neg());

            } else if (OperationSymbols.isSymbol(instruction)) {
                if (instruction.equals(OperationSymbols.NEG.getSymbol())) {
                    previousNegation = true;
                } else if (!symbols.isEmpty()
                        && symbols.peek() != null
                        && symbols.peek().getPriority()
                        > OperationSymbols.getSymbol(instruction).getPriority()) {

                    temp = expressions.pop();
                    expressions.push(OperationSymbols.call(
                            expressions.pop(),
                            temp,
                            symbols.pop().getSymbol()));

                    symbols.push(OperationSymbols.getSymbol(instruction));
                } else {
                    symbols.push(OperationSymbols.getSymbol(instruction));
                }
            } else if (ConstantSymbol.isSymbol(instruction)) {
                if (ConstantSymbol.FAL.getSymbol().equals(instruction))
                    expressions.push(False.getInstance());
                else
                    expressions.push(True.getInstance());
            } else if (instruction.equals("(")) {
                symbols.push(null);
                parenthesesNeg.push(false);
            } else if (instruction.equals("_")) {
                if (lastExpression == null)
                    throw new ParsingElementsError(string);
                else
                    expressions.push(lastExpression);
            } else { // is expression
                expressions.push(Variable.give(instruction));
            }
        }

        while (!symbols.isEmpty() && expressions.size() >= 2) {
            temp = expressions.pop();
            expressions.push(OperationSymbols.call(expressions.pop(), temp,
                    symbols.pop().getSymbol()));
        }

        lastExpression = expressions.peek();
        if (Options.HARD_FOLDING.isEnabled())
            lastExpression = lastExpression.fold();

        return lastExpression;
    }

    public void printResult(Expression expression) {
        if (Options.PARSED.isEnabled())
            System.out.println("Expression: " + expression);

        if (Options.TAUTOLOGY.isEnabled())
            System.out.println("Is tautology: " + expression.isTautology());

        if (Options.CONTRADICTION.isEnabled())
            System.out.println("Is contradiction: " + expression.isContradiction());

        if (Options.ALL_VALUES.isEnabled()) {
            System.out.println("All states:");
            for (HashMap<String, Boolean> state : expression)
                System.out.println("\t" + state
                        + "\tresult: " + expression.evaluate(state));
        }
    }

    private void getHelp() {
        System.out.println("Type in expression using operators");
        OperationSymbols.getInformation();
        System.out.println("To change operation symbols write !symbols");
        System.out.println("To change things printed write !options");
        System.out.println("To see this message write !help\n");
    }

    private void changeSymbols(String string) {
        String[] instructions = string.split("\\s+");

        if (instructions.length != 3) {
            System.err.println("ERROR wrong number of arguments for !symbols");
            System.out.println("usage: !symbols operation_name new_symbol");
            return;
        }

        if (OperationSymbols.isSymbol(instructions[2])) {
            System.err.println("ERROR symbol '"
                    + instructions[2] + "' is already in use");
            return;
        }

        try {
            OperationSymbols.valueOf(instructions[1].toUpperCase()).setSymbol(instructions[2]);
            System.out.println("Changed completed successfully!");
        } catch (IllegalArgumentException e) {
            System.err.println("ERROR there is no option called '"
                    + instructions[1].toUpperCase() + "'");
        }
    }

    private void changeOption(String string) {
        String[] instructions = string.split("\\s+");

        if (instructions.length != 3) {
            System.err.println("ERROR wrong number of arguments for !options");
            return;
        }

        boolean enabled;
        if (instructions[2].equals("True") || instructions[2].equals("true")
                || instructions[2].equals("T") || instructions[2].equals("t"))
            enabled = true;
        else if (instructions[2].equals("False") || instructions[2].equals("false")
                || instructions[2].equals("F") || instructions[2].equals("f"))
            enabled = false;
        else {
            System.err.println("ERROR wrong new value '" + instructions[2] + "'");
            return;
        }

        try {
            Options.valueOf(instructions[1].toUpperCase()).setEnabled(enabled);
            System.out.println("Changed completed successfully!");
        } catch (IllegalArgumentException e) {
            System.err.println("ERROR there is no option called '"
                    + instructions[1].toUpperCase() + "'");
        }
    }

    public void parseLine(String string) throws VariableNotFound {
        line++;

        if (string.equals("!help")) {
            getHelp();
            return;
        }

        if (string.equals("!options")) {
            Options.printAllOptions();
            return;
        }

        if (string.startsWith("!options ")) {
            changeOption(string);
            return;
        }

        if (string.equals("!symbols")) {
            OperationSymbols.getInformation();
            System.out.println("usage: !symbols operation_name new_symbol\n");
            return;
        }

        if (string.startsWith("!symbols ")) {
            changeSymbols(string);
            return;
        }

        if (!string.matches("^[a-zA-Z0-9=|&>^ -~]*$")) {
            System.err.println(
                    "Input can only contain letters, " +
                            "numbers, operations and '=' sign.");
            return;
        }

        Expression expression = parserHelper(string);
        printResult(expression);
    }
}
