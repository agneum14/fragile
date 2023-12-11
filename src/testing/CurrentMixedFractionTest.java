package testing;

import calculating.CurrentMixedFraction;
import calculating.CurrentMixedFraction.Pos;
import calculating.MixedFraction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class that tests CurrentMixedFraction.
 */
public class CurrentMixedFractionTest
{
  /**
   * Asserts that the attributes of the CurrentMixedFraction match the expected values.
   *
   * @param cmf
   *          The CurrentMixedFraction to test.
   * @param sign
   *          The expected sign value.
   * @param whole
   *          The expected whole value.
   * @param num
   *          The expected numerator value.
   * @param denom
   *          The expected denominator value.
   * @param pos
   *          The expected position.
   */
  public void assertCMF(final CurrentMixedFraction cmf, final int sign, final Integer whole,
      final Integer num, final Integer denom, final Pos pos)
  {
    assertEquals(sign, cmf.getSign());
    assertEquals(whole, cmf.getWhole());
    assertEquals(num, cmf.getNum());
    assertEquals(denom, cmf.getDenom());
    assertEquals(pos, cmf.getPos());
  }

  /**
   * Test method for {@link CurrentMixedFraction#addDigit(int)} when a negative digit is added.
   */
  @Test
  void addNegDigitThrows()
  {
    final CurrentMixedFraction cmf = new CurrentMixedFraction();

    final Throwable e = assertThrows(IllegalArgumentException.class, () -> 
    {
      cmf.addDigit(-1);
    });

    assertEquals("d can't be less than 0", e.getMessage());
  }

  /**
   * Test method for {@link CurrentMixedFraction#addDigit(int)} when a large digit is added.
   */
  @Test
  void addLargeDigitThrows()
  {
    final CurrentMixedFraction cmf = new CurrentMixedFraction();

    final Throwable e = assertThrows(IllegalArgumentException.class, () -> 
    {
      cmf.addDigit(10);
    });

    assertEquals("d can't exceed 9", e.getMessage());
  }

  /**
   * Test method for {@link CurrentMixedFraction#addDigit(int)} to ensure proper digit addition.
   */
  @Test
  void addDigit()
  {
    final CurrentMixedFraction cmf = new CurrentMixedFraction();

    cmf.addDigit(1);
    cmf.addDigit(9);
    cmf.nextPos();
    cmf.addDigit(2);
    cmf.addDigit(5);
    cmf.nextPos();
    cmf.addDigit(5);
    cmf.addDigit(0);
    cmf.nextPos();
    cmf.addDigit(2);

    assertCMF(cmf, 1, 192, 25, 50, Pos.WHOLE);
  }

  /**
   * Test method for {@link CurrentMixedFraction#changeSign()} to verify sign change.
   */
  @Test
  void changeSign()
  {
    final CurrentMixedFraction cmf = new CurrentMixedFraction();

    cmf.changeSign();

    assertEquals(-1, cmf.getSign());
  }

  /**
   * Test method for {@link CurrentMixedFraction#clear()} to check if the fraction is cleared
   * properly.
   */
  @Test
  void clear()
  {
    final CurrentMixedFraction cmf = new CurrentMixedFraction();

    cmf.addDigit(2);
    cmf.nextPos();
    cmf.addDigit(5);
    cmf.nextPos();
    cmf.addDigit(8);
    cmf.nextPos();
    cmf.changeSign();
    cmf.clear();

    assertCMF(cmf, 1, null, null, null, Pos.WHOLE);
  }

  /**
   * Test method for {@link CurrentMixedFraction#toMixedFraction()} to ensure correct conversion to
   * MixedFraction.
   */
  void toMixedFraction()
  {
    final CurrentMixedFraction cmf = new CurrentMixedFraction();

    cmf.changeSign();
    cmf.addDigit(1);
    cmf.addDigit(5);
    cmf.nextPos();
    cmf.addDigit(5);
    cmf.nextPos();
    cmf.addDigit(1);
    cmf.addDigit(5);
    final MixedFraction mf = new MixedFraction(cmf);

    assertEquals(-1, mf.getSign());
    assertEquals(15, mf.getWhole());
    assertEquals(5, mf.getNum());
    assertEquals(15, mf.getDenom());
  }

  /**
   * Test method for {@link CurrentMixedFraction#invert()} to test the inversion of the fraction.
   */
  @Test
  void invertTest()
  {

  }

  /**
   * Test method for {@link CurrentMixedFraction#simplify()} to check if the fraction is simplified
   * correctly.
   */
  @Test
  void simplifyTest()
  {

  }

  /**
   * Test method for {@link CurrentMixedFraction#removeDigit()} to verify the removal of digits.
   */
  @Test
  void removeDigitTest()
  {

  }

  /**
   * Test method for {@link CurrentMixedFraction#prevPos()} to ensure the correct transition to the
   * previous position.
   */
  @Test
  void prevPosTest()
  {

  }

  /**
   * Test method for {@link CurrentMixedFraction#CurrentMixedFraction(MixedFraction)} to validate
   * the constructor with a MixedFraction.
   */
  @Test
  void cmfConstructorWithMFTest()
  {

  }
}
