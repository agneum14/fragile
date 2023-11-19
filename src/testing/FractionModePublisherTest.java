package testing;

import calculating.FractionModePublisher;
import calculating.FractionModeSubscriber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FractionModePublisherTest
{
  private class DummySubscriber implements FractionModeSubscriber
  {
    public boolean proper;
    public boolean reduced;

    public DummySubscriber()
    {
      proper = false;
      reduced = false;
    }

    @Override
    public void handleProperMode(final boolean properMode)
    {
      proper = properMode;
    }

    @Override
    public void handleReducedMode(final boolean reducedMode)
    {
      reduced = reducedMode;
    }
  }

  @Test
  void notifyProperMode()
  {
    FractionModePublisher fmp = FractionModePublisher.getInstance();

    fmp.notifyProperMode(true);

    assertEquals(true, fmp.getProper());
  }

  @Test
  void notifyProperModeWithSubscriber()
  {
    FractionModePublisher fmp = FractionModePublisher.getInstance();
    DummySubscriber ds = new DummySubscriber();

    fmp.addSubscriber(ds);
    fmp.notifyProperMode(true);

    assertEquals(true, ds.proper);
  }

  @Test
  void notifyReducedMode()
  {
    FractionModePublisher fmp = FractionModePublisher.getInstance();

    fmp.notifyReducedMode(true);

    assertEquals(true, fmp.getReduced());
  }

  @Test
  void notifyReducedModeWithSubscriber()
  {
    FractionModePublisher fmp = FractionModePublisher.getInstance();
    DummySubscriber ds = new DummySubscriber();

    fmp.addSubscriber(ds);
    fmp.notifyReducedMode(true);

    assertEquals(true, ds.reduced);
  }

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
