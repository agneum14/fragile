package calculating;

import java.util.ArrayList;
import java.util.List;

/**
 * This class notifies the FractionStyleSubscribers the mixed fraction style has changed. The
 * currently supported styles are bar, solidus, and slash, all of which are detailed in the Domain
 * Glossary.
 *
 * This code complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class FractionStylePublisher
{

  /**
   * The mixed fraction styles.
   */
  public enum FractionStyle
  {
    BAR, SOLIDUS, SLASH
  }

  private final List<FractionStyleSubscriber> subscribers;
  private FractionStyle style;

  /**
   * This constructor initializes subscribers to an empty ArrayList and sets style to BAR.
   */
  public FractionStylePublisher()
  {
    subscribers = new ArrayList<>();
    style = FractionStyle.BAR;
  }

  /**
   * Add a FractionStyleSubscriber to the subscribers.
   *
   * @param subscriber
   *     The FractionStyleSubscriber to add
   */
  public void addSubscriber(final FractionStyleSubscriber subscriber)
  {
    subscribers.add(subscriber);
  }

  /**
   * Remove a FractionStyleSubscriber from the subscribers.
   *
   * @param subscriber
   *     The FractionStyleSubscriber to remove
   */
  public void removeSubscriber(final FractionStyleSubscriber subscriber)
  {
    subscribers.remove(subscriber);
  }

  /**
   * Remove all FractionModeSubscribers.
   */
  public void removeAllSubscribers() {
      subscribers.removeAll(subscribers);
  }

  /**
   * Notify all the subscribers the mixed fraction style has changed.
   *
   * @param fractionStyle
   *     The updated mixed fraction style
   */
  public void notifyStyle(final FractionStyle fractionStyle)
  {
    this.style = fractionStyle;

    for (FractionStyleSubscriber subscriber : subscribers)
    {
      subscriber.handleStyle(fractionStyle);
    }
  }

  /**
   * Getter for style.
   *
   * @return style
   */
  public FractionStyle getStyle()
  {
    return style;
  }
}
