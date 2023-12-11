package testing;

import calculating.MixedFraction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MixedFractionTest
{
  void assertMFs(final MixedFraction mf1, final MixedFraction mf2)
  {
    assertMFVars(mf1, mf2.getSign(), mf2.getWhole(), mf2.getNum(), mf2.getDenom());
  }

  void assertMFVars(final MixedFraction mf, final int sign, final int whole, final int num,
      final int denom)
  {
    assertEquals(sign, mf.getSign());
    assertEquals(whole, mf.getWhole());
    assertEquals(num, mf.getNum());
    assertEquals(denom, mf.getDenom());
  }

  @Test
  void constructor()
  {
    final MixedFraction mf = new MixedFraction(1, 2, 3, 4);

    assertMFVars(mf, 1, 2, 3, 4);
  }

  @Test
  void constructorZeroDenominatorThrows()
  {
    final Throwable e = assertThrows(IllegalArgumentException.class, () -> {
      new MixedFraction(1, 2, 3, 0);
    });

    assertEquals("denominator can't be 0", e.getMessage());
  }

  @Test
  void constructorInvalidSign()
  {
    final Throwable e = assertThrows(IllegalArgumentException.class, () -> {
      new MixedFraction(0, 2, 3, 4);
    });

    assertEquals("sign must be 1 or -1", e.getMessage());
  }

  @Test
  void constructorFromOther()
  {
    final MixedFraction mf1 = new MixedFraction(1, 2, 3, 4);
    final MixedFraction mf2 = new MixedFraction(mf1);

    assertMFs(mf1, mf2);
  }

  @Test
  void reduce()
  {
    final MixedFraction mf = new MixedFraction(-1, 1, 14, 6);
    mf.reduce();

    assertMFVars(mf, -1, 3, 1, 3);
  }

  @Test
  void reduceZero()
  {
    final MixedFraction mf = new MixedFraction(1, 0, 0, 3);
    mf.reduce();

    assertMFVars(mf, 1, 0, 0, 1);
  }

  @Test
  void reduceNumBecomesZero()
  {
    final MixedFraction mf = new MixedFraction(1, 2, 6, 3);
    mf.reduce();

    assertMFVars(mf, 1, 4, 0, 1);
  }

  @Test
  void reduceNegativeZero()
  {
    final MixedFraction mf = new MixedFraction(-1, 0, 0, 7);
    mf.reduce();

    assertMFVars(mf, 1, 0, 0, 1);
  }

  @Test
  void add()
  {
    final MixedFraction mf1 = new MixedFraction(1, 2, 2, 4);
    final MixedFraction mf2 = new MixedFraction(1, 1, 1, 4);
    final MixedFraction sum = MixedFraction.add(mf1, mf2);

    assertMFVars(sum, 1, 0, 60, 16);
  }

  @Test
  void addZero()
  {
    final MixedFraction mf1 = new MixedFraction(1, 0, 0, 3);
    final MixedFraction mf2 = new MixedFraction(1, 0, 0, 5);
    final MixedFraction sum = MixedFraction.add(mf1, mf2);

    assertMFVars(sum, 1, 0, 0, 5);
  }

  @Test
  void addNegative()
  {
    final MixedFraction mf1 = new MixedFraction(1, 0, 3, 4);
    final MixedFraction mf2 = new MixedFraction(-1, 5, 6, 7);
    final MixedFraction sum = MixedFraction.add(mf1, mf2);

    assertMFVars(sum, -1, 0, 143, 28);
  }

  @Test
  void sub()
  {
    final MixedFraction mf1 = new MixedFraction(1, 2, 3, 4);
    final MixedFraction mf2 = new MixedFraction(1, 1, 1, 4);
    final MixedFraction sum = MixedFraction.sub(mf1, mf2);

    assertMFVars(sum, 1, 0, 24, 16);
  }

  @Test
  void subZero()
  {
    final MixedFraction mf1 = new MixedFraction(1, 0, 0, 3);
    final MixedFraction mf2 = new MixedFraction(1, 0, 0, 5);
    final MixedFraction sum = MixedFraction.sub(mf1, mf2);

    assertMFVars(sum, 1, 0, 0, 3);
  }

  @Test
  void subNegative()
  {
    final MixedFraction mf1 = new MixedFraction(-1, 0, 3, 4);
    final MixedFraction mf2 = new MixedFraction(1, 5, 6, 7);
    final MixedFraction sum = MixedFraction.sub(mf1, mf2);

    assertMFVars(sum, -1, 0, 185, 28);
  }

  @Test
  void div()
  {
    final MixedFraction mf1 = new MixedFraction(1, 3, 1, 2);
    final MixedFraction mf2 = new MixedFraction(1, 1, 1, 2);
    final MixedFraction prod = MixedFraction.div(mf1, mf2);

    assertMFVars(prod, 1, 0, 14, 6);
  }

  @Test
  void divByZeroThrows()
  {
    final MixedFraction mf1 = new MixedFraction(1, 2, 3, 4);
    final MixedFraction mf2 = new MixedFraction(1, 0, 0, 1);

    assertThrows(ArithmeticException.class, () -> {
      MixedFraction.div(mf1, mf2);
    });
  }

  @Test
  void divWholeZeroNumNot()
  {
    final MixedFraction mf1 = new MixedFraction(1, 2, 3, 4);
    final MixedFraction mf2 = new MixedFraction(1, 0, 1, 1);
    final MixedFraction prod = MixedFraction.div(mf1, mf2);

    assertMFVars(prod, 1, 0, 11, 4);
  }

  @Test
  void divNumZeroWholeNot()
  {
    final MixedFraction mf1 = new MixedFraction(1, 2, 3, 4);
    final MixedFraction mf2 = new MixedFraction(1, 1, 0, 1);
    final MixedFraction prod = MixedFraction.div(mf1, mf2);

    assertMFVars(prod, 1, 0, 11, 4);
  }

  @Test
  void mult()
  {
    final MixedFraction mf1 = new MixedFraction(1, 1, 1, 2);
    final MixedFraction mf2 = new MixedFraction(1, 2, 1, 2);
    final MixedFraction prod = MixedFraction.mult(mf1, mf2);

    assertMFVars(prod, 1, 0, 15, 4);
  }

  @Test
  void toStringTest()
  {
    final MixedFraction mf1 = new MixedFraction(1, 2, 3, 4);

    assertEquals("2 3/4", mf1.toString());
  }

  @Test
  void compareLess()
  {
    MixedFraction mf = new MixedFraction(1, 1, 0, 1);
    MixedFraction other = new MixedFraction(1, 2, 0, 1);
    assertEquals(-1, mf.compareTo(other));
  }

  @Test
  void compareGreater()
  {
    MixedFraction mf = new MixedFraction(1, 3, 0, 1);
    MixedFraction other = new MixedFraction(-1, 5, 0, 1);
    assertEquals(1, mf.compareTo(other));
  }

  @Test
  void compareEqual()
  {
    MixedFraction mf = new MixedFraction(-1, 7, 8, 2);
    MixedFraction other = new MixedFraction(-1, 7, 16, 4);
    assertEquals(0, mf.compareTo(other));
  }
  
  @Test
  void mediantTest()
  {
    //needs coverage.
  }
  
  @Test
  void intPowerTest()
  {
    //Either test it or remove intPower if we aren't doing it.
  }
}
