package gui;

import calculating.MixedFraction;
import gui.mf.MixedFractionPanel;
import utilities.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This class implements the pie chart window, which visualizes the current mixed fraction
 * evaluation as pies. The whole number is represented by multiple full pies, and the fractional
 * number by a fractional pie. If the whole number exceeds 49, a numbered pie instead of the full
 * pies.
 *
 * This code complies with the JMU honor code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class PieChartWindow extends JFrame
{
  private final JPanel heading;

  /**
   * Construct the pie chart window, creating the heading panel and calling draw with 0 (as a mixed
   * fraction).
   */
  public PieChartWindow()
  {
    super("Pie Chart");
    getContentPane().setLayout(new GridBagLayout());
    setPreferredSize(new Dimension(500, 850));

    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    heading = new JPanel();
    final JLabel headingLabel = new JLabel(Language.translate("Current Mixed Fraction", "Fraction Mixte Actuelle", "現在の混合分数"));
    heading.add(headingLabel);

    draw(new MixedFraction(1, 0, 0, 1));

    addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosed(WindowEvent e)
      {
        toggleVisibility();
      }
    });
  }

  /**
   * Update the window to display a new mixed fraction.
   *
   * @param mf
   *     The new mixed fraction
   */
  public void draw(final MixedFraction mf)
  {
    JPanel pies, pieFrac;
    int piesNum;
    GridBagConstraints c;

    // create JPanel for the current mixed fraction
    JPanel mfp = new MixedFractionPanel(mf);

    // add pies
    pies = new JPanel(new GridLayout(0, 5));
    piesNum = 0;
    if (mf.getWhole() > 49)
    {
      pies.add(new PieNumber(mf.getWhole()));
      piesNum++;
    }
    else
    {
      for (int i = 0; i < mf.getWhole(); i++)
      {
        pies.add(new PieFull());
        piesNum++;
      }
    }
    if (mf.getNum() != 0)
    { // don't display a 0 fractional pie
      pieFrac = new PieFraction(mf);
      pies.add(pieFrac);
      piesNum++;
    }

    // the grid should always have 50 elements so the size of each pie doesn't
    // change when the mixed fraction evaluation is changed (i.e. this method is
    // called again)
    for (; piesNum < 50; piesNum++)
    {
      pies.add(new JPanel());
    }

    getContentPane().removeAll();
    c = new GridBagConstraints();

    c.weightx = 1;
    getContentPane().add(heading, c);

    c.gridy = 1;
    getContentPane().add(mfp, c);

    // consume remaining vertical space with the pie grid
    c.gridy = 2;
    c.weighty = 1;
    c.fill = GridBagConstraints.BOTH;
    getContentPane().add(pies, c);

    pack();
    repaint();
  }

  public void toggleVisibility()
  {
    if (isVisible())
    {
      setVisible(false);
    }
    else
    {
      setVisible(true);
    }
  }
}
