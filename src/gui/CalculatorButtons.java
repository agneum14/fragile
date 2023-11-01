package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
/**
 * Class for the calculatorButtons
 */
public class CalculatorButtons extends JPanel
{
  private static final long serialVersionUID = 1L;
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

  private ActionListener listener;

  public CalculatorButtons(ActionListener listener)
  {
    super();
    this.listener = listener;
    createCalculatorButtons();
  }

  // TODO make the buttons have fonts and implementation for the button presses
  private JButton setButton(String string)
  {
    JButton button = new JButton(string);
    button.addActionListener(listener);
    return button;

  }
  
  

  private void createCalculatorButtons()
  {
    // creating the layout for the buttons
    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    // Reset button
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 1.0;
    c.weighty = 1.0;
    c.fill = GridBagConstraints.BOTH;
    c.insets = new Insets(5, 5, 5, 5);
    add(setButton(RESET), c);

    // Clear button
    c.gridx = 1;
    c.gridy = 0;
    add(setButton(CLEAR), c);
    // Backspace button
    c.gridx = 2;
    c.gridy = 0;
    add(setButton(BACKSPACE), c);

    c.gridx = 3;
    c.gridy = 0;
    add(setButton(ADDITION), c);
    // Sign button
    c.gridx = 4;
    c.gridy = 0;
    add(setButton(SIGN), c);
    // buttons for numbers 7-9
    c.gridx = 0;
    c.gridy = 1;
    add(setButton(String.valueOf(7)), c);
    //
    c.gridx = 1;
    c.gridy = 1;
    add(setButton(String.valueOf(8)), c);

    c.gridx = 2;
    c.gridy = 1;
    add(setButton(String.valueOf(9)), c);
    // subtraction button
    c.gridx = 3;
    c.gridy = 1;
    add(setButton(SUBTRACTION), c);

    c.gridx = 0;
    c.gridy = 2;
    add(setButton(String.valueOf(4)), c);

    c.gridx = 1;
    c.gridy = 2;
    add(setButton(String.valueOf(5)), c);

    c.gridx = 2;
    c.gridy = 2;
    add(setButton(String.valueOf(6)), c);

    c.gridx = 3;
    c.gridy = 2;
    add(setButton(MULTIPLICATION), c);

    c.gridx = 0;
    c.gridy = 3;
    add(setButton(String.valueOf(1)), c);

    c.gridx = 1;
    c.gridy = 3;
    add(setButton(String.valueOf(2)), c);

    c.gridx = 2;
    c.gridy = 3;
    add(setButton(String.valueOf(3)), c);

    c.gridx = 3;
    c.gridy = 3;
    add(setButton(DIVISION), c);

    c.gridx = 0;
    c.gridy = 4;
    c.gridwidth = 2;
    add(setButton(String.valueOf(0)), c);

    c.gridx = 2;
    c.gridy = 4;
    c.gridwidth = 1;
    add(setButton(POSITION), c);

    c.gridx = 3;
    c.gridy = 4;
    add(setButton(EQUALS), c);
    
  }
  

}
