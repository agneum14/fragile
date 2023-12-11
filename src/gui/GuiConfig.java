package gui;

import utilities.MapFormatter;
import utilities.ResourceManager;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Config Class for Fragile.
 */
public class GuiConfig
{
  private static boolean exists = false;
  private static GuiConfig instance;
  private Map<String, String> map;

  /**
   * Constructor for the GUI's config.
   */
  private GuiConfig()
  {
    try
    {
      map = MapFormatter.read("config.txt");
    } catch (FileNotFoundException | NoSuchElementException e)
    {
      loadDefault();
    }
  }

  public static GuiConfig newInstance()
  {
    if (!exists)
    {
      instance = new GuiConfig();
      exists = true;
    }
    return instance;
  }


  /**
   * Loads default fragile if the config isn't present.
   */
  private void loadDefault()
  {
    map = new HashMap<String, String>();
    try
    {
      ResourceManager rm = ResourceManager.newInstance();
      Path logoPath = Paths.get(rm.getResourcePath().toString(), "Fragile_Logo.png");
      map.put("logo", logoPath.toString());
    } catch (Exception e)
    {
      map.put("logo", "/html/Fragile_Logo.png");
    }
    map.put("red", "210");
    map.put("green", "237");
    map.put("blue", "255");
    try
    {
      MapFormatter.write(map, "config.txt");
    } catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * Getter for the color in the config.
   *
   * @return The color based on the stored RGB values in map
   */
  public Color getColor()
  {
    Integer red = Integer.parseInt(map.get("red"));
    Integer green = Integer.parseInt(map.get("green"));
    Integer blue = Integer.parseInt("blue");
    return new Color(red, green, blue);
  }

  /**
   * Getter for the Logo path.
   *
   * @return The path string
   */
  public String getLogoPath()
  {
    return map.get("logo");
  }
}
