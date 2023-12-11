package calculating;

import gui.Display.Operator;

import java.util.ArrayList;
import java.util.List;

/**
 * This is used in Display to evaluate conditional expressions (e.g. expressions with less than,
 * greater than, or equal to operands).
 *
 * This code complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class ConditionalExpressionEvaluator
{

  private static final String ERROR_MESSAGE = "malformed conditional expression";

  /**
   * Evaluate the given conditional expression. The general algorithm is to split the expression on
   * the conditional operators into sub-expressions, simplify those into mixed fractions with the
   * ExpressionEvaluator, and finally compare the mixed fractions.
   *
   * @param expression
   *          The conditional expression
   * @return The boolean result of the evaluation
   * @throws IllegalArgumentException
   *           If the conditional expression is malformed
   */
  public static boolean evaluate(final List<ExpressionElement> expression)
      throws IllegalArgumentException
  {
    if (expression == null)
    {
      throw new IllegalArgumentException("expression can't be null");
    }

    List<List<ExpressionElement>> subExpressions = new ArrayList<>();
    List<ExpressionElement> conditionalOperators = new ArrayList<>();
    List<MixedFraction> operands = new ArrayList<>();
    List<Integer> conditionalIndices = new ArrayList<>();
    conditionalIndices.add(-1);

    // find the conditional operators and indices
    for (int i = 0; i < expression.size(); i++)
    {
      ExpressionElement ee = expression.get(i);
      if (ee == Operator.LESS || ee == Operator.GREATER || ee == Operator.EQUAL_TO)
      {
        conditionalOperators.add(ee);
        conditionalIndices.add(i);
      }
    }
    conditionalIndices.add(expression.size());

    if (conditionalOperators.size() == 0)
    {
      throw new IllegalArgumentException(
          "there aren't any conditional operators in the expression");
    }

    for (int i = 0; i < conditionalIndices.size() - 1; i++)
    {
      Integer index = conditionalIndices.get(i);
      if (index < expression.size() - 1)
      {
        List<ExpressionElement> subExpression = expression.subList(index + 1,
            conditionalIndices.get(i + 1));
        subExpressions.add(subExpression);
      }
    }

    // evaluate the sub-expressions
    for (List<ExpressionElement> se : subExpressions)
    {
      try
      {
        MixedFraction result = new ExpressionEvaluator(se, new ArrayList<>()).evaluate();
        operands.add(result);
      }
      catch (IllegalArgumentException e)
      {
        throw new IllegalArgumentException(ERROR_MESSAGE);
      }
      catch (IllegalStateException e)
      {
        throw new IllegalArgumentException(ERROR_MESSAGE);
      }
    }

    // make list of expected comparison results
    ArrayList<Integer> expected = new ArrayList<>();
    for (ExpressionElement op : conditionalOperators)
    {
      if (op == Operator.LESS)
        expected.add(-1);
      else if (op == Operator.EQUAL_TO)
        expected.add(0);
      else if (op == Operator.GREATER)
        expected.add(1);
    }

    // calculate the actual comparisons
    ArrayList<Integer> actual = new ArrayList<>();
    for (int i = 0; i < operands.size() - 1; i++)
    {
      MixedFraction first = operands.get(i);
      MixedFraction second = operands.get(i + 1);
      actual.add(first.compareTo(second));
    }

    return expected.equals(actual);
  }
}
