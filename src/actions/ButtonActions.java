package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
/**
 * Class which is an ActionListener for the calculatorButtons
 * 
 * @author Joshua Hairston
 * 
 * @version 11/2/2023
 * 
 * This Code Complies with the JMU Honor code.
 */
public class ButtonActions implements ActionListener
{
  //All possible buttons on the calculator.
  private static final String ADDITION = "+";
  private static final String SUBTRACTION = "-";
  private static final String MULTIPLICATION = "\u00D7";
  private static final String DIVISION = "\u00F7";
  private static final String EQUALS = "=";
  private static final String SIGN = "\u00B1";
  private static final String RESET = "R";
  private static final String CLEAR = "C";
  private static final String BACKSPACE = "\u2190";
  private static final String POSITION = "?";
  private static final String ONE = "1";
  private static final String TWO = "2";
  private static final String THREE = "3";
  private static final String FOUR = "4";
  private static final String FIVE = "5";
  private static final String SIX = "6";
  private static final String SEVEN = "7";
  private static final String EIGHT = "8";
  private static final String NINE = "9";

  @Override
  public void actionPerformed(ActionEvent e)
  {
    //This is for the button "1"
    String command = e.getActionCommand();
    if(ONE.equals(command)) System.out.println("WORKS");
  }

}
