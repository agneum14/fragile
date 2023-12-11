package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The MapFormatter class provides static methods to read and write key-value pairs from/to a file.
 * It is designed to work with Map objects.
 * 
 * @author Ray
 * 
 * @author Asa Gittman
 * 
 * @version 12/11/2023
 */
public class MapFormatter
{
  /**
   * Reads keyvalue pairs from a file and returns them as a Map.
   *
   * @param path
   *          The path to the file containing the keyvalue pairs.
   * @return A Map containing the read keyvalue pairs.
   * @throws FileNotFoundException
   *           If the specified file is not found.
   */
  public static Map<String, String> read(final String path) throws FileNotFoundException
  {
    File f = new File(path);
    if (!f.exists())
    {
      throw new FileNotFoundException();
    }
    Scanner s = new Scanner(f);
    HashMap<String, String> map = new HashMap<>();

    s.useDelimiter("[;\\n]");
    while (s.hasNext())
    {
      String key = s.next();
      if (!s.hasNext())
      {
        break;
      }
      String val = s.next();
      map.put(key, val);
    }
    s.close();
    return map;
  }

  /**
   * Writes keyvalue pairs from a Map to a file.
   *
   * @param map
   *          The `Map` containing keyvalue pairs to be written.
   * @param path
   *          The path to the file where the keyvalue pairs will be written.
   * @param <K>
   *          The type of keys in the Map.
   * @param <V>
   *          The type of values in the Map.
   * @throws IOException
   *           If an I/O error occurs while writing to the file.
   */
  public static <K extends Object, V extends Object> void write(final Map<K, V> map,
      final String path) throws IOException
  {
    FileWriter f = new FileWriter(path);
    for (Map.Entry<K, V> entry : map.entrySet())
    {
      K key = entry.getKey();
      V val = entry.getValue();
      if (key == null || val == null)
      {
        continue;
      }
      f.write(key.toString());
      f.write(";");
      f.write(val.toString());
      f.write("\n");
    }
    f.close();
  }
}
