package testing;

import calculating.CurrentMixedFraction;
import calculating.CurrentMixedFraction.Pos;
import calculating.MixedFraction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurrentMixedFractionTest
{
  public void assertCMF(final CurrentMixedFraction cmf, final int sign, final Integer whole,
      final Integer num, final Integer denom, final Pos pos)
  {
    assertEquals(sign, cmf.getSign());
    assertEquals(whole, cmf.getWhole());
    assertEquals(num, cmf.getNum());
    assertEquals(denom, cmf.getDenom());
    assertEquals(pos, cmf.getPos());
  }

  @Test
  void addNegDigitThrows()
  {
    final CurrentMixedFraction cmf = new CurrentMixedFraction();

    final Throwable e = assertThrows(IllegalArgumentException.class, () -> {
      cmf.addDigit(-1);
    });

    assertEquals("d can't be less than 0", e.getMessage());
  }

  @Test
  void addLargeDigitThrows()
  {
    final CurrentMixedFraction cmf = new CurrentMixedFraction();

    final Throwable e = assertThrows(IllegalArgumentException.class, () -> {
      cmf.addDigit(10);
    });

    assertEquals("d can't exceed 9", e.getMessage());
  }

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

  @Test
  void changeSign()
  {
    final CurrentMixedFraction cmf = new CurrentMixedFraction();

    cmf.changeSign();

    assertEquals(-1, cmf.getSign());
  }

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

  @Test
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
}
