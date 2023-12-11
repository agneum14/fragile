package testing;

import calculating.ConditionalExpressionEvaluator;
import calculating.ExpressionElement;
import calculating.MixedFraction;
import gui.Display.Operator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ConditionExpressionEvaluatorTest for the ConditionExpressionEvaluator class.
 */
public class ConditionExpressionEvaluatorTest
{
  /**
   * Tests a basic conditional expression where a fraction is greater than another fraction.
   */
  @Test
  void basicTest()
  {
    List<ExpressionElement> exp = new ArrayList<>();
    exp.add(new MixedFraction(new MixedFraction(1, 2, 0, 1)));
    exp.add(Operator.GREATER);
    exp.add(new MixedFraction(new MixedFraction(1, 1, 0, 1)));

    assertTrue(ConditionalExpressionEvaluator.evaluate(exp));
  }

  /**
   * Tests a conditional expression with three elements, checking if one fraction is greater than or
   * equal to another.
   */
  @Test
  void threeTest()
  {
    List<ExpressionElement> exp = new ArrayList<>();
    exp.add(new MixedFraction(new MixedFraction(1, 2, 0, 1)));
    exp.add(Operator.GREATER);
    exp.add(new MixedFraction(new MixedFraction(1, 1, 0, 1)));
    exp.add(Operator.EQUAL_TO);
    exp.add(new MixedFraction(new MixedFraction(1, 1, 0, 1)));

    assertTrue(ConditionalExpressionEvaluator.evaluate(exp));
  }

  /**
   * Tests a complex conditional expression with multiple expressions and arithmetic operations.
   */
  @Test
  void twoExpressionsTest()
  {
    List<ExpressionElement> exp = new ArrayList<>();
    exp.add(new MixedFraction(new MixedFraction(1, 2, 0, 1)));
    exp.add(Operator.ADD);
    exp.add(new MixedFraction(new MixedFraction(1, 2, 0, 1)));
    exp.add(Operator.MULT);
    exp.add(new MixedFraction(new MixedFraction(1, 3, 0, 1)));
    exp.add(Operator.GREATER);
    exp.add(new MixedFraction(new MixedFraction(1, 1, 0, 1)));
    exp.add(Operator.SUB);
    exp.add(new MixedFraction(new MixedFraction(1, 1, 0, 1)));
    exp.add(Operator.DIV);
    exp.add(new MixedFraction(new MixedFraction(1, 1, 0, 1)));

    assertTrue(ConditionalExpressionEvaluator.evaluate(exp));
  }

  /**
   * Tests handling a null expression, expecting an IllegalArgumentException.
   */
  @Test
  void nullExpressionTest()
  {
    assertThrows(IllegalArgumentException.class, () ->
    {
      ConditionalExpressionEvaluator.evaluate(null);
    });
  }

  /**
   * Tests handling an expression with no conditionals, expecting an IllegalArgumentException.
   */
  @Test
  void noConditionalsTest()
  {
    List<ExpressionElement> exp = new ArrayList<>();
    exp.add(new MixedFraction(1, 1, 0, 1));

    assertThrows(IllegalArgumentException.class, () ->
    {
      ConditionalExpressionEvaluator.evaluate(exp);
    });
  }

  /**
   * Tests handling consecutive conditionals, expecting an IllegalArgumentException.
   */
  @Test
  void consecutiveConditionalsTest()
  {
    List<ExpressionElement> exp = new ArrayList<>();
    exp.add(new MixedFraction(new MixedFraction(1, 1, 0, 1)));
    exp.add(Operator.SUB);
    exp.add(new MixedFraction(new MixedFraction(1, 5, 0, 1)));
    exp.add(Operator.LESS);
    exp.add(Operator.LESS);
    exp.add(new MixedFraction(new MixedFraction(1, 1, 0, 1)));

    assertThrows(IllegalArgumentException.class, () ->
    {
      ConditionalExpressionEvaluator.evaluate(exp);
    });
  }

  /**
   * Tests a complex conditional expression with parentheses and arithmetic operations.
   */
  @Test
  void complexTest()
  {
    ArrayList<ExpressionElement> exp = new ArrayList<>();
    exp.add(Operator.OPEN_PAREN);
    exp.add(new MixedFraction(1, 1, 0, 1));
    exp.add(Operator.MULT);
    exp.add(Operator.OPEN_PAREN);
    exp.add(new MixedFraction(1, 2, 0, 1));
    exp.add(Operator.ADD);
    exp.add(new MixedFraction(1, 3, 0, 1));
    exp.add(Operator.CLOSE_PAREN);
    exp.add(Operator.CLOSE_PAREN);
    exp.add(Operator.DIV);
    exp.add(new MixedFraction(1, 5, 0, 1));
    exp.add(Operator.LESS);
    exp.add(new MixedFraction(1, 0, 0, 1));

    assertFalse(ConditionalExpressionEvaluator.evaluate(exp));
  }

}
