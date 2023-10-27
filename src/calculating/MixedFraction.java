package calculating;

import utilities.Algorithms;

/**
 * This class represents a mixed fraction and provides methods for doing mixed fraction arithmetic.
 * In this implementation, the mixed fraction can only be negative as a whole, meaning the
 * individual components (e.g. the whole number, numerator, and denominator) can't be negative unto
 * themselves.
 *
 * This code complies with the JMU honor code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class MixedFraction
{
  private int denom;
  private int num;
  private int sign;
  private int whole;

  /**
   * This constructor sets all member variables explicity, then simplifies the mixed fraction.
   *
   * @param sign
   *     Indicates whether the number is negative (-1) or positive (1)
   * @param whole
   *     The whole number component of the mixed fraction
   * @param num
   *     The numerator of the mixed fraction
   * @param denom
   *     The denominator of the mixed fraction
   * @throws IllegalArgumentException
   *     if denom is 0 or sign isn't either 1 or -1
   */
  public MixedFraction(final int sign, final int whole, final int num, final int denom)
      throws IllegalArgumentException
  {
    if (denom == 0)
    {
      throw new IllegalArgumentException("denominator can't be 0");
    }
    if (sign != -1 && sign != 1)
    {
      throw new IllegalArgumentException("sign must be 1 or -1");
    }
    this.denom = denom;
    this.num = num;
    this.sign = sign;
    this.whole = whole;

    this.simplify();
  }

  /**
   * This constructor creates a new MixedFraction from the member variables of another MixedFraction
   * (a deep copy).
   *
   * @param o
   *     The Mixed Fraction to deep copy
   */
  public MixedFraction(final MixedFraction o)
  {
    this(o.sign, o.whole, o.num, o.denom);
  }

  /**
   * Add two MixedFractions.
   *
   * @param mf1
   *     The first MixedFraction to sum
   * @param mf2
   *     The second MixedFraction to sum
   * @return The sum of the two MixedFractions
   */
  public static MixedFraction add(final MixedFraction mf1, final MixedFraction mf2)
  {
    MixedFraction f1, f2;
    int lcm, num, num1, num2, sign;

    f1 = new MixedFraction(mf1).fractionalize();
    f2 = new MixedFraction(mf2).fractionalize();

    // scale the numerators to the least common denominator
    lcm = Algorithms.lcm(f1.denom, f2.denom);
    num1 = f1.getNum() * lcm / f1.getDenom();
    num2 = f2.getNum() * lcm / f2.getDenom();

    num = num1 * f1.getSign() + num2 * f2.getSign();
    if (num < 0)
    {
      sign = -1;
      num *= -1;
    }
    else
    {
      sign = 1;
    }

    return new MixedFraction(sign, 0, num, lcm);
  }

  /**
   * Divide two MixedFractions.
   *
   * @param mf1
   *     The MixedFraction dividend
   * @param mf2
   *     The MixedFraction divisor
   * @return The MixedFraction quotient
   * @throws ArithmeticException
   *     if the divisor is 0
   */
  public static MixedFraction div(final MixedFraction mf1, final MixedFraction mf2)
      throws ArithmeticException
  {
    MixedFraction f1, f2;
    int num, denom, sign;

    if (mf2.getWhole() == 0 && mf2.getNum() == 0)
    {
      throw new ArithmeticException("can't divide a MixedFraction by 0");
    }

    f1 = new MixedFraction(mf1).fractionalize();
    f2 = new MixedFraction(mf2).fractionalize();

    num = f1.getNum() * f2.getDenom();
    denom = f1.getDenom() * f2.getNum();
    sign = f1.getSign() * f2.getSign();

    return new MixedFraction(sign, 0, num, denom);
  }

  /**
   * Multiply two MixedFractions.
   *
   * @param mf1
   *     The first MixedFraction to multiply
   * @param mf2
   *     The second MixedFraction to multiply
   * @return The MixedFraction product
   */
  public static MixedFraction mult(final MixedFraction mf1, final MixedFraction mf2)
  {
    MixedFraction f1, f2;
    int num, denom, sign;

    f1 = new MixedFraction(mf1).fractionalize();
    f2 = new MixedFraction(mf2).fractionalize();

    num = f1.getNum() * f2.getNum();
    denom = f1.getDenom() * f2.getDenom();
    sign = f1.getSign() * f2.getSign();

    return new MixedFraction(sign, 0, num, denom);
  }

  /**
   * Subtract two MixedFractions.
   *
   * @param mf1
   *     The MixedFraction minuend
   * @param mf2
   *     The MixedFraction subtrahend
   * @return The MixedFraction result of the subtraction operation
   */
  public static MixedFraction sub(final MixedFraction mf1, final MixedFraction mf2)
  {
    MixedFraction f1, f2;
    int lcm, num, num1, num2, sign;

    f1 = new MixedFraction(mf1).fractionalize();
    f2 = new MixedFraction(mf2).fractionalize();

    // scale the numerators to the least common denominator
    lcm = Algorithms.lcm(f1.denom, f2.denom);
    num1 = f1.getNum() * lcm / f1.getDenom();
    num2 = f2.getNum() * lcm / f2.getDenom();

    num = num1 * f1.getSign() - num2 * f2.getSign();
    if (num < 0)
    {
      sign = -1;
      num *= -1;
    }
    else
    {
      sign = 1;
    }

    return new MixedFraction(sign, 0, num, lcm);
  }

  /**
   * Fractionalize the MixedFraction, meaning that whole becomes 0 and the numerator is top-heavy.
   *
   * @return The fractionalized MixedFraction
   */
  public MixedFraction fractionalize()
  {
    num += whole * denom;
    whole = 0;

    return this;
  }

  /**
   * Reduce this MixedFraction. If num is top-heavy, it's reduced and carried into whole. Also, if
   * num is 0 after this operation, denom becomes 1 (simplifying the arithmetic in this class).
   * Furthermore, if both whole and num are zero, meaning the entire MixedFraction is 0, then the
   * sign is changed to 1, so -0 is unrepresentable.
   */
  private void simplify()
  {
    int carry, gcd;

    carry = num / denom;
    whole += carry;
    num %= denom;

    if (num == 0)
    {
      if (whole == 0)
      { // eliminate -0
        sign = 1;
      }
      denom = 1;
    }
    else
    {
      gcd = Algorithms.gcd(num, denom);
      num /= gcd;
      denom /= gcd;
    }
  }

  /**
   * The getter for denom.
   *
   * @return denom
   */
  public int getDenom()
  {
    return denom;
  }

  /**
   * The getter for num.
   *
   * @return num
   */
  public int getNum()
  {
    return num;
  }

  /**
   * The getter for sign.
   *
   * @return sign
   */
  public int getSign()
  {
    return sign;
  }

  /**
   * The getter for whole.
   *
   * @return whole
   */
  public int getWhole()
  {
    return whole;
  }

  /**
   * Override toString to simply represent the MixedFraction as a String.
   *
   * @return The String representation of this MixedFraction
   */
  @Override
  public String toString()
  {
    String s;

    s = String.format("sign: %s, whole: %s, num: %s, denom: %s", sign, whole, num, denom);

    return s;
  }
}
