package gui;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import utilities.MapFormatter;
import utilities.ResourceManager;

public class ShortcutManager
{
  private static boolean exists = false;
  private static ShortcutManager instance;
  private static Map<String, String> map;

  /**
   * Constructor for the GUI's config.
   */
  private ShortcutManager()
  {
    try
    {
      map = MapFormatter.read("shortcuts.txt");
    }
    catch (FileNotFoundException | NoSuchElementException e)
    {
      map = new HashMap<>();
    }
  }

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
   * Loads default fragile if the config isn't present.
   */
  private void loadDefault()
  {

  }

  public void setKeybind(String str, Character character)
  {
    map.put(str, character.toString());
  }

  public void saveKeybinds()
  {
    try
    {
      MapFormatter.write(map, "shortcuts.txt");

    }
    catch (IOException e1)
    {
      e1.printStackTrace();
    }
  }

  public Map<String, String> getMap()
  {
    return new HashMap<String, String>(map);
  }

}
