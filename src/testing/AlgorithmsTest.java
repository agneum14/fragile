package testing;

import org.junit.jupiter.api.Test;
import utilities.Algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlgorithmsTest
{
  @Test
  void gcdIsOne()
  {
    assertEquals(1, Algorithms.gcd(3, 5));
  }

  @Test
  void gcdSimple()
  {
    assertEquals(3, Algorithms.gcd(6, 21));
  }

  @Test
  void lcmIsProduct()
  {
    assertEquals(21, Algorithms.lcm(3, 7));
  }

  @Test
  void lcmSimple()
  {
    assertEquals(24, Algorithms.lcm(8, 12));
  }
  
  
}
