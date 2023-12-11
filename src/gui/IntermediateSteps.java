package gui;

import calculating.ExpressionElement;
import calculating.MixedFraction;
import gui.Display.Operator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The steps display shows a step-by-step breakdown of every operation performed in an expression.
 *
 * This code complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class IntermediateSteps extends JWindow implements ActionListener
{
  private static final long serialVersionUID = 1L;
  private static final int HEIGHT = 300;
  private static final int MAX_WIDTH = 450;
  private static final int MIN_WIDTH = 30;
  private static final int Y_OFFSET = 110;
  private static final String LESS = "<";

  private final JFrame parent;
  private boolean opened;
  private final JButton button;
  private final Timer timer;
  private final JPanel content;

  /**
   * Constructor for the History class.
   *
   * @param parent
   *     The parent frame to attach the history to.
   */
  public IntermediateSteps(final JFrame parent)
  {
    super(parent);
    this.parent = parent;
    opened = false; // Tracking the status of the window.

    setBackground(Display.displayColor);
    setLayout(new BorderLayout());
    button = new JButton(LESS);
    button.addActionListener(this);
    add(button, BorderLayout.WEST);
    // Initialize history panel
    content = new JPanel();
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
    final JScrollPane scrollPane = new JScrollPane(content);
    add(scrollPane, BorderLayout.CENTER);
    // Animation
    timer = new Timer(10, e -> animate());

    setSize(MIN_WIDTH, HEIGHT);
    setVisible(true);
  }

  /**
   * Called by the calculator window, this function updates the position of the intermediate steps
   * display to keep it attached to the left side of the calculator window.
   *
   * @param x
   *     The x position
   * @param y
   *     The y position
   */
  public void setPosition(final int x, final int y)
  {
    setLocation(x - getWidth(), y + Y_OFFSET);
  }

  /**
   * Add a new expression to the history and repaint.
   *
   * @param steps
   * The steps to add
   */
  public void update(final List<List<ExpressionElement>> steps)
  {
    content.removeAll();

    for (final List<ExpressionElement> step : steps)
    {
      final MixedFraction b = (MixedFraction) step.get(0);
      final Operator operator = (Operator) step.get(1);
      final MixedFraction a = (MixedFraction) step.get(2);
      final MixedFraction result = (MixedFraction) step.get(3);

      final JPanel stepContent = new JPanel(new GridBagLayout());
      final GridBagConstraints g = new GridBagConstraints();
      g.gridx = 0;
      g.gridy = -1;
      g.anchor = GridBagConstraints.WEST;

      g.gridy++;
      final JLabel first = new JLabel(
          String.format("STEPPING THROUGH %s %s %s", b.toString(), operator.toString(),
              a.toString()));
      stepContent.add(first, g);

      g.gridy++;
      MixedFraction aFrac = new MixedFraction(a).improper();
      final MixedFraction bFrac = new MixedFraction(b).improper();
      final JLabel frac = new JLabel(
          String.format("Convert the operands to improper: %s  %s", fracStr(bFrac),
              fracStr(aFrac)));
      stepContent.add(frac, g);

      if (operator == Operator.ADD || operator == Operator.SUB)
      {
        g.gridy++;
        final JLabel gcd = new JLabel(
            String.format("The GCD of the denominators %d and %d is %d", b.getDenom(), a.getDenom(),
                result.getDenom()));
        stepContent.add(gcd, g);

        g.gridy++;
        final MixedFraction bScale = MixedFraction.mult(b,
            new MixedFraction(1, result.getDenom() / b.getDenom(), 0, 1));
        final MixedFraction aScale = MixedFraction.mult(a,
            new MixedFraction(1, result.getDenom() / a.getDenom(), 0, 1));
        final JLabel scale = new JLabel(
            String.format("Scale the operands to the GCD: %s  %s", fracStr(bScale),
                fracStr(aScale)));
        stepContent.add(scale, g);

        g.gridy++;
        final JLabel perform = new JLabel(
            String.format("Perform the operation: %s", fracStr(result)));
        stepContent.add(perform, g);
      }
      else if (operator == Operator.DIV || operator == Operator.MULT)
      {
        if (operator == Operator.DIV)
        {
          g.gridy++;
          aFrac = new MixedFraction(1, 0, aFrac.getDenom(), aFrac.getNum());
          final JLabel inv = new JLabel(
              String.format("Invert the second operand: %s", fracStr(aFrac)));
          stepContent.add(inv, g);
        }
        g.gridy++;
        final JLabel mult = new JLabel(
            String.format("Multiply the numerators: %d * %d = %d", bFrac.getNum(), aFrac.getNum(),
                result.getNum()));
        stepContent.add(mult, g);

        g.gridy++;
        final JLabel denom = new JLabel(
            String.format("Multiply the denominators: %d * %d = %d", bFrac.getDenom(),
                aFrac.getDenom(), result.getDenom()));
        stepContent.add(denom, g);

        g.gridy++;
        final JLabel res = new JLabel(
            String.format("Combine to get the result: %s", fracStr(result)));
        stepContent.add(res, g);

        g.weighty = 1;
        g.fill = GridBagConstraints.BOTH;
        g.gridy++;
        stepContent.add(new JPanel(), g);

      }

      content.add(stepContent);
    }

    content.revalidate();
    content.repaint();
  }

  /**
   * Get a String representation of a MixedFraction without the whole component.
   *
   * @param mf
   *     The mixed fraction
   * @return The String representation
   */
  private String fracStr(final MixedFraction mf)
  {
    final String neg = (mf.getSign() == -1) ? "-" : "";
    return String.format("%s%d/%d", neg, mf.getNum(), mf.getDenom());
  }

  /**
   * Reset the steps by clearing the content panel and repainting.
   */
  public void reset()
  {
    content.removeAll();
    revalidate();
    repaint();
  }

  /**
   * Method for checking to see if the window is either closed or open.
   */
  @Override
  public void actionPerformed(final ActionEvent e)
  {
    final String ac = e.getActionCommand();
    if (ac.equals(LESS))
    {
      if (!opened)
      {
        opened = true;
        button.setText(">");
        timer.start();
      }
    }
    else
    {
      opened = false;
      button.setText(LESS);
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
      final int change = (currentWidth < targetWidth) ? 5 : -5;
      final int newWidth = currentWidth + change;
      setSize(newWidth, HEIGHT);
      setPosition(parent.getX(), parent.getY());
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
