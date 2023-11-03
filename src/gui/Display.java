package gui;

import calculating.CurrentMixedFraction;
import calculating.MixedFraction;
import gui.mf.CurrentMixedFractionPanel;
import gui.mf.MixedFractionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
  public enum Op
  {
    ADD, SUB, DIV, MULT, EQUAL
  }

  private static final Color POWDER_BLUE = new Color(210, 237, 255);
  private JPanel cep;
  private JPanel cmfp;
  private CurrentMixedFraction cmf;
  private MixedFraction eval;
  private Op cop;
  private PieChartWindow pcw;

  public Display(PieChartWindow pcw)
  {
    setBackground(POWDER_BLUE);
    setLayout(new GridBagLayout());

    this.pcw = pcw;
    eval = new MixedFraction(1, 0, 0, 1);
    cep = new JPanel(new FlowLayout(FlowLayout.LEFT));
    cmf = new CurrentMixedFraction();
    cmfp = new CurrentMixedFractionPanel(cmf);
    cop = null;

    draw();
  }

  private void draw()
  {
    GridBagConstraints c;
    removeAll();
    JPanel empty;
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
      add(Box.createHorizontalStrut(10), c);
    }

    // float current expression panel north
    {
      c = new GridBagConstraints();
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 1;
      add(Box.createHorizontalStrut(10), c);
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
      add(Box.createHorizontalStrut(10), c);
    }

    revalidate();
    repaint();
  }

  private void acToOp(String ac) throws IllegalArgumentException
  {
    cop = switch (ac)
    {
      case CalculatorButtons.EQUALS -> Op.EQUAL;
      case CalculatorButtons.ADDITION -> Op.ADD;
      case CalculatorButtons.SUBTRACTION -> Op.SUB;
      case CalculatorButtons.MULTIPLICATION -> Op.MULT;
      case CalculatorButtons.DIVISION -> Op.DIV;
      default -> throw new IllegalArgumentException("action command isn't an operator");
    };
  }

  public void addToCEP(JPanel p)
  {
    cep.add(p);
    draw();
  }

  public void addToCEP(JLabel l)
  {
    cep.add(l);
    draw();
  }

  public void clearCEP()
  {
    cep = new JPanel(new FlowLayout(FlowLayout.LEFT));
    draw();
  }

  public void reset()
  {
    clearCEP();
    clear();
    draw();
  }

  public void updateCMFP()
  {
    cmfp = new CurrentMixedFractionPanel(cmf);
    draw();
  }

  public void clear()
  {
    cmf = new CurrentMixedFraction();
    updateCMFP();
  }

  public void handleButton(ActionEvent e)
  {
    final String ac = e.getActionCommand();

    Integer digit;
    try
    {
      digit = Integer.parseInt(ac);
    }
    catch (NumberFormatException nfe)
    {
      digit = null;
    }

    if (digit != null)
    {
      cmf.addDigit(digit);
      updateCMFP();
    }
    else if (ac.equals(CalculatorButtons.BACKSPACE))
    {
      cmf.removeDigit();
      updateCMFP();
    }
    else if (ac.equals(CalculatorButtons.SIGN))
    {
      cmf.changeSign();
      updateCMFP();
    }
    else if (ac.equals(CalculatorButtons.POSITION))
    {
      cmf.nextPos();
      updateCMFP();
    }
    else if (ac.equals(CalculatorButtons.RESET))
    {
      reset();
    }
    else if (ac.equals(CalculatorButtons.CLEAR))
    {
      clear();
    }
    else if (ac.equals(CalculatorButtons.EQUALS) || ac.equals(
        CalculatorButtons.ADDITION) || ac.equals(CalculatorButtons.SUBTRACTION) || ac.equals(
        CalculatorButtons.MULTIPLICATION) || ac.equals(CalculatorButtons.DIVISION))
    {
      if (cop == Op.EQUAL)
      {
        if (ac != CalculatorButtons.EQUALS)
        {
          acToOp(ac);
          eval = new MixedFraction(cmf.toMixedFraction());
          reset();
          addToCEP(new MixedFractionPanel(eval));
          addToCEP(new JLabel(ac));
        }

        return;
      }

      MixedFraction mf;
      try
      {
        mf = cmf.toMixedFraction();
      }
      catch (IllegalArgumentException ile)
      {
        JOptionPane.showMessageDialog(null, ile.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      if (cop == null)
      {
        eval = mf;
      }
      else if (cop == Op.ADD)
      {
        eval = MixedFraction.add(eval, mf);
      }
      else if (cop == Op.SUB)
      {
        eval = MixedFraction.sub(eval, mf);
      }
      else if (cop == Op.MULT)
      {
        eval = MixedFraction.mult(eval, mf);
      }
      else if (cop == Op.DIV)
      {
        eval = MixedFraction.div(eval, mf);
      }

      addToCEP(new MixedFractionPanel(mf));
      addToCEP(new JLabel(ac));

      if (ac.equals(CalculatorButtons.EQUALS))
      {
        pcw.draw(eval);
        addToCEP(new MixedFractionPanel(eval));
      }

      clear();

      acToOp(ac);
    }
  }
}
