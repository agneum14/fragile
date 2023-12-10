package utilities;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class ResourceManager
{
  private static boolean hasCopied = false;
  private static Path resourcePath;


  public static boolean copyFiles() throws IOException, URISyntaxException
  {
    if (!hasCopied)
    {
      hasCopied = true;
      Path path = ResourceCopier.copyResourcesToTemp("temp", "../html");
      resourcePath = path;
      return true;
    }
    return false;
  }

  public static Path getResourcePath()
  {
    return resourcePath;
  }
}
