package gui;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a "pie," which is a circular JPanel used in combination to visually display
 * a mixed fraction in the pie chart window.
 *
 * This code complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class Pie extends JPanel
{
  protected static final int MARGIN = 2;
  protected static final int STROKE = 6;
  private static final long serialVersionUID = 1L;

  /**
   * Override getPreferred size to make pies 50 by 50.
   */
  @Override
  public Dimension getPreferredSize()
  {
    return new Dimension(50, 50);
  }
}
