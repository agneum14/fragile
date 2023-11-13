package calculating;

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
   * Add a digit to the either whole, num, or denom (depending on pos).
   *
   * @param d
   *          The digit to add
   * @throws IllegalArgumentException
   *           if d is greater than 9 or less than 0
   */
  public void addDigit(int d) throws IllegalArgumentException
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

    target = switch (pos)
    {
      case WHOLE -> whole;
      case NUM -> num;
      case DENOM -> denom;
    };

    if (target == null)
    {
      target = d;
    }
    else
    {
      target *= 10;
      target += d;
    }

    // using two switch statements because Java lacks pointers
    switch (pos)
    {
      case WHOLE -> whole = target;
      case NUM -> num = target;
      case DENOM -> denom = target;
    }
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
   * Advance pos, going from WHOLE to NUM, NUM to DENOM, and DENOM back to WHOLE.
   */
  public void nextPos()
  {
    pos = pos.next();
  }

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

    target = switch (pos)
    {
      case WHOLE -> whole;
      case NUM -> num;
      case DENOM -> denom;
      default -> 0; // impossible
    };

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

    switch (pos)
    {
      case WHOLE -> whole = target;
      case NUM -> num = target;
      case DENOM -> denom = target;
      default -> whole = 0; // impossible
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

  public enum Pos
  {
    WHOLE
    {
      @Override
      public Pos prev()
      {
        return values()[2];
      }
    },
    NUM, DENOM
    {
      @Override
      public Pos next()
      {
        return values()[0];
      }
    };

    public Pos next()
    {
      return values()[ordinal() + 1];
    }

    public Pos prev()
    {
      return values()[ordinal() - 1];
    }
  }
}
