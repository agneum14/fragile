package gui;

import calculating.MixedFraction;

import javax.swing.*;
import java.awt.*;

/**
 * This class visually represents a MixedFraction with a JPanel. The minus sign (if it exists) is
 * displayed leftmost, followed by the whole number, followed by the fraction. The fraction (as in
 * not the negative sign or the whole number) is comprised the numerator, solidus, and denominator
 * in a vertical stack. The fraction will not be displayed if the numerator is 0.
 *
 * This work complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class MixedFractionPanel extends JPanel
{
  private static final int MINUS_SIZE = 10;

  /**
   * This constructor constructs the JPanel from the given MixedFraction.
   *
   * @param mf
   *     The MixedFraction to display
   */
  public MixedFractionPanel(final MixedFraction mf)
  {
    setLayout(new FlowLayout(FlowLayout.LEFT));

    if (mf.getSign() == -1)
    {
      final JPanel minusPanel = new HorizontalLine(MINUS_SIZE);
      add(minusPanel);
    }

    JLabel wholeLabel = new JLabel(String.valueOf(mf.getWhole()));
    final Font wholeLabelFont = new Font(wholeLabel.getFont().getName(), Font.PLAIN, 25);
    wholeLabel.setFont(wholeLabelFont);
    add(wholeLabel);

    if (mf.getNum() != 0)
    {
      final JPanel fractionPanel = new JPanel(new GridBagLayout());
      final GridBagConstraints c = new GridBagConstraints();
      final Font fracFont = new Font(getFont().getName(), Font.PLAIN, 18);

      c.gridx = 0;
      c.gridy = 0;
      wholeLabel = new JLabel(String.valueOf(mf.getNum()));
      wholeLabel.setFont(fracFont);
      fractionPanel.add(wholeLabel);

      c.gridy = 1;
      final JPanel denomPanel = new JPanel();
      denomPanel.setLayout(new BoxLayout(denomPanel, BoxLayout.PAGE_AXIS));
      denomPanel.add(new JSeparator(JSeparator.HORIZONTAL));
      final JLabel denomLabel = new JLabel(String.valueOf(mf.getDenom()));
      denomLabel.setFont(fracFont);
      denomPanel.add(denomLabel);
      fractionPanel.add(denomPanel, c);

      add(fractionPanel);
    }
  }
}
