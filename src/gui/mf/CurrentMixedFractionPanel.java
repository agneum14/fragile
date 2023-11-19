package gui.mf;

import calculating.CurrentMixedFraction;
import calculating.FractionModePublisher;
import calculating.FractionStylePublisher;
import gui.Display;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * This class visually represents a CurrentMixedFraction with a JPanel. The minus sign (if it
 * exists) is displayed leftmost, followed by the whole number, followed by the fraction. The
 * fraction (as in not the negative sign or the whole number) is comprised the numerator, solidus,
 * and denominator in a vertical stack. The component of the current mixed fraction currently
 * selected (either WHOLE, NUM, or DENOM) is displayed with a dashed border. If either the whole,
 * num, or denom hasn't been entered yet (meaning it's null) a blank JPanel is displayed for that
 * component.
 *
 * This work complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class CurrentMixedFractionPanel extends MixedFractionPanel
{
  private static final Dimension WHOLE_DIM = new Dimension(16, 30);
  private static final Dimension NUM_DIM = new Dimension(11, 22);

  /**
   * This constructor creates the JPanel from the current mixed fraction. Methods used in the super
   * constructor have been overridden to change it's behavior as intended (i.e. blank JPanels are
   * used for null components). Afterwards, a dashed border is added to the component in the current
   * position.
   *
   * @param cmf
   *     The current mixed fraction
   */
  public CurrentMixedFractionPanel(final CurrentMixedFraction cmf)
  {
    super(cmf.getSign(), cmf.getWhole(), cmf.getNum(), cmf.getDenom(), null);

    FractionStylePublisher.getInstance().removeSubscriber(this);
    FractionModePublisher.getInstance().removeSubscriber(this);

    // add a border to the component of the current position
    final Border border = BorderFactory.createDashedBorder(Color.BLACK);
    switch (cmf.getPos())
    {
      case WHOLE -> wholePanel.setBorder(border);
      case NUM -> numPanel.setBorder(border);
      case DENOM -> denomPanel.setBorder(border);
      default -> wholePanel.setBorder(border); // never happens
    }
  }

  /**
   * Set the whole panel. If whole isn't null, the super method is used. Otherwise, a blank JPanel
   * is used
   */
  @Override
  protected void setWholePanel()
  {
    if (getWhole() != null)
    {
      super.setWholePanel();
    }
    else
    {
      wholePanel = new JPanel();
      wholePanel.setPreferredSize(WHOLE_DIM);
      wholePanel.setBackground(Display.POWDER_BLUE);
    }
  }

  /**
   * Set the num panel. If num isn't null, the super method is used. Otherwise, a blank JPanel is
   * used
   */
  @Override
  protected void setNumPanel()
  {
    if (getNum() != null)
    {
      super.setNumPanel();
    }
    else
    {
      numPanel = new JPanel();
      numPanel.setPreferredSize(NUM_DIM);
      numPanel.setBackground(Display.POWDER_BLUE);
    }
  }

  /**
   * Set the denom panel. If dneom isn't null, the super method is used. Otherwise, a blank JPanel
   * is used
   */
  @Override
  protected void setDenomPanel()
  {
    if (getDenom() != null)
    {
      super.setDenomPanel();
    }
    else
    {
      denomPanel = new JPanel();
      denomPanel.setPreferredSize(NUM_DIM);
      denomPanel.setBackground(Display.POWDER_BLUE);
    }
  }

  /**
   * Add all the panels to the JPanel and repaint.
   */
  @Override
  protected void draw()
  {
    removeAll();

    add(signPanel);
    add(wholePanel);
    add(fractionPanel);

    repaint();
  }

  /**
   * Override update so the current mixed fraction isn't manipulated when the reduced or proper mode
   * is set.
   */
  @Override
  protected void update()
  {
    setPanelsAndDraw();
  }

}
