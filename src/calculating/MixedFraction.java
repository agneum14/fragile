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
public class MixedFraction implements Comparable<MixedFraction>, ExpressionElement
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
  public MixedFraction(final int sign, Integer whole, Integer num, Integer denom)
      throws IllegalArgumentException
  {
    // replace nulls with default values
    if (whole == null)
    {
      whole = 0;
    }
    if (num == null)
    {
      num = 0;
    }
    if (denom == null)
    {
      denom = 1;
    }

    // throw exceptions for zero denominator or invalid sign
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

    // make -0 unrepresentable
    if (whole == 0 && num == 0)
    {
      this.sign = 1;
    }
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
   * This constructor creates a CurrentMixedFraction from the member variables of a
   * CurrentMixedFraction.
   *
   * @param cmf
   *     The CurrentMixedFraction to convert to a MixedFraction
   */
  public MixedFraction(final CurrentMixedFraction cmf)
  {
    this(cmf.getSign(), cmf.getWhole(), cmf.getNum(), cmf.getDenom());
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
    int sign, num, denom;

    f1 = new MixedFraction(mf1).improper();
    f2 = new MixedFraction(mf2).improper();

    // early return for adding 0
    if (f1.getNum() == 0)
    {
      return f2;
    }
    else if (f2.getNum() == 0)
    {
      return f1;
    }

    num = f1.getNum() * f1.getSign() * f2.getDenom() + f2.getNum() * f2.getSign() * f1.getDenom();
    if (num < 0)
    {
      num *= -1;
      sign = -1;
    }
    else
    {
      sign = 1;
    }
    denom = f1.getDenom() * f2.getDenom();

    return new MixedFraction(sign, 0, num, denom);
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

    f1 = new MixedFraction(mf1).improper();
    f2 = new MixedFraction(mf2).improper();

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

    f1 = new MixedFraction(mf1).improper();
    f2 = new MixedFraction(mf2).improper();

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
    int sign, num, denom;

    f1 = new MixedFraction(mf1).improper();
    f2 = new MixedFraction(mf2).improper();

    // early return for subtracting 0
    if (f2.getNum() == 0)
    {
      return f1;
    }
    else if (f1.getNum() == 0)
    {
      return new MixedFraction(f2.getSign() * -1, 0, f2.getNum(), f2.getDenom());
    }

    num = f1.getNum() * f1.getSign() * f2.getDenom() - f2.getNum() * f2.getSign() * f1.getDenom();
    if (num < 0)
    {
      num *= -1;
      sign = -1;
    }
    else
    {
      sign = 1;
    }
    denom = f1.getDenom() * f2.getDenom();

    return new MixedFraction(sign, 0, num, denom);
  }

  /**
   * Calculate the mediant of two mixed fractions.
   *
   * @param mf1
   *     The first mixed fraction
   * @param mf2
   *     The second mixed fraction
   * @return A new mixed fraction.
   */
  public static MixedFraction mediant(final MixedFraction mf1, final MixedFraction mf2)
  {
    int sign, numerator, denominator;
    MixedFraction f1, f2;
    f1 = new MixedFraction(mf1).improper();
    f2 = new MixedFraction(mf2).improper();

    numerator = f1.getNum() * f1.getSign() + f2.getNum() * f2.getSign();
    denominator = f1.getDenom() + f2.getDenom();
    if (numerator < 0)
    {
      numerator *= -1;
      sign = -1;
    }
    else
    {
      sign = 1;
    }
    return new MixedFraction(sign, 0, numerator, denominator);

  }

  /**
   * Calculate a mixed fraction to an Integer Power.
   *
   * @param mf
   *     the fraction to calculated.
   * @param power
   *     The power to put the mixed fraction to
   * @return a new mixed fraction
   */
  public static MixedFraction intPower(final MixedFraction mf, final int power)
  {

    final MixedFraction f = new MixedFraction(mf).improper();

    if (power == 0)
    {
      return new MixedFraction(1, 0, 1, 1);
    }

    int sign = 1;
    final int numerator = (int) Math.pow(f.getNum(), Math.abs(power));
    final int denominator = (int) Math.pow(f.getDenom(), Math.abs(power));

    if (f.getSign() == -1 && power % 2 == 1)
    {
      sign = -1;
    }

    if (power > 0)
    {
      return new MixedFraction(sign, 0, numerator, denominator);
    }
    else
    {
      return new MixedFraction(sign, 0, denominator, numerator);
    }
  }

  /**
   * Method for the less than operator.
   *
   * @param mf1
   *     the mixed fraction that should be greater than.
   * @param mf2
   *     the mixed fraction that should be less than.
   * @return true or false depending on if mf1 is greater than mf2.
   */
  public static boolean GreaterThan(final MixedFraction mf1, final MixedFraction mf2)
  {
    final MixedFraction frac = MixedFraction.sub(mf1, mf2);
    return frac.sign == 1 ? true : false;
  }

  public static boolean LessThan(final MixedFraction mf1, final MixedFraction mf2)
  {
    final MixedFraction frac = MixedFraction.sub(mf1, mf2);
    return frac.sign == -1 ? true : false;
  }

  public static boolean EqualTo(final MixedFraction mf1, final MixedFraction mf2)
  {

    return (mf1.whole == mf2.whole && mf1.num == mf2.num && mf1.denom == mf2.denom && mf1.sign == mf2.sign) ?
        true :
        false;
  }

  /**
   * Fractionalize the MixedFraction, meaning that whole becomes 0 and the numerator is top-heavy.
   *
   * @return The fractionalized MixedFraction
   */
  public MixedFraction improper()
  {
    num += whole * denom;
    whole = 0;

    return this;
  }

  /**
   * Make this MixedFraction proper, meaning the numerator is less than the denominator with the
   * excess in the whole component.
   *
   * @return The proper MixedFraction
   */
  public MixedFraction proper()
  {
    int carry;

    carry = num / denom;
    whole += carry;
    num %= denom;

    return this;
  }

  /**
   * Reduce this MixedFraction. A reduced MixedFraction is proper and the numerator and denominator
   * are in the lowest terms.
   *
   * @return The reduced MixedFraction
   */
  public MixedFraction reduce()
  {
    int gcd;

    proper();

    gcd = Algorithms.gcd(num, denom);
    num /= gcd;
    denom /= gcd;

    return this;
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

  /**
   * Implement Comparable to compare mixed fractions.
   *
   * @param other
   *     The MixedFraction to compare to
   * @return -1 if this MixedFraction is less than other, 0 if equal, and 1 if greater than
   */
  @Override
  public int compareTo(final MixedFraction other)
  {
    final MixedFraction f1 = new MixedFraction(this);
    final MixedFraction f2 = new MixedFraction(other);

    final MixedFraction res = MixedFraction.sub(f1, f2);
    if (res.getSign() == -1)
    {
      return -1;
    }
    else if (res.getWhole() == 0 && res.getNum() == 0)
    {
      return 0;
    }
    else
    {
      return 1;
    }
  }
}
