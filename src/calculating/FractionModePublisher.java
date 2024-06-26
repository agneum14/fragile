package calculating;

import java.util.ArrayList;
import java.util.List;

/**
 * This class notifies the FractionModeSubscribers in the list of subscribers the proper or reduced
 * modes for mixed fractions have changed, so these modes can be changed on-the-fly. If proper is
 * true, all mixed fractions are displayed in proper form, improper otherwise. If reduced is true,
 * all mixed fractions are displayed in reduced form, irreduced otherwise.
 *
 * This code complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class FractionModePublisher
{

  private static FractionModePublisher instance;

  private boolean proper;
  private boolean reduced;
  private final List<FractionModeSubscriber> subscribers;

  /**
   * This constructor initializes the proper and reduced modes to false and subscribers to an empty
   * ArrayList.
   */
  private FractionModePublisher()
  {
    proper = false;
    reduced = false;
    subscribers = new ArrayList<>();
  }

  /**
   * Singleton which returns the instance of the FractionModePublisher if there is already an
   * instance, else it makes a new FractionModePublisher.
   * 
   * @return FractionModePublisher the publisher.
   */
  public static synchronized FractionModePublisher getInstance()
  {
    if (instance == null)
    {
      instance = new FractionModePublisher();
    }

    return instance;
  }

  /**
   * This method sets the proper mode and notifies subscribers the proper mode has changed.
   *
   * @param properMode
   *          The new proper mode
   */
  public void notifyProperMode(final boolean properMode)
  {
    proper = properMode;

    for (FractionModeSubscriber subscriber : subscribers)
    {
      subscriber.handleProperMode(proper);
    }
  }

  /**
   * This method sets the reduced mode and notifies subscribers the reduced mode has changed.
   *
   * @param reducedMode
   *          The new proper mode
   */
  public void notifyReducedMode(final boolean reducedMode)
  {
    reduced = reducedMode;

    for (FractionModeSubscriber subscriber : subscribers)
    {
      subscriber.handleReducedMode(reduced);
    }
  }

  /**
   * Add a FractionModeSubscriber to subscribers.
   *
   * @param subscriber
   *          The F;actionModeSubscriber to add
   */
  public void addSubscriber(final FractionModeSubscriber subscriber)
  {
    subscribers.add(subscriber);
  }

  /**
   * Remove a FractionModeSubscriber from subscribers.
   *
   * @param subscriber
   *          The FractionModeSubscriber to remove
   */
  public void removeSubscriber(final FractionModeSubscriber subscriber)
  {
    subscribers.remove(subscriber);
  }

  /**
   * Getter for proper.
   *
   * @return proper the boolean for if its supposed to be proper.
   */
  public boolean getProper()
  {
    return proper;
  }

  /**
   * Getter for reduced.
   *
   * @return reduced the boolean for it its supposed to be Reduced.
   */
  public boolean getReduced()
  {
    return reduced;
  }
}
