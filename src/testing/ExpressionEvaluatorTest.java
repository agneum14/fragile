package testing;

import calculating.ExpressionElement;
import calculating.ExpressionEvaluator;
import calculating.MixedFraction;
import gui.Display.Operator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * ExpressionEvaluatorTest for the ExpressionEvaluator class.
 */
public class ExpressionEvaluatorTest
{
  private static List<List<ExpressionElement>> steps;

  static
  {
    steps = new ArrayList<>();
  }

  /**
   * Test method for a basic arithmetic expression.
   */
  @Test
  void basic()
  {
    ArrayList<ExpressionElement> expression = new ArrayList<>();
    expression.add(new MixedFraction(1, 1, 0, 1));
    expression.add(Operator.ADD);
    expression.add(new MixedFraction(1, 2, 0, 1));
    MixedFraction res = new ExpressionEvaluator(expression, steps).evaluate();

    assertEquals(0, res.compareTo(new MixedFraction(1, 3, 0, 1)));
  }

  /**
   * Test method for an empty expression.
   */
  @Test
  void emptyExpression()
  {
    ArrayList<ExpressionElement> expression = new ArrayList<>();

    assertThrows(IllegalArgumentException.class, () -> 
    {
      new ExpressionEvaluator(expression, steps).evaluate();
    });
  }

  /**
   * Test method for an expression with only one operand.
   */
  @Test
  void oneOperand()
  {
    ArrayList<ExpressionElement> expression = new ArrayList<>();
    expression.add(new MixedFraction(1, 1, 0, 1));
    MixedFraction res = new ExpressionEvaluator(expression, steps).evaluate();

    assertEquals(0, res.compareTo(new MixedFraction(1, 1, 0, 1)));
  }

  /**
   * Test method for an expression with only one operator.
   */
  @Test
  void oneOperator()
  {
    ArrayList<ExpressionElement> expression = new ArrayList<>();
    expression.add(Operator.ADD);

    assertThrows(IllegalStateException.class, () -> 
    {
      new ExpressionEvaluator(expression, steps).evaluate();
    });
  }

  /**
   * Test method for an expression with parentheses.
   */
  @Test
  void paren()
  {
    ArrayList<ExpressionElement> expression = new ArrayList<>();
    expression.add(new MixedFraction(1, 2, 0, 1));
    expression.add(Operator.MULT);
    expression.add(Operator.OPEN_PAREN);
    expression.add(new MixedFraction(1, 3, 0, 1));
    expression.add(Operator.SUB);
    expression.add(new MixedFraction(-1, 5, 0, 1));
    expression.add(Operator.CLOSE_PAREN);
    MixedFraction res = new ExpressionEvaluator(expression, steps).evaluate();

    assertEquals(0, res.compareTo(new MixedFraction(1, 16, 0, 1)));
  }

  /**
   * Test method for an expression with nested parentheses.
   */
  @Test
  void nestedParen()
  {
    ArrayList<ExpressionElement> expression = new ArrayList<>();
    expression.add(Operator.OPEN_PAREN);
    expression.add(new MixedFraction(1, 1, 0, 1));
    expression.add(Operator.MULT);
    expression.add(Operator.OPEN_PAREN);
    expression.add(new MixedFraction(1, 2, 0, 1));
    expression.add(Operator.ADD);
    expression.add(new MixedFraction(1, 3, 0, 1));
    expression.add(Operator.CLOSE_PAREN);
    expression.add(Operator.CLOSE_PAREN);
    expression.add(Operator.DIV);
    expression.add(new MixedFraction(1, 5, 0, 1));

    MixedFraction res = new ExpressionEvaluator(expression, steps).evaluate();

    assertEquals(0, res.compareTo(new MixedFraction(1, 1, 0, 1)));
  }

  /**
   * Test method for an expression with nested parentheses and various operators.
   */
  @Test
  void nestedParen2()
  {
    ArrayList<ExpressionElement> expression = new ArrayList<>();
    expression.add(Operator.OPEN_PAREN);
    expression.add(new MixedFraction(1, 0, 3, 4));
    expression.add(Operator.MED);
    expression.add(Operator.OPEN_PAREN);
    expression.add(new MixedFraction(1, 2, 4, 5));
    expression.add(Operator.SUB);
    expression.add(new MixedFraction(1, 1, 2, 7));
    expression.add(Operator.CLOSE_PAREN);
    expression.add(Operator.CLOSE_PAREN);
    expression.add(Operator.MULT);
    expression.add(new MixedFraction(1, 2, 1, 2));

    MixedFraction res = new ExpressionEvaluator(expression, steps).evaluate();

    assertEquals(0, res.compareTo(new MixedFraction(1, 0, 140, 39)));
  }

  /**
   * Test method for a null expression.
   */
  @Test
  void nullExpression()
  {
    assertThrows(IllegalArgumentException.class, () -> new ExpressionEvaluator(null, steps));
  }

  /**
   * Test method for null steps.
   */
  @Test
  void nullSteps()
  {
    List<ExpressionElement> expression = new ArrayList<>();
    expression.add(new MixedFraction(1, 1, 0, 1));
    assertThrows(IllegalArgumentException.class, () -> new ExpressionEvaluator(expression, null));
  }

  /**
   * Test method for an expression with unmatched parentheses.
   */
  @Test
  void unmatchedParen()
  {
    ArrayList<ExpressionElement> expression = new ArrayList<>();
    expression.add(Operator.OPEN_PAREN);
    expression.add(new MixedFraction(1, 1, 0, 1));
    expression.add(Operator.MULT);
    expression.add(Operator.OPEN_PAREN);
    expression.add(new MixedFraction(1, 2, 0, 1));
    expression.add(Operator.ADD);
    expression.add(new MixedFraction(1, 3, 0, 1));
    expression.add(Operator.CLOSE_PAREN);
    expression.add(Operator.DIV);
    expression.add(new MixedFraction(1, 5, 0, 1));

    assertThrows(IllegalStateException.class, () -> 
    {
      new ExpressionEvaluator(expression, steps).evaluate();
    });
  }

  /**
   * Test method for consecutive operators in the expression.
   */
  @Test
  void consecutiveOperators()
  {
    ArrayList<ExpressionElement> expression = new ArrayList<>();
    expression.add(new MixedFraction(1, 1, 0, 1));
    expression.add(Operator.DIV);
    expression.add(Operator.ADD);
    expression.add(new MixedFraction(1, 2, 0, 1));

    assertThrows(IllegalStateException.class,
        () -> new ExpressionEvaluator(expression, steps).evaluate());
  }

  /**
   * Test method for consecutive operands in the expression.
   */
  @Test
  void consecutiveOperands()
  {
    ArrayList<ExpressionElement> expression = new ArrayList<>();
    expression.add(new MixedFraction(1, 1, 0, 1));
    expression.add(new MixedFraction(1, 1, 0, 1));
    expression.add(Operator.ADD);
    expression.add(new MixedFraction(1, 2, 0, 1));

    assertThrows(IllegalStateException.class,
        () -> new ExpressionEvaluator(expression, steps).evaluate());
  }

  /**
   * Test method for an operand before an open parenthesis.
   */
  @Test
  void operandBeforeOpenParen()
  {
    ArrayList<ExpressionElement> expression = new ArrayList<>();
    expression.add(new MixedFraction(1, 1, 0, 1));
    expression.add(Operator.ADD);
    expression.add(new MixedFraction(1, 2, 0, 1));
    expression.add(Operator.OPEN_PAREN);
    expression.add(new MixedFraction(1, 2, 0, 1));
    expression.add(Operator.DIV);
    expression.add(new MixedFraction(1, 2, 0, 1));
    expression.add(Operator.CLOSE_PAREN);

    assertThrows(IllegalStateException.class,
        () -> new ExpressionEvaluator(expression, steps).evaluate());
  }

  /**
   * Test method for an operator after an open parenthesis.
   */
  @Test
  void operatorAfterOpenParen()
  {
    ArrayList<ExpressionElement> expression = new ArrayList<>();
    expression.add(new MixedFraction(1, 1, 0, 1));
    expression.add(Operator.ADD);
    expression.add(Operator.OPEN_PAREN);
    expression.add(Operator.ADD);
    expression.add(new MixedFraction(1, 2, 0, 1));
    expression.add(Operator.DIV);
    expression.add(new MixedFraction(1, 2, 0, 1));
    expression.add(Operator.CLOSE_PAREN);

    assertThrows(IllegalStateException.class,
        () -> new ExpressionEvaluator(expression, steps).evaluate());
  }

  /**
   * Test method for an operator before a close parenthesis.
   */
  @Test
  void operatorBeforeCloseParen()
  {
    ArrayList<ExpressionElement> expression = new ArrayList<>();
    expression.add(new MixedFraction(1, 1, 0, 1));
    expression.add(Operator.ADD);
    expression.add(Operator.OPEN_PAREN);
    expression.add(new MixedFraction(1, 2, 0, 1));
    expression.add(Operator.DIV);
    expression.add(new MixedFraction(1, 2, 0, 1));
    expression.add(Operator.DIV);
    expression.add(Operator.CLOSE_PAREN);

    assertThrows(IllegalStateException.class,
        () -> new ExpressionEvaluator(expression, steps).evaluate());
  }

  @Test
  void operandAfterCloseParen()
  {
    ArrayList<ExpressionElement> expression = new ArrayList<>();
    expression.add(new MixedFraction(1, 1, 0, 1));
    expression.add(Operator.ADD);
    expression.add(Operator.OPEN_PAREN);
    expression.add(new MixedFraction(1, 2, 0, 1));
    expression.add(Operator.DIV);
    expression.add(new MixedFraction(1, 2, 0, 1));
    expression.add(Operator.CLOSE_PAREN);
    expression.add(Operator.SUB);

    assertThrows(IllegalStateException.class,
        () -> new ExpressionEvaluator(expression, steps).evaluate());
  }
}
