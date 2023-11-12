package lang;

import lang.LanguagePublisher.Lang;

/**
 * LanguagePublishers are notified when the LanguagePublisher updates the language. GUI components
 * involving language implement this to change language on the fly.
 *
 * This code complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public interface LanguageSubscriber
{

  /**
   * Update the language of the LanguageSubscriber.
   *
   * @param lang
   *     The updated language
   */
  public void updateLang(final Lang lang);
}
