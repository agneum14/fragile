package gui;

import calculating.FractionModePublisher;
import calculating.FractionStylePublisher;
import calculating.MixedFraction;
import gui.mf.MixedFractionPanel;

import javax.swing.*;
import java.awt.*;

/**
 * PieChartCell
 */
public class PieChartCell extends JPanel
{
  public PieChartCell(final FractionStylePublisher fractionStylePublisher,
      final FractionModePublisher fractionModePublisher, final MixedFraction mf, final String operator)
  {
    setLayout(new GridBagLayout());
    setOpaque(false);

    final MixedFraction reduced = new MixedFraction(mf).reduce();
    final GridBagConstraints g = new GridBagConstraints();
    final Font font = new Font(getFont().getName(), Font.PLAIN, 18);
    final String operatorText = (operator == null) ? "   " : String.format(" %s ", operator);
    final MixedFractionPanel mfp = new MixedFractionPanel(mf, fractionStylePublisher.getStyle(),
        fractionModePublisher.getProper(), fractionModePublisher.getReduced());

    fractionStylePublisher.addSubscriber(mfp);
    fractionModePublisher.addSubscriber(mfp);

    g.gridx = 0;
    g.gridy = 0;
    JLabel operatorLabel = new JLabel(operatorText);
    operatorLabel.setFont(font);
    add(operatorLabel, g);

    g.gridx = 1;
    g.weightx = 1;
    add(mfp, g);

    g.gridx = 0;
    g.gridy = 1;
    g.weightx = 0;
    operatorLabel = new JLabel(operatorText);
    operatorLabel.setFont(font);
    add(operatorLabel, g);

    // add the pies
    g.gridx = 1;
    g.weightx = 1;
    g.weighty = 1;
    g.fill = GridBagConstraints.BOTH;

    final int numExists = (reduced.getNum() == 0) ? 0 : 1;
    // int wholePlusFrac = reduced.getWhole() + numExists;
    // int numPies = (wholePlusFrac < 5) ? wholePlusFrac : 5;
    final JPanel pies = new JPanel(new GridLayout(0, 5));
    pies.setOpaque(false);
    if (reduced.getWhole() + numExists <= 5)
    {
      for (int i = 0; i < reduced.getWhole(); i++)
      {
        pies.add(new PieFull());
      }
    }
    else
    {
      pies.add(new PieNumber(reduced.getWhole()));
    }

    if (numExists == 1)
    {
      pies.add(new PieFraction(reduced));
    }
    add(pies, g);
  }

  public static void main(final String[] args)
  {
    final Frame f = new Frame("test");
    final FractionStylePublisher fsp = new FractionStylePublisher();
    final FractionModePublisher fmp = new FractionModePublisher();

    final var gridLayout = new GridLayout(0, 3);
    gridLayout.setVgap(20);
    f.setLayout(gridLayout);
    f.add(new PieChartCell(fsp, fmp, new MixedFraction(1, 4, 2, 3), "+"));
    f.add(new PieChartCell(fsp, fmp, new MixedFraction(1, 5, 2, 3), "+"));
    f.add(new PieChartCell(fsp, fmp, new MixedFraction(1, 5, 2, 3), "+"));
    f.add(new PieChartCell(fsp, fmp, new MixedFraction(1, 0, 5, 6), "="));

    f.pack();
    f.setVisible(true);
  }
}
