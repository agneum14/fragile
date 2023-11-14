package app;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.CalculatorWindow;

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
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    CalculatorWindow window = new CalculatorWindow();
  }
}
