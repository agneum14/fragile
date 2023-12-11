package gui;

import javax.swing.*;
import java.awt.*;

/**
 * A numerical pie is a filled circle with a number inside, e.g. "x50." This is used in the pie
 * chart window when the whole number of the mixed fraction exceeds 49, in an effort to concisely
 * visualize larger mixed fractions.
 *
 * This work complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class PieNumber extends Pie
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * This constructor uses the whole number of the mixed fraction to add a JLabel to the panel,
   * displaying that number textually.
   *
   * @param whole
   *     The whole number of the mixed fraction
   */
  public PieNumber(final int whole)
  {
    setLayout(new GridLayout(0, 1));

    final String text = String.format("x%d", whole);
    final JLabel label = new JLabel(text, JLabel.CENTER);
    label.setFont(new Font(getFont().getName(), Font.BOLD, 20));

    add(label);
  }

  /**
   * Paint a full circle on the JPanel.
   *
   * @param g
   *     The panel's Graphics object
   */
  protected void paintComponent(final Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    g.fillOval(0, 0, getWidth() - MARGIN, getHeight() - MARGIN);

    g.setColor(Color.RED);
    int pos = STROKE / 2;
    int width = getWidth() - MARGIN - STROKE;
    int height = getHeight() - MARGIN - STROKE;
    g.fillOval(pos, pos, width, height);
  }
}
