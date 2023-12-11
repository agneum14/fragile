package utilities;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

/**
 * The ResourceManager class ensures that the resources are copied to a temporary location during
 * instantiation.
 */
public class ResourceManager
{
  private static boolean exists = false;
  private static ResourceManager instance;
  private Path resourcePath;

  /**
   * Private constructor for ResourceManager. Copies resources to a temporary location during
   * instantiation.
   *
   * @throws IOException
   *           If an I/O error occurs.
   * @throws URISyntaxException
   *           If a string could not be parsed.
   */
  private ResourceManager() throws IOException, URISyntaxException
  {
    this.resourcePath = ResourceCopier.copyResourcesToTemp("temp", "../html");
  }

  /**
   * Creates a new instance of ResourceManager if it does not already exist.
   *
   * @return The instance of ResourceManager.
   * @throws IOException
   *           If an I/O error occurs.
   * @throws URISyntaxException
   *           If a string could not be parsed.
   */
  public static ResourceManager newInstance() throws IOException, URISyntaxException
  {
    if (!exists)
    {
      instance = new ResourceManager();
    }
    return instance;
  }

  /**
   * Gets the path to the resource directory.
   *
   * @return The Path to the resource directory.
   */
  public Path getResourcePath()
  {
    return resourcePath;
  }
}
