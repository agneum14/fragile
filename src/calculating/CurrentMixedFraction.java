package calculating;

import utilities.Algorithms;

/**
 * This class represents the data of the current mixed fraction and provides functionality for
 * manipulating it.
 *
 * This code complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class CurrentMixedFraction
{
  private int sign;
  private Integer whole;
  private Integer num;
  private Integer denom;
  private Pos pos;

  /**
   * This constructor sets sign to 1, whole, num, and denom to null, and pos to WHOLE.
   */
  public CurrentMixedFraction()
  {
    clear();
  }

  /**
   * Construct the current mixed fraction from the components of a mixed fraction.
   *
   * @param mf
   *     The mixed fraction
   */
  public CurrentMixedFraction(final MixedFraction mf)
  {
    sign = mf.getSign();
    whole = mf.getWhole();
    num = mf.getNum();
    denom = mf.getDenom();
    pos = Pos.WHOLE;
  }

  /**
   * Add a digit to the either whole, num, or denom (depending on pos).
   *
   * @param d
   *     The digit to add
   * @throws IllegalArgumentException
   *     if d is greater than 9 or less than 0
   */
  public void addDigit(final int d) throws IllegalArgumentException
  {
    Integer target;

    if (d > 9)
    {
      throw new IllegalArgumentException("d can't exceed 9");
    }
    if (d < 0)
    {
      throw new IllegalArgumentException("d can't be less than 0");
    }

    if (pos == Pos.WHOLE)
      target = whole;
    else if (pos == Pos.NUM)
      target = num;
    else
      target = denom;

    if (target == null)
    {
      target = d;
    }
    else
    {
      target *= 10;
      target += d;
    }

    // using two of these if blocks because Java lacks pointers
    if (pos == Pos.WHOLE)
      whole = target;
    else if (pos == Pos.NUM)
      num = target;
    else
      denom = target;
  }

  /**
   * Change sign from 1 to -1 or vice versa.
   */
  public void changeSign()
  {
    sign *= -1;
  }

  /**
   * Reset the state of the curent mixed fraction. Sign is set to 1, whole, num, and denom are set
   * to null, and pos is set to WHOLE
   */
  public void clear()
  {
    sign = 1;
    whole = null;
    num = null;
    denom = null;
    pos = Pos.WHOLE;
  }

  /**
   * Advance the current position, going from WHOLE to NUM, NUM to DENOM, and DENOM back to WHOLE.
   */
  public void nextPos()
  {
    pos = pos.next();
  }

  /**
   * Return to the previous position, from DENOM to NUM, NUM to WHOLE, and WHOLE back to DENOM.
   */
  public void prevPos()
  {
    pos = pos.prev();
  }

  /**
   * Remove a digit from either whole, num, or denom (depending on pos).
   */
  public void removeDigit()
  {
    Integer target;

    if (pos == Pos.WHOLE)
      target = whole;
    else if (pos == Pos.NUM)
      target = num;
    else
      target = denom;

    if (target == null)
    { // no digits
      prevPos();
      return;
    }
    if (target < 10)
    { // one digit
      target = null;
    }
    else
    {
      target /= 10;
    }

    if (pos == Pos.WHOLE)
      whole = target;
    else if (pos == Pos.NUM)
      num = target;
    else
      denom = target;
  }

  /**
   * Method for when the user presses the simplify button. Simplifies the CurrentMixedFraction's
   * numerator and denominator.
   */
  public void simplify() throws IllegalArgumentException
  {
    if (num == null || denom == null)
    {
      throw new IllegalArgumentException(
          "cannot simplify because numerator or denominator is not entered");
    }
    final int gcf = Algorithms.gcd(num, denom);
    if (gcf == 1) // Simplified
    {
      return;
    }
    num /= gcf;
    denom /= gcf;
  }

  /**
   * Invert the current mixed fraction (e.g. fractionalize then swap the numerator and
   * denominator).
   */
  public void invert()
  {
    // swapping numerator and denominator.
    if (whole == null)
    {
      final int tempNumerator = num;
      num = denom;
      denom = tempNumerator;
    }
    else
    {
      num += whole * denom;
      final int tempNumerator = num;
      num = denom;
      denom = tempNumerator;
      whole = 0;
    }
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
  public Integer getWhole()
  {
    return whole;
  }

  /**
   * The getter for num.
   *
   * @return num
   */
  public Integer getNum()
  {
    return num;
  }

  /**
   * The getter for denom.
   *
   * @return denom
   */
  public Integer getDenom()
  {
    return denom;
  }

  /**
   * The getter for pos.
   *
   * @return pos
   */
  public Pos getPos()
  {
    return pos;
  }

  /**
   * The current position of the current mixed fraction.
   */
  public enum Pos
  {
    WHOLE
        {
          /**
           * Override prev for WHOLE to return the last position.
           *
           * @return The last position (DENOM)
           */
          @Override
          public Pos prev()
          {
            return values()[2];
          }
        }, NUM, DENOM
      {
        /**
         * Override next for DENOM to return the first position.
         *
         * @return The first position (WHOLE)
         */
        @Override
        public Pos next()
        {
          return values()[0];
        }
      };

    /**
     * Find the next position.
     *
     * @return The next position
     */
    public Pos next()
    {
      return values()[ordinal() + 1];
    }

    /**
     * Find the previous position.
     *
     * @return The previous position
     */
    public Pos prev()
    {
      return values()[ordinal() - 1];
    }
  }
}
