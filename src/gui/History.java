package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Timer;

/**
 * Class for the session history of the calculator
 * 
 * @author Joshua Hairston, Zachary Buchan
 * 
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
  private Timer timer;
  private ArrayList<JPanel> panels;

  /**
   * Constructor for the History class
   */
  public History(final JFrame frame)
  {
    super(frame);
    this.status = CLOSED; // Tracking the status of the window.
    this.panels = new ArrayList<>();
    setupLayout();
    setSize(30, 300);
    setVisible(true);
  }

  /**
   * Setting up the background, the layout of the history window, and the animation.
   */
  private void setupLayout()
  {
    getContentPane().setBackground(Display.POWDER_BLUE);
    setLayout(new BorderLayout());
    button = new JButton(">");
    button.addActionListener(this);
    add(button, BorderLayout.EAST);

    // animation
    timer = new Timer(10, new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        animate();
      }
    });
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
        status = OPENED;
        button.setText("<");
        timer.start();
      }
    }
    else
    {
      status = CLOSED;
      button.setText(">");
      timer.start();
    }
  }

  /**
   * Animates the button.
   */
  private void animate()
  {
    int targetWidth = (status == OPENED) ? 300 : 30;
    int currentWidth = getWidth();

    if (currentWidth != targetWidth)
    {
      int newWidth = (currentWidth < targetWidth) ? currentWidth + 5 : currentWidth - 5;
      setSize(newWidth, 300);

      if (currentWidth < targetWidth && newWidth >= targetWidth)
      {
        timer.stop();
      }
      else if (currentWidth > targetWidth && newWidth <= targetWidth)
      {
        timer.stop();
      }
    }
  }

  /**
   * Changing the location of the history window to move with the main window.
   * 
   * @param x
   *          the x value given
   * @param y
   *          the y value given.
   */
  public void setHistoryLocation(int x, int y)
  {
    setLocation(x, y);
  }

  public void addPanel(JPanel panel)
  {
    panels.add(panel);

  }
}
