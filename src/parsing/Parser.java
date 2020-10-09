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
        LinkedList<Boolean> parenthesesNeg = new LinkedList<>(); // todo nie lepiej stack??
        ArrayList<LineArguments> lineArguments = new ArrayList<>();

        parseLineArguments(instructions, lineArguments);
        checkLineArguments(lineArguments, string);

        Expression temp;
        boolean previousNegation = false;

        // TODO assuming high priority of negation
        for (String instruction : instructions) {
            if (previousNegation && instruction.equals("(")) {
                previousNegation = false;
                symbols.push(null);
                parenthesesNeg.push(true);
            } else if (previousNegation) {
                previousNegation = false;
                if (instruction.equals("_"))
                    expressions.push(lastExpression.neg());
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

                if (parenthesesNeg.pollLast())
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
                            instruction));
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
        return expressions.peek();
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

    public void parseLine(String string) throws VariableNotFound {
        line++;

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
