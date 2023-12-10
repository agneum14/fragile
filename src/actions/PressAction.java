package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

/**
 * Class for keyboard actions
 * 
 * @author Joshua Hairston
 * 
 * @version 11/12/2023
 *
 *          This Code Compiles with the JMU Honor Code.
 */
public class PressAction extends AbstractAction
{

  private static final long serialVersionUID = 1L;
  private JComponent comp;

  /**
   * @param comp
   */
  public PressAction(JComponent comp)
  {
    super();
    this.comp = comp;
  }

  /**
   * method performing the button pressing action.
   */
  @Override
  public void actionPerformed(ActionEvent e)
  {
    if (comp instanceof JButton)
    {
      JButton p = (JButton) comp;
      p.grabFocus();
      p.doClick();
    }
    else if (comp instanceof JMenuItem)
    {
      JMenuItem p = (JMenuItem) comp;
      p.grabFocus();
      p.doClick();
    }
    else if (comp instanceof JRadioButton)
    {
      JRadioButton p = (JRadioButton) comp;
      p.grabFocus();
      p.doClick();
    }
    else if (comp instanceof JCheckBoxMenuItem)
    {
      JCheckBoxMenuItem p = (JCheckBoxMenuItem) comp;
      p.grabFocus();
      p.doClick();
    }
    else
    {
      JOptionPane.showMessageDialog(null, "Unknown shortcut", "Error", JOptionPane.ERROR_MESSAGE);

    }
  }

}
