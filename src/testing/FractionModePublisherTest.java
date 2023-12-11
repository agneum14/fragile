package testing;

import calculating.FractionModePublisher;
import calculating.FractionModeSubscriber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * class for the FractionNodePublisher class.
 */
public class FractionModePublisherTest
{
  /**
   * Inner class for a dummy subscriber implementing FractionNodeSubscriber.
   */
  private class DummySubscriber implements FractionModeSubscriber
  {
    private boolean proper;
    private boolean reduced;

    /**
     * The constructor for the dummy subscriber.
     */
    public DummySubscriber()
    {
      proper = false;
      reduced = false;
    }

    /**
     * Returns boolean for the proper mode.
     */
    @Override
    public void handleProperMode(final boolean properMode)
    {
      proper = properMode;
    }

    /**
     * Returns boolean for the reduced mode.
     */
    @Override
    public void handleReducedMode(final boolean reducedMode)
    {
      reduced = reducedMode;
    }
  }

  /**
   * Test method for notifying proper mode.
   */
  @Test
  void notifyProperMode()
  {
    FractionModePublisher fmp = FractionModePublisher.getInstance();

    fmp.notifyProperMode(true);

    assertEquals(true, fmp.getProper());
  }

  /**
   * Test method for notifying proper mode with a subscriber.
   */
  @Test
  void notifyProperModeWithSubscriber()
  {
    FractionModePublisher fmp = FractionModePublisher.getInstance();
    DummySubscriber ds = new DummySubscriber();

    fmp.addSubscriber(ds);
    fmp.notifyProperMode(true);

    assertEquals(true, ds.proper);
  }

  /**
   * Test method for notifying reduced mode.
   */
  @Test
  void notifyReducedMode()
  {
    FractionModePublisher fmp = FractionModePublisher.getInstance();

    fmp.notifyReducedMode(true);

    assertEquals(true, fmp.getReduced());
  }

  /**
   * Test method for notifying reduced mode with a subscriber.
   */
  @Test
  void notifyReducedModeWithSubscriber()
  {
    FractionModePublisher fmp = FractionModePublisher.getInstance();
    DummySubscriber ds = new DummySubscriber();

    fmp.addSubscriber(ds);
    fmp.notifyReducedMode(true);

    assertEquals(true, ds.reduced);
  }

  /**
   * Test method for removing a subscriber.
   */
  @Test
  void removeSubscriber()
  {
    FractionModePublisher fmp = FractionModePublisher.getInstance();
    DummySubscriber ds = new DummySubscriber();

    fmp.addSubscriber(ds);
    fmp.removeSubscriber(ds);
    fmp.notifyProperMode(true);

    assertEquals(false, ds.proper);
  }
}
