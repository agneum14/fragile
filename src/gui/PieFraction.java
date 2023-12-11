package gui;

import calculating.MixedFraction;

import java.awt.*;

/**
 * A fractional pie is a partially filled in circle representing a fraction, such as 3/4.
 *
 * This work complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class PieFraction extends Pie
{
  private static final long serialVersionUID = 1L;
  private final int num;
  private final int denom;

  /**
   * This constructor initializes num and denom from a MixedFraction.
   *
   * @param mf
   *          The MixedFraction
   */
  public PieFraction(final MixedFraction mf)
  {
    num = mf.getNum();
    denom = mf.getDenom();
  }

  /**
   * Paint a fractional circle on the JPanel.
   *
   * @param g
   *          The panel's Graphics object
   */
  protected void paintComponent(final Graphics g)
  {
    super.paintComponent(g);
    final int angle = 360 * num / denom * -1; // multiply by -1 to fill clockwise
                                              //
    g.setColor(Color.BLACK);
    g.fillOval(0, 0, getWidth() - MARGIN, getHeight() - MARGIN);

    g.setColor(Color.RED);
    int pos = STROKE / 2;
    int width = getWidth() - MARGIN - STROKE;
    int height = getHeight() - MARGIN - STROKE;
    g.fillArc(pos, pos, width, height, 0, angle);
  }
}
