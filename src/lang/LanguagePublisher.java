package lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class notifies the LanguageSubscribers the language has changed. English, French, and
 * Japanese are supported.
 *
 * This code complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class LanguagePublisher
{

  private final List<LanguageSubscriber> subs;
  private final Lang localeLang;

  /**
   * Initialize subs, the List of LanguageSubscribers, to an empty ArrayList. Also set localeLang to
   * the language of the user's default locale, defaulting to English if the language isn't
   * supported
   */
  public LanguagePublisher()
  {
    subs = new ArrayList<>();

    localeLang = switch (Locale.getDefault().getLanguage())
    {
      case "fr" -> Lang.FR;
      case "ja" -> Lang.JA;
      default -> Lang.EN;
    };
  }

  /**
   * Add a LanguageSubscriber to subs.
   *
   * @param ls
   *     The LanguageSubscriber to add
   */
  public void add(final LanguageSubscriber ls)
  {
    subs.add(ls);
  }

  /**
   * Notify all LanguageSubscribers in subs the language has changed.
   *
   * @param lang
   *     The language
   */
  public void notify(final Lang lang)
  {
    for (LanguageSubscriber sub : subs)
    {
      sub.updateLang(lang);
    }
  }

  /**
   * Notify all LanguageSubscribers in subs the language has changed to the language of the user's
   * default locale.
   */
  public void notifyLocale()
  {
    notify(localeLang);
  }

  /**
   * The supported languages: EN for English, FR for French, and JA for Japanese.
   */
  public enum Lang
  {
    EN, FR, JA
  }
}
