package utilities;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class ResourceManager
{
  private static boolean exists = false;
  private static ResourceManager instance;
  private Path resourcePath;

  private ResourceManager() throws IOException, URISyntaxException
  {
    this.resourcePath = ResourceCopier.copyResourcesToTemp("temp", "../html");
  }

  public static ResourceManager newInstance() throws IOException, URISyntaxException
  {
    if (!exists)
    {
      instance = new ResourceManager();
    }
    return instance;
  }

  public Path getResourcePath()
  {
    return resourcePath;
  }
}
