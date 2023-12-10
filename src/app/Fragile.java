package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import gui.CalculatorWindow;
import gui.Menu;

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

        // Create a file picker dialog after the calculator is shown
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        // Check if a file is selected
        if (result == JFileChooser.APPROVE_OPTION)
        {
            // Get the selected file
            String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Read the content of the file and store each character in a separate string in an array
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFilePath)))
            {
                List<String> charList = new ArrayList<>();
                int charValue;
                while ((charValue = reader.read()) != -1)
                {
                    charList.add(String.valueOf((char) charValue));
                }

                // Convert the list to an array
                String[] charArray = charList.toArray(new String[0]);
                Menu.checkBoxes(charArray);

               
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            // Handle the case where no file is selected
            System.out.println("No file selected. Exiting...");
        }
    }
}
