package parsing;

import logicalOperations.*;

import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Parser {

    private int line = 0;

    private void errorMessage() {
        System.out.println("Error in line " + line);
    }

    public Expression parseLine(String string) throws VariableNotFound {
        line++;

        if (!string.matches("^[a-zA-Z0-9=|&>^ -~]*$")) {
            System.err.println(
                    "Input can only contain letters, numbers and '=' sign.");
            return null;
        }

        String[] instructions = string.split("\\s+");

        Stack<Expression> expressions = new Stack<>();
        Stack<OperationSymbols> symbols = new Stack<>();
        LinkedList<Boolean> parenthesesNeg = new LinkedList<>();

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

        while (!symbols.isEmpty()) {
            temp = expressions.pop();
            expressions.push(OperationSymbols.call(expressions.pop(), temp,
                    symbols.pop().getSymbol()));
        }

        System.out.println("Should be 1 == " + expressions.size());
        System.out.println("Should be 0 == " + symbols.size());

        if (expressions.size() != 1 || symbols.size() != 0)
            System.out.println("Something went wrong!");

        System.out.println(expressions.peek()
                + " tautology: "
                + expressions.peek().isTautology());

        return expressions.peek();
    }
}
