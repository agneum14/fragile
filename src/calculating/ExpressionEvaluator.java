package calculating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import gui.Display.Operator;

/**
 * ExpressionEvaluator
 */
public class ExpressionEvaluator {

    private static Map<Operator, Integer> precedence;

    private ArrayList<Object> expression;
    private Stack<MixedFraction> operands;
    private Stack<Operator> operators;

    static {
        precedence = new HashMap<>();
        precedence.put(Operator.ADD, 1);
        precedence.put(Operator.SUB, 1);
        precedence.put(Operator.MULT, 2);
        precedence.put(Operator.DIV, 2);
    }

    public ExpressionEvaluator(ArrayList<Object> expression) {
        this.expression = expression;
        operands = new Stack<>();
        operators = new Stack<>();
    }

    public MixedFraction evaluate() throws IllegalStateException {
        for (Object foo : expression) {
            if (foo instanceof MixedFraction) {
                MixedFraction operand = (MixedFraction) foo;
                operands.push(operand);
            } else if (foo instanceof Operator) {
                Operator operator = (Operator) foo;

                if (operator == Operator.OPEN_PAREN) {
                    operators.push(operator);
                } else if (operator == Operator.CLOSE_PAREN) {
                    while (operators.peek() != Operator.OPEN_PAREN) {
                        MixedFraction result = performOperation();
                        operands.push(result);
                    }
                } else {
                    while (!operators.isEmpty() && precedence.get(operator) <= precedence.get(operators.peek())) {
                        MixedFraction result = performOperation();
                        operands.push(result);
                    }
                    operators.push(operator);
                }
            }
            else {
                throw new IllegalStateException("Expression element isn't a MixedFraction or Operand");
            }
        }

        while (!operators.isEmpty()) {
            MixedFraction result = performOperation();
            operands.push(result);
        }
        return operands.pop();
    }

    private MixedFraction performOperation() throws IllegalStateException {
        MixedFraction a = operands.pop();
        MixedFraction b = operands.pop();
        Operator operator = operators.pop();
        MixedFraction res;

        switch (operator) {
            case ADD:
                res = MixedFraction.add(a, b);
                break;
            case SUB:
                res = MixedFraction.sub(a, b);
                break;
            case MULT:
                res = MixedFraction.mult(a, b);
                break;
            case DIV:
                res = MixedFraction.div(a, b);
                break;
            default:
                throw new IllegalStateException();
        };

        return res;
    }
}
