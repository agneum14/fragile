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
 * ConditionExpressionEvaluatorTest
 */
public class ConditionExpressionEvaluatorTest
{

  @Test
  void basic()
  {
    List<ExpressionElement> exp = new ArrayList<>();
    exp.add(new MixedFraction(new MixedFraction(1, 2, 0, 1)));
    exp.add(Operator.GREATER);
    exp.add(new MixedFraction(new MixedFraction(1, 1, 0, 1)));

    assertTrue(ConditionalExpressionEvaluator.evaluate(exp));
  }

  @Test
  void three()
  {
    List<ExpressionElement> exp = new ArrayList<>();
    exp.add(new MixedFraction(new MixedFraction(1, 2, 0, 1)));
    exp.add(Operator.GREATER);
    exp.add(new MixedFraction(new MixedFraction(1, 1, 0, 1)));
    exp.add(Operator.EQUAL_TO);
    exp.add(new MixedFraction(new MixedFraction(1, 1, 0, 1)));

    assertTrue(ConditionalExpressionEvaluator.evaluate(exp));
  }

  @Test
  void twoExpressions()
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

  @Test
  void nullExpression()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionalExpressionEvaluator.evaluate(null);
    });
  }

  @Test
  void noConditionals()
  {
    List<ExpressionElement> exp = new ArrayList<>();
    exp.add(new MixedFraction(1, 1, 0, 1));

    assertThrows(IllegalArgumentException.class, () -> {
      ConditionalExpressionEvaluator.evaluate(exp);
    });
  }

  @Test
  void consecutiveConditionals()
  {
    List<ExpressionElement> exp = new ArrayList<>();
    exp.add(new MixedFraction(new MixedFraction(1, 1, 0, 1)));
    exp.add(Operator.SUB);
    exp.add(new MixedFraction(new MixedFraction(1, 5, 0, 1)));
    exp.add(Operator.LESS);
    exp.add(Operator.LESS);
    exp.add(new MixedFraction(new MixedFraction(1, 1, 0, 1)));

    assertThrows(IllegalArgumentException.class, () -> {
      ConditionalExpressionEvaluator.evaluate(exp);
    });
  }

  @Test
  void complex()
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