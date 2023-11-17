package gui;

import calculating.FractionModePublisher;
import calculating.FractionStylePublisher;
import calculating.MixedFraction;
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
  private final JPanel pies;


  /**
   * Construct the pie chart window, creating the heading panel and calling draw with 0 (as a mixed
   * fraction).
   */
  public PieChartWindow()
  {
    super("Pie Chart");
    GridBagConstraints g = new GridBagConstraints();

    getContentPane().setLayout(new GridBagLayout());
    setPreferredSize(new Dimension(750, 400));
    setMinimumSize(new Dimension(750, 400));
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    g.gridx = 0;
    g.gridy = 0;
    heading = new JPanel();
    final JLabel headingLabel = new JLabel(
        Language.translate("Current Expression", "Expression Actuelle.", "Aktueller Ausdruck."));
    heading.add(headingLabel);
    add(heading, g);

    g.gridy = 1;
    g.weightx = 1;
    g.fill = GridBagConstraints.BOTH;
    var gridLayout = new GridLayout(0, 3);
    gridLayout.setVgap(20);
    pies = new JPanel(gridLayout);
    pies.setOpaque(false);
    add(pies, g);

    g.gridy = 2;
    g.weighty = 1;
    add(new JPanel(), g);

    addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosed(WindowEvent e)
      {
        toggleVisibility();
      }
    });
  }

  public void addCell(FractionStylePublisher fractionStylePublisher,
      FractionModePublisher fractionModePublisher, MixedFraction mf, String operator)
  {
    pies.add(new PieChartCell(fractionStylePublisher, fractionModePublisher, mf, operator));
    pies.revalidate();
    pies.repaint();
  }

  public void reset()
  {
    pies.removeAll();
    revalidate();
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
