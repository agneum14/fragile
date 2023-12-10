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

      
       
    }
}
