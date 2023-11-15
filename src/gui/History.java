package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * Class for the session history of the calculator
 * 
 * @author Joshua Hairston
 * 
 * @version 11/13/2023
 * 
 *          This Code Complies with the JMU Honor code.
 */
public class History extends JWindow implements ActionListener
{
  private static final long serialVersionUID = 1L;
  private static final int CLOSED = 0;
  private static final int OPENED = 1;
  private int status;
  private JButton button;

  /**
   * Constructor for the History class
   */
  public History(JFrame frame)
  {
    super(frame);
    this.status = CLOSED; // Tracking the status of the window.
    setupLayout();
    setSize(30, 300);
    setVisible(true);
  }
  /**
   * Setting up the background and the layout of the history window.
   */
  private void setupLayout()
  {
    getContentPane().setBackground(Display.POWDER_BLUE);
    setLayout(new BorderLayout());
    button = new JButton(">");
    button.addActionListener(this);
    add(button, BorderLayout.EAST);

  }
  /**
   * Method for checking to see if the window is either closed or open. 
   */
  @Override
  public void actionPerformed(ActionEvent e)
  {
    String ac = e.getActionCommand();
    if (ac.equals(">"))
    {
      if (status == CLOSED)
      {
        setSize(300, 300);
        status = OPENED;
        button.setText("<");

      }

    }
    else
    {
      setSize(30, 300);
      status = CLOSED;
      button.setText(">");
    }
  }

  public void setHistoryLocation(int x, int y)
  {
    this.setLocation(x, y);

  }
  
  public void draw() {
    
  }
}
