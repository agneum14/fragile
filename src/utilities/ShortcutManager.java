package utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * The ShortcutManager class is responsible for managing keyboard shortcuts in the application. It
 * follows the Singleton pattern to ensure a single instance is used across the application.
 * 
 * @author Joshua Hairston
 * 
 * @version 12/11/2023
 */
public class ShortcutManager
{
  private static final String SHORTCUTS = "shortcuts.txt";
  private static boolean exists = false;
  private static ShortcutManager instance;
  private static Map<String, String> map;

  /**
   * Initializes the map by reading keyboard shortcuts from a file. If the file is not found or
   * empty, initializes an empty map.
   */
  private ShortcutManager()
  {
    try
    {
      map = MapFormatter.read(SHORTCUTS);
    }
    catch (FileNotFoundException | NoSuchElementException e)
    {
      map = new HashMap<>();
    }
  }

  /**
   * Creates a new instance of ShortcutManager if one does not already exist, or returns the
   * existing instance.
   *
   * @return The singleton instance of ShortcutManager.
   */
  public static ShortcutManager newInstance()
  {
    if (!exists)
    {
      instance = new ShortcutManager();
      exists = true;
    }
    return instance;
  }

  /**
   * Sets a keyboard shortcut for a specific action.
   *
   * @param character
   *          The character in which the shortcut is set.
   * @param str
   *          the menuItems name.
   * 
   */
  public void setKeybind(final String str, final Character character)
  {
    map.put(str, character.toString());
    saveKeybinds();
  }

  /**
   * Saves the current keyboard shortcuts to a file.
   *
   * @throws IOException
   *           If an I/O error occurs.
   */
  private void saveKeybinds()
  {
    try
    {
      MapFormatter.write(map, SHORTCUTS);

    }
    catch (IOException e1)
    {
      e1.printStackTrace();
    }
  }

  /**
   * Retrieves a copy of the current keyboard shortcuts map.
   *
   * @return A new HashMap containing the keyboard shortcuts.
   */
  public Map<String, String> getMap()
  {
    return new HashMap<String, String>(map);
  }

}
