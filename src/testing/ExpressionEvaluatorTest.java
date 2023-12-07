package testing;

import calculating.ExpressionEvaluator;
import org.junit.jupiter.api.Test;

import calculating.MixedFraction;
import gui.Display.Operator;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ExpressionEvaluatorTest
 */
public class ExpressionEvaluatorTest {
  @Test
  public void basic() {
    ArrayList<Object> expression = new ArrayList<>();
    expression.add(Operator.OPEN_PAREN);
    expression.add(new MixedFraction(1, 1, 0, 1));
    expression.add(Operator.ADD);
    expression.add(new MixedFraction(1, 1, 0, 1));
    expression.add(Operator.CLOSE_PAREN);
    expression.add(Operator.MULT);
    expression.add(new MixedFraction(1, 2, 0, 1));
    MixedFraction res = new ExpressionEvaluator(expression).evaluate();
    res.reduce();

    assertEquals(1, res.getSign());
    assertEquals(4, res.getWhole());
    assertEquals(0, res.getNum());
    assertEquals(1, res.getDenom());
  }
}
