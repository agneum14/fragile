package calculating;

/**
 * This interface subscribes to the FractionModePublisher to handle changes in the proper and
 * reduced modes. This allows for changing the modes of mixed fractions on-the-fly.
 *
 * This code complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public interface FractionModeSubscriber
{

  /**
   * This method handles a change in the proper mode.
   *
   * @param proper
   *     The new proper mode
   */
  public void handleProperMode(boolean proper);

  /**
   * This method handles a change in the reduced mode.
   *
   * @param reduced
   *     The new reduced mode
   */
  public void handleReducedMode(boolean reduced);
}
