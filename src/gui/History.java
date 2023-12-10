package gui;

import calculating.ExpressionElement;
import calculating.MixedFraction;
import gui.Display.Operator;
import gui.mf.MixedFractionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Class for the session history of the calculator.
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
  private static final String GREATER = ">";
  private boolean opened;
  private JButton button;
  private Timer timer;
  private JPanel historyPanel;

  /**
   * Constructor for the History class.
   *
   * @param frame The parent frame to attach the history to.
   */
  public History(final JFrame frame)
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
    button = new JButton(GREATER);
    button.addActionListener(this);
    add(button, BorderLayout.EAST);
    // Initialize history panel
    historyPanel = new JPanel();
    historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
    final JScrollPane scrollPane = new JScrollPane(historyPanel);
    add(scrollPane, BorderLayout.CENTER);
    // Animation
    timer = new Timer(10, e -> animate());
  }

  /**
   * Add a new expression to the history and repaint.
   *
   * @param expressionList
   *     The expression (as a list of mixed fractions and operators) to add
   * @param display
   *     The display. This needs to be passed to create new mixed fraction panels
   */
  public void update(final List<ExpressionElement> expressionList, final Display display)
  {
    final JPanel expression = new JPanel();

    for (final ExpressionElement ee : expressionList)
    {
      if (ee instanceof MixedFraction)
      {
        final MixedFraction mf = (MixedFraction) ee;
        final MixedFractionPanel mfp = new MixedFractionPanel(mf, display);
        expression.add(mfp);
      }
      else if (ee instanceof Operator)
      {
        final Operator operator = (Operator) ee;
        final JLabel operatorLabel = new JLabel(operator.toString());
        expression.add(operatorLabel);
      }
    }

    historyPanel.add(expression);
    historyPanel.revalidate();
    historyPanel.repaint();
  }

  /**
   * Method for checking to see if the window is either closed or open.
   */
  @Override
  public void actionPerformed(final ActionEvent e)
  {
    final String ac = e.getActionCommand();
    if (ac.equals(GREATER))
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
      button.setText(GREATER);
      timer.start();
    }
  }

  /**
   * Animates the button.
   */
  private void animate()
  {
    final int targetWidth = (opened) ? MAX_WIDTH : MIN_WIDTH;
    final int currentWidth = getWidth();
    if (currentWidth != targetWidth)
    {
      final int newWidth = (currentWidth < targetWidth) ? currentWidth + 5 : currentWidth - 5;
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
