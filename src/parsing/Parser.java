package parsing;

import logicalOperations.*;

import javax.swing.*;
import java.util.HashMap;
import java.util.Stack;

public class Parser {

    private int line = 0;

    private void errorMessage() {
        System.out.println("Error in line " + line);
    }

    public void parseLine(String string) throws VariableNotFound {
        line++;

        if (!string.matches("^[a-zA-Z0-9=|&>^ -]*$")) {
            System.err.println(
                    "Input can only contain letters, numbers and '=' sign.");
            return;
        }

        String[] instructions = string.split("\\s+");

        Stack<Expression> expressions = new Stack<>();
        Stack<OperationSymbols> symbols = new Stack<>();
        //symbols.push();

        Expression temp;

        // En.valueOf("NEG").getSymbol() ??
        // throws IllegalArgumentException

        for (String instruction : instructions) {
            if (OperationSymbols.isSymbol(instruction)) {
                if (!symbols.isEmpty()
                        && symbols.peek().getPriority()
                        > OperationSymbols.valueOf(instruction).getPriority()) {

                    // dobra kolejnosc?
                    expressions.push(OperationSymbols.call(
                            expressions.pop(),
                            expressions.pop(),
                            instruction));
                } else {
                    symbols.push(OperationSymbols.getSymbol(instruction));
                }
            } else if (ConstantSymbol.isSymbol(instruction)) {
                System.out.println("Read constant symbol " + instruction);
            } else { // is expression
                expressions.push(Variable.give(instruction));
            }
        }

        while (!symbols.isEmpty()) {
            temp = expressions.pop();
            expressions.push(OperationSymbols.call(expressions.pop(), temp,
                    symbols.pop().getSymbol()));
        }

        System.out.println(expressions.peek()
                + " tautology: "
                + expressions.peek().isTautology());
    }
}
