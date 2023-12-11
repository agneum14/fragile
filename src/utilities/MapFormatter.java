package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MapFormatter
{
  public static Map<String, String> read(String path) throws FileNotFoundException
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

  public static <K extends Object, V extends Object> void write(Map<K, V> map,
                                                                String path) throws IOException
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
      f.write(":");
      f.write(val.toString());
      f.write("\n");
    }
    f.close();
  }
}
