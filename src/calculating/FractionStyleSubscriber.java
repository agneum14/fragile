package calculating;

import calculating.FractionStylePublisher.FractionStyle;

/**
 * This interface listens for changes in the mixed fraction style from the FractionStylePublisher.
 * MixedFractionPanels implement this interface to update their display on the fly.
 *
 * This code complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public interface FractionStyleSubscriber
{

  /**
   * Handle a new mixed fraction style.
   *
   * @param style
   *          The new mixed fraction style
   */
  public void handleStyle(final FractionStyle style);

  /**
   * Handle a new separated preference.
   * 
   * @param separated
   *          The separated preference
   */
  public void handleSeparated(final boolean separated);
}
