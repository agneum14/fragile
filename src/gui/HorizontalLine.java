package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * This class constructs a JPanel with a horizontal line drawn on it.
 *
 * This work complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class HorizontalLine extends JPanel
{
  private final double width;

  /**
   * This constructor sets the width of the horizontal line, which is also the width of the JPanel.
   *
   * @param width
   *     The width of the horizontal line
   */
  public HorizontalLine(final int width)
  {
    this.width = (double) width;
    setPreferredSize(new Dimension(width, 1));
  }

  /**
   * Paint the horizontal line of the JPanel.
   *
   * @param g
   *     The panel's Graphics object
   */
  @Override
  protected void paintComponent(final Graphics g)
  {
    super.paintComponent(g);

    final Graphics2D g2 = (Graphics2D) g;
    final int yPos = getHeight() / 2;
    final Line2D line = new Line2D.Double(0, yPos, width, yPos);
    g2.draw(line);
  }
}
