package gui;

import java.awt.*;

/**
 * A full pie is a completely filled in circle representing the number 1.
 *
 * This work complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class PieFull extends Pie
{
  /**
   * Paint a full circle on the JPanel.
   *
   * @param g
   *     The panel's Graphics object
   */
  @Override
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
