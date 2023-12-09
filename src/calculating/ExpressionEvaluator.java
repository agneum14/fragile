package calculating;

import gui.Display.Operator;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * This class evaluates expression of MixedFractions the multiplication, division, addition and
 * subtraction operands, and parenthesis. Order of operations is supported.
 *
 * This code complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class ExpressionEvaluator
{

  private static final String EXCEPTION_STR = "malformed expression";

  private final List<ExpressionElement> expression;
  private final List<List<ExpressionElement>> steps;
  private final Stack<MixedFraction> operands;
  private final Stack<Operator> operators;

  /**
   * This constructor instantiates the structures needed for evaluating the expression. Furthermore,
   * a reference to steps is kept so it can be populated by the evaluate function.
   *
   * @param expression
   *     The expression to evaluate
   * @param steps
   *     The list to populate with each step
   * @throws IllegalArgumentException
   *     If expression or steps is null, or if expression is empty
   */
  public ExpressionEvaluator(final List<ExpressionElement> expression,
      final List<List<ExpressionElement>> steps) throws IllegalArgumentException
  {
    if (expression == null)
    {
      throw new IllegalArgumentException("expression can't be null");
    }
    if (steps == null)
    {
      throw new IllegalArgumentException("steps can't be null");
    }
    if (expression.isEmpty())
    {
      throw new IllegalArgumentException("expression can't be empty");
    }
    this.expression = expression;
    this.steps = steps;
    operands = new Stack<>();
    operators = new Stack<>();
  }

  /**
   * This method clears and populates steps with the intermediate steps, and calculates the result
   * of the expression.
   *
   * @return The mixed fraction result of the expression
   * @throws IllegalStateException
   *     If the expression is malformed. A malformed expression has unbalanced paranthesis,
   *     consecutive operands or operators, an operand before an open parenthesis, an operator after
   *     an open parenthesis, and operator before a closed parenthesis, or an operand after a closed
   *     parenthesis.
   */
  public MixedFraction evaluate() throws IllegalStateException
  {
    // clear steps
    steps.remove(steps);

    // check for invalid adjacencies
    for (int i = 1; i < expression.size(); i++)
    {
      if (expression.get(i - 1) instanceof MixedFraction && expression.get(
          i) instanceof MixedFraction)
      {
        throw new IllegalStateException(EXCEPTION_STR);
      }

      if (expression.get(i - 1) instanceof MixedFraction && expression.get(
          i) == Operator.OPEN_PAREN)
      {
        throw new IllegalStateException(EXCEPTION_STR);
      }
    }

    for (final ExpressionElement ee : expression)
    {
      if (ee instanceof MixedFraction)
      {
        final MixedFraction mf = (MixedFraction) ee;
        operands.push(mf);
      }
      else
      {
        final Operator operator = (Operator) ee;

        if (operator == Operator.OPEN_PAREN)
        {
          operators.push(operator);
        }
        else if (operator == Operator.CLOSE_PAREN)
        {
          while (!operators.isEmpty() && operators.peek() != Operator.OPEN_PAREN)
          {
            final MixedFraction result = performOperation();
            operands.push(result);
          }

          if (!operators.isEmpty())
          {
            operators.pop();
          }
        }
        else
        {
          while (!operators.isEmpty() && precedence(operator) <= precedence(operators.peek()))
          {
            final MixedFraction result = performOperation();
            operands.push(result);
          }
          operators.push(operator);
        }
      }
    }

    while (!operators.isEmpty())
    {
      final MixedFraction result = performOperation();
      operands.push(result);
    }
    return operands.pop();
  }

  /**
   * Performs a single operation (or step) by popping two operands and an operator from the
   * respective stacks. The step is added to steps in the format of operand, operator, operand,
   * result.
   *
   * @return The result of the operation
   * @throws IllegalStateException
   *     If the expression is malformed
   */
  private MixedFraction performOperation() throws IllegalStateException
  {
    MixedFraction a;
    MixedFraction b;
    Operator operator;
    MixedFraction result;

    try
    {
      a = operands.pop();
      b = operands.pop();
      operator = operators.pop();
    }
    catch (final EmptyStackException e)
    {
      throw new IllegalStateException(EXCEPTION_STR);
    }

    if (operator == Operator.ADD)
    {
      result = MixedFraction.add(a, b);
    }
    else if (operator == Operator.SUB)
    {
      result = MixedFraction.sub(b, a);
    }
    else if (operator == Operator.MULT)
    {
      result = MixedFraction.mult(a, b);
    }
    else if (operator == Operator.DIV)
    {
      result = MixedFraction.div(b, a);
    }
    else
    {
      throw new IllegalStateException(EXCEPTION_STR); // unreachable
    }

    final ArrayList<ExpressionElement> step = new ArrayList<>();
    step.add(b);
    step.add(operator);
    step.add(a);
    step.add(result);
    steps.add(step);

    return result;
  }

  /**
   * A helper function to get the precendence of a given operator.
   *
   * @param operator
   *     The operator
   * @return The operator's precedence
   */
  private int precedence(final Operator operator)
  {
    return switch (operator)
    {
      case ADD -> 1;
      case SUB -> 1;
      case MULT -> 1;
      case DIV -> 1;
      default -> 0;
    };
  }
}
