package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import actions.ButtonActions;

/**
 * Class for the calculator buttons, which is added to the main window.
 * 
 * @author Joshua Hairston
 * 
 * @version 11/2/2023
 * 
 *          This Code Complies with the JMU Honor Code.
 */
public class CalculatorButtons extends JPanel implements ActionListener
{
  public static final String ADDITION = "+";
  public static final String SUBTRACTION = "-";
  public static final String MULTIPLICATION = "\u00D7";
  public static final String DIVISION = "\u00F7";
  public static final String EQUALS = "=";
  public static final String SIGN = "\u00B1";
  public static final String RESET = "R";
  public static final String CLEAR = "C";
  public static final String BACKSPACE = "\u2190";
  public static final String POSITION = "P";
  private Display display;

  /**
   * Constructor which takes in the class ButtonActions which is an ActionListener.
   * 
   */
  public CalculatorButtons(Display display)
  {
    this.display = display;
    createCalculatorButtons();
  }

  /**
   * Private helper method for making buttons.
   * 
   * @param string
   *          the button's text
   * @return JButton a new JButton
   */
  private JButton setButton(String string)
  {
    JButton button = new JButton(string);
    button.addActionListener(this); // adding the action
    button.setActionCommand(string);
    Font font = new Font(Font.MONOSPACED, Font.BOLD, 15); // Changed the font
    button.setFont(font);
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
    // buttons for the numbers 4,6
    c.gridx = 0;
    c.gridy = 2;
    add(setButton(String.valueOf(4)), c);
    c.gridx = 1;
    c.gridy = 2;
    add(setButton(String.valueOf(5)), c);

    c.gridx = 2;
    c.gridy = 2;
    add(setButton(String.valueOf(6)), c);
    // the multiplication button
    c.gridx = 3;
    c.gridy = 2;
    add(setButton(MULTIPLICATION), c);
    // buttons for the numbers 1-3
    c.gridx = 0;
    c.gridy = 3;
    add(setButton(String.valueOf(1)), c);
    c.gridx = 1;
    c.gridy = 3;
    add(setButton(String.valueOf(2)), c);

    c.gridx = 2;
    c.gridy = 3;
    add(setButton(String.valueOf(3)), c);
    // Division button
    c.gridx = 3;
    c.gridy = 3;
    add(setButton(DIVISION), c);
    // button for the number zero
    c.gridx = 0;
    c.gridy = 4;
    c.gridwidth = 2;
    add(setButton(String.valueOf(0)), c);
    // The position button with the position picture
    c.gridx = 2;
    c.gridy = 4;
    c.gridwidth = 1;
    ImageIcon icon = new ImageIcon("res/position_logo.png");
    JButton button = new JButton();
    button.addActionListener(this);
    button.setActionCommand(POSITION);
    button.setIcon(icon);
    add(button, c);
    // The equals button
    c.gridx = 3;
    c.gridy = 4;
    add(setButton(EQUALS), c);

  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    display.handleButton(e);
  }

  /**
   * Setting the preferred size of the buttons.
   * 
   * @return
   */
  public Dimension setPreferredSize()
  {
    return getMinimumSize();
  }

}
