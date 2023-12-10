package app;

import gui.CalculatorWindow;
import gui.Menu;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Fragile implements Runnable
{
  public static void main(String[] args) throws InterruptedException, InvocationTargetException
  {
    SwingUtilities.invokeAndWait(new Fragile());
  }

  @Override
  public void run()
  {
    try
    {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
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
    } else
    {
      System.out.println("File not found: " + filePath);
    }
  }

  /**
   * Checks if the file exists.
   *
   * @param filePath
   * @return true if the file exists, false otherwise
   */
  private boolean fileExists(String filePath)
  {
    Path path = Paths.get(filePath);
    return Files.exists(path);
  }

  /**
   * Reads the letters from the file.
   *
   * @param filePath
   * @return
   */
  private String[] readLettersFromFile(String filePath)
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
    } catch (IOException e)
    {
      e.printStackTrace();
      return null; // Handle the exception
    }
  }
}