package utilities;

import java.util.Locale;

/**
 * This class grabs the language from the user's default locale, which is necessary since Fragile
 * now supports French and Japanese in addition to English. It also provides methods useful for
 * translation.
 *
 * This code complies with the JMU Honor Code
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class Language
{

  /**
   * The supported languages.
   */
  public static enum LocaleLanguage
  {
    ENGLISH, FRENCH, GERMAN;
  }

  private static final LocaleLanguage LANGUAGE = setLanguage();

  /**
   * Return either the English, French, or German string depending on the language from the user's
   * default locale. English is the default if the language isn't supported.
   *
   * @param english The English String
   * @param french The French String
   * @param japanese The Japanese String
   * @return The String of the corresponding language
   */
  public static String translate(final String english, final String french, final String german)
  {
    return switch (LANGUAGE)
    {
      case FRENCH -> french;
      case GERMAN -> german;
      default -> english;
    };
  }

  /**
   * Getter for language.
   *
   * @return language
   */
  public static LocaleLanguage getLanguage()
  {
    return LANGUAGE;
  }

  /**
   * This is used to set language to the language from the user's locale. It defaults to English if
   * the language isn't supported (i.e. it isn't English, French or Japanese)
   *
   * @return The language from the user's default locale
   */
  private static final LocaleLanguage setLanguage()
  {
    System.out.println(Locale.getDefault().getLanguage());
    return switch (Locale.getDefault().getLanguage())
    {
      case "fr" -> LocaleLanguage.FRENCH;
      case "de" -> LocaleLanguage.GERMAN;
      default -> LocaleLanguage.ENGLISH;
    };
  }
}
