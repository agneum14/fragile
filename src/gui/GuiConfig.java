package gui;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import utilities.MapFormatter;

public class GuiConfig
{
  
  private Map<String, String> map;
  
  /**
   * Constructor for the GUI's config.
   */
  public GuiConfig() 
  {
    try 
    {
      map = MapFormatter.read("/html/config.txt");
    }catch(FileNotFoundException e) 
    {
      loadDefault();
    }
  }
  
  /**
   * Loads default fragile if the config isn't present.
   */
  private void loadDefault()
  {
    map = new HashMap<String, String>();
    map.put("logo", "/html/Fragile_Logo.png");
    map.put("red", "210");
    map.put("green", "237");
    map.put("blue", "255");
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
