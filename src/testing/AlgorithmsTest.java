package testing;

import org.junit.jupiter.api.Test;
import utilities.Algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JUnit test class for the Algorithms.
 */
public class AlgorithmsTest
{
  /**
   * Testing if the gcd is one.
   */
  @Test
  void gcdIsOne()
  {
    assertEquals(1, Algorithms.gcd(3, 5));
  }

  /**
   * Testing if the gcd is simplified.
   */
  @Test
  void gcdSimple()
  {
    assertEquals(3, Algorithms.gcd(6, 21));
  }

  /**
   * Testing the lcm algorithm.
   */
  @Test
  void lcmIsProduct()
  {
    assertEquals(21, Algorithms.lcm(3, 7));
  }

  /**
   * Testing if the lcm is simplified.
   */
  @Test
  void lcmSimple()
  {
    assertEquals(24, Algorithms.lcm(8, 12));
  }

  /**
   * Test specifically for the tester.
   */
  @Test
  void constructorTest()
  {
    Algorithms al = new Algorithms();
  }

}
