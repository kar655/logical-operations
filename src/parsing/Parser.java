package parsing;

import logicalOperations.*;

import java.util.*;

public class Parser {

    private int line = 0;

    private void errorMessage() {
        System.out.println("Error in line " + line);
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
            }
        }
    }

    public Expression parseLine(String string) throws VariableNotFound {
        line++;

        if (!string.matches("^[a-zA-Z0-9=|&>^ -~]*$")) {
            System.err.println(
                    "Input can only contain letters, numbers and '=' sign.");
            return null;
        }

        String[] instructions = string.split("\\s+");

        if (!checkParentheses(instructions))
            throw new ParenthesesError(string);

        Stack<Expression> expressions = new Stack<>();
        Stack<OperationSymbols> symbols = new Stack<>();
        LinkedList<Boolean> parenthesesNeg = new LinkedList<>();
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
                System.out.println("Read constant symbol " + instruction);
            } else if (instruction.equals("(")) {
                symbols.push(null);
                parenthesesNeg.push(false);
            } else { // is expression
                expressions.push(Variable.give(instruction));
            }
        }

        if (previousNegation) {
            System.out.println("ERROR expecting expression after negation");
        }

        while (!symbols.isEmpty() && expressions.size() >= 2) {
            temp = expressions.pop();
            expressions.push(OperationSymbols.call(expressions.pop(), temp,
                    symbols.pop().getSymbol()));
        }

//        System.out.println(expressions.peek()
//                + " tautology: "
//                + expressions.peek().isTautology());

        return expressions.peek();
    }
}
