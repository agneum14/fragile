package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import gui.Display;

/**
 * Class for keyboard actions
 * 
 * @author Joshua Hairston
 * 
 * @version 11/12/2023
 *
 * This Code Compiles with the JMU Honor Code.
 */
public class PressAction extends AbstractAction
{

  private static final long serialVersionUID = 1L;
  private JButton button;

  public PressAction(JButton button)
  {
    super();
    this.button = button;
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    button.grabFocus();
    button.doClick();
  }

}
