package gui;

import calculating.CurrentMixedFraction;
import gui.mf.CurrentMixedFractionPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Class which represents the display of the calculator window.
 *
 * @author Joshua Hairston
 * @version 11/2/2023
 *
 *     This code complies with the JMU Honor Code.
 */
public class Display extends JPanel
{
  private static final Color POWDER_BLUE = new Color(210, 237, 255);
  private JPanel cep;
  private JPanel cmfp;

  public Display()
  {
    this.setBackground(POWDER_BLUE);
    this.setLayout(new GridBagLayout());
    
    cep = new JPanel(new FlowLayout(FlowLayout.LEFT));
    cmfp = new CurrentMixedFractionPanel(new CurrentMixedFraction());

    draw();
  }

  private void draw()
  {
    GridBagConstraints c;
    removeAll();

    // add current expression panel
    {
      c = new GridBagConstraints();
      c.gridx = 0;
      c.gridy = 0;
      c.anchor = GridBagConstraints.NORTHWEST;
      add(cep, c);
    }

    // float current expression panel west
    {
      c = new GridBagConstraints();
      c.gridx = 1;
      c.gridy = 0;
      c.weightx = 1;
      add(new JPanel(), c);
    }

    // float current expression panel north
    {
      c = new GridBagConstraints();
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 1;
      add(new JPanel(), c);
    }

    // add current mixed fraction panel
    {
      c = new GridBagConstraints();
      c.gridx = 1;
      c.gridy = 2;
      c.anchor = GridBagConstraints.SOUTHEAST;
      add(cmfp, c);
    }

    {
      c = new GridBagConstraints();
      c.gridx = 0;
      c.gridy = 2;
      c.weightx = 1;
      add(new JPanel(), c);
    }

    repaint();
  }

  public void addToCEP(JPanel p)
  {
    cep.add(p);
    draw();
  }

  public void clearCEP()
  {
    cep = new JPanel(new FlowLayout(FlowLayout.LEFT));
    draw();
  }

  public void updateCMFP(CurrentMixedFraction cmf)
  {
    cmfp = new CurrentMixedFractionPanel(cmf);
    draw();
  }
}
