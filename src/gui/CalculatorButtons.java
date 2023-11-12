package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import actions.PressAction;

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
  private static final long serialVersionUID = 1L;
  public static final String ADDITION = "+";
  public static final String SUBTRACTION = "-";
  public static final String MULTIPLICATION = "\u00D7";
  public static final String DIVISION = "\u00F7";
  public static final String EQUALS = "=";
  public static final String SIGN = "\u00B1";
  public static final String RESET = "R";
  public static final String CLEAR = "C";
  public static final String BACK_SPACE = "\u2190";
  public static final String POSITION = "P";
  public static final String MEDIANT = "\u21F9";
  public static final String EXPONENT = "X\u207F";
  public static final String INVERSE = "inv";
  public static final String SIMPLIFY = "\u21A1";
  private Display display;

  /**
   * Constructor which takes in the class ButtonActions which is an ActionListener.
   * 
   * @param display
   *          the main window's display.
   * 
   */
  public CalculatorButtons(final Display display)
  {
    this.display = display;
    keyboardInputs();
    createCalculatorButtons();
  }

  /**
   * Private helper method for making buttons.
   * 
   * @param string
   *          the button's text
   * @return JButton a new JButton
   */
  private JButton setButton(final String string)
  {

    JButton button = new JButton(string);
    button.addActionListener(this);
//    button.setActionCommand(string); //TODO figure out why this is not needed
    Font font = new Font(Font.MONOSPACED, Font.BOLD, 15); // Changed the font
    button.setFont(font);

    // getting the action map so the keyboard action for this button can be
    ActionMap actionMap = this.getActionMap();
    actionMap.put(string, new PressAction(button));

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
    add(setButton(CLEAR), c);

    // Backspace button
    c.gridx = 2;
    c.ipadx = 100;
    add(setButton(BACK_SPACE), c);

    // Addition button
    c.gridx = 3;
    add(setButton(ADDITION), c);

    // Mediant button
    c.gridx = 4;
    add(setButton(MEDIANT), c);

    // Sign button
    c.gridx = 5;
    add(setButton(SIGN), c);

    // buttons for numbers 7-9
    c.gridx = 0;
    c.gridy = 1; // setting the y-axis of the button only once per row to save LOC.
    add(setButton(String.valueOf(7)), c);

    c.gridx = 1;
    add(setButton(String.valueOf(8)), c);

    c.gridx = 2;
    add(setButton(String.valueOf(9)), c);

    // Subtraction button
    c.gridx = 3;
    add(setButton(SUBTRACTION), c);

    // Exponent button
    c.gridx = 4;
    add(setButton(EXPONENT), c);

    // Inverse button
    c.gridx = 5;
    add(setButton(INVERSE), c);

    // buttons for the numbers 4,6
    c.gridx = 0;
    c.gridy = 2;
    add(setButton(String.valueOf(4)), c);

    c.gridx = 1;
    add(setButton(String.valueOf(5)), c);

    c.gridx = 2;
    add(setButton(String.valueOf(6)), c);

    // the multiplication button
    c.gridx = 3;
    add(setButton(MULTIPLICATION), c);

    // Simplify button
    c.gridx = 5;
    add(setButton(SIMPLIFY), c);

    // buttons for the numbers 1-3
    c.gridx = 0;
    c.gridy = 3;
    add(setButton(String.valueOf(1)), c);

    c.gridx = 1;
    add(setButton(String.valueOf(2)), c);

    c.gridx = 2;
    add(setButton(String.valueOf(3)), c);

    // Division button
    c.gridx = 3;
    add(setButton(DIVISION), c);

    // button for the number zero
    c.gridx = 0;
    c.gridy = 4;
    c.gridwidth = 2;
    add(setButton(String.valueOf(0)), c);

    // The position button with the position picture
    c.gridx = 2;
    c.gridwidth = 1;
    ImageIcon icon = new ImageIcon("res/position_logo.png");
    JButton button = new JButton();
    button.addActionListener(this);
    button.setActionCommand(POSITION);
    button.setIcon(icon);
    ActionMap actionMap = this.getActionMap();
    actionMap.put(POSITION, new PressAction(button));
    add(button, c);

    // The equals button
    c.gridx = 3;
    add(setButton(EQUALS), c);

  }

  /**
   * Method dealing with keyboard inputs.
   */
  private void keyboardInputs()
  {
    InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    int number = 0;
    for (int i = 48; i <= 57; i++)
    {
      inputMap.put(KeyStroke.getKeyStroke(i, 0), "" + number++);

    }
    // putting the operators into the input map
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, KeyEvent.SHIFT_DOWN_MASK), ADDITION);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), SUBTRACTION);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), MULTIPLICATION);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0), DIVISION);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), EQUALS);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0), POSITION);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), BACK_SPACE);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), CLEAR);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), RESET);
    // TODO talk to group about what they want the other keys to be for the other operators.
  }

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    display.handleButton(e);
  }

  /**
   * Setting the preferred size of the buttons.
   * 
   * @return the minimum size of the buttons.
   */
  public Dimension setPreferredSize()
  {
    return getMinimumSize();
  }

}
