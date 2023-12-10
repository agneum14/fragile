package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;

import gui.CalculatorWindow;
import gui.Menu;

/**
 * The Fragile Application.
 * 
 * @author Joshua Hairston
 * @author Asa Gittmsn
 * @author Ray Steen
 * @author Andrew
 * @author Zach
 * @author Logan
 * 
 * @version 12/10/2023
 */
public class Fragile implements Runnable
{
  /**
   * Main method for the startup of the application.
   * 
   * @param args
   *          the arguments for the main method.
   * @throws InterruptedException
   * @throws InvocationTargetException
   */
  public static void main(final String[] args)
      throws InterruptedException, InvocationTargetException
  {
    SwingUtilities.invokeAndWait(new Fragile());
  }

  @Override
  public void run()
  {
    try
    {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    }
    catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | UnsupportedLookAndFeelException e)
    {
      e.printStackTrace();
    }

    // Create CalculatorWindow
    CalculatorWindow calculatorWindow = new CalculatorWindow();

    // Show the calculator window
    calculatorWindow.setVisible(true);

    // Read letters from file
    String filePath = "calculator_last_saved.txt";

    if (fileExists(filePath))
    {
      String[] lettersArray = readLettersFromFile(filePath);

      if (lettersArray != null)
      {
        Menu.checkBoxes(lettersArray);

      }
    }
    else
    {
      System.out.println("File not found: " + filePath);
    }
  }

  /**
   * Private method which checks if the file exists.
   * 
   * @param filePath
   *          the file path.
   * @return true if the file exists, false otherwise
   */
  private boolean fileExists(final String filePath)
  {
    Path path = Paths.get(filePath);
    return Files.exists(path);
  }

  /**
   * Reads the letters from the file.
   * 
   * @param filePath
   *          the file path.
   * @return content the letters used for preferences stored in an array.
   */
  private String[] readLettersFromFile(final String filePath)
  {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
    {
      StringBuilder contentBuilder = new StringBuilder();
      int character;

      while ((character = reader.read()) != -1)
      {
        char letter = (char) character;
        // only letters in array
        if (Character.isLetter(letter))
        {
          contentBuilder.append(letter);
        }
      }

      String content = contentBuilder.toString();
      return content.split(""); // Splitting the content into an array of letters
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return null; // Handle the exception
    }
  }
}
