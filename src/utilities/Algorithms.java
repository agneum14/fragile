package utilities;

/**
 * This class provides algorithms useful for calculations on mixed fractional numbers.
 *
 * This work complies with the JMU honor code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class Algorithms
{
  /**
   * Find the greatest common denominator of two whole numbers.
   *
   * @param a
   *     The first whole number
   * @param b
   *     The second whole number
   * @return The GCD of a and b
   */
  public static int gcd(final int a, final int b)
  {
    return b == 0 ? a : gcd(b, a % b);
  }

  /**
   * Find the least common multiple of two whole numbers.
   *
   * @param a
   *     The first whole number
   * @param b
   *     The second whole number
   * @return The LCM of a and b
   */
  public static int lcm(final int a, final int b)
  {
    int gcd, num;

    gcd = gcd(a, b);
    num = Math.abs(a * b);

    return num / gcd;
  }
}
