package gui;

import gui.mf.MixedFractionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for the session history of the calculator
 *
 * This code complies with the JMU Honor Code
 *
 * @author Joshua Hairston, Zachary Buchan
 * @version 11/13/2023
 */
public class History extends JWindow implements ActionListener
{
  private static final long serialVersionUID = 1L;
  private static final int HEIGHT = 300;
  private static final int MAX_WIDTH = 450;
  private static final int MIN_WIDTH = 30;
  private boolean opened;
  private JButton button;
  private Timer timer;
  private JPanel historyPanel;
  private JPanel expression;

  /**
   * Constructor for the History class
   */
  public History(JFrame frame)
  {
    super(frame);
    opened = false; // Tracking the status of the window.
    setupLayout();
    setSize(MIN_WIDTH, HEIGHT);
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
    // Initialize history panel
    historyPanel = new JPanel();
    historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
    JScrollPane scrollPane = new JScrollPane(historyPanel);
    add(scrollPane, BorderLayout.CENTER);
    // Animation
    timer = new Timer(10, e -> animate());

    expression = new JPanel();
    historyPanel.add(expression);
  }

  public void addToExpression(final MixedFractionPanel mfp, String operator)
  {
    if (operator != null)
    {
      expression.add(new JLabel(operator));
    }
    expression.add(mfp);
    revalidate();
    repaint();
  }

  public void nextExpression()
  {
    expression = new JPanel();
    historyPanel.add(expression);
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
      if (!opened)
      {
        opened = true;
        button.setText("<");
        timer.start();
      }
    }
    else
    {
      opened = false;
      button.setText(">");
      timer.start();
    }
  }

  /**
   * Animates the button.
   */
  private void animate()
  {
    int targetWidth = (opened) ? MAX_WIDTH : MIN_WIDTH;
    int currentWidth = getWidth();
    if (currentWidth != targetWidth)
    {
      int newWidth = (currentWidth < targetWidth) ? currentWidth + 5 : currentWidth - 5;
      setSize(newWidth, HEIGHT);
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
}
