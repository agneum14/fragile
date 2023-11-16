package gui;

import calculating.CurrentMixedFraction;
import calculating.FractionModePublisher;
import calculating.FractionStylePublisher;
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
 *          This code complies with the JMU Honor Code.
 */
public class Display extends JPanel
{
  public enum Op
  {
    ADD, SUB, DIV, MULT, EQUAL, MED
  }

  public static final Color POWDER_BLUE = new Color(210, 237, 255);
  private JPanel cep;
  private JPanel cmfp;
  private CurrentMixedFraction cmf;
  private MixedFraction eval;
  private Op cop;
  private PieChartWindow pcw;
  private FractionStylePublisher fractionStylePublisher;
  private History history;
  private FractionModePublisher fractionModePublisher;

  public Display(PieChartWindow pcw, FractionStylePublisher fractionStylePublisher,
      FractionModePublisher fractionModePublisher, History history)
  {
    setBackground(POWDER_BLUE);
    setLayout(new GridBagLayout());
    this.pcw = pcw;
    this.fractionStylePublisher = fractionStylePublisher;
    this.fractionModePublisher = fractionModePublisher;
    this.history = history;
    eval = new MixedFraction(1, 0, 0, 1);
    cep = new JPanel(new FlowLayout(FlowLayout.LEFT));
    cep.setBackground(POWDER_BLUE);
    cmf = new CurrentMixedFraction();
    cmfp = new CurrentMixedFractionPanel(cmf);
    cop = null;

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
      case CalculatorButtons.MEDIANT -> Op.MED;
      default -> throw new IllegalArgumentException("action command isn't an operator");
    };
  }

  public void addToCEP(MixedFractionPanel p)
  {
    cep.add(p);
    fractionStylePublisher.addSubscriber(p);
    fractionModePublisher.addSubscriber(p);
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
    cep.setBackground(POWDER_BLUE);
    draw();
  }

  public void reset()
  {
    fractionStylePublisher.removeAllSubscribers();
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
    else if (ac.equals(CalculatorButtons.BACK_SPACE))
    {
      cmf.removeDigit();
      updateCMFP();
    }
    else if (ac.equals(CalculatorButtons.SIMPLIFY))
    {
      try
      {
        cmf.simplify();
        updateCMFP();
      }
      catch (IllegalArgumentException arg)
      {
        JOptionPane.showMessageDialog(null, arg.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
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
      cop = null;
      eval = new MixedFraction(1, 0, 0, 1);
      pcw.draw(eval);
    }
    else if (ac.equals(CalculatorButtons.CLEAR))
    {
      clear();
    }
    else if (ac.equals(CalculatorButtons.EQUALS) || ac.equals(CalculatorButtons.ADDITION)
        || ac.equals(CalculatorButtons.SUBTRACTION) || ac.equals(CalculatorButtons.MULTIPLICATION)
        || ac.equals(CalculatorButtons.DIVISION) || ac.equals(CalculatorButtons.MEDIANT))
    {
      if (cop == Op.EQUAL)
      {
        if (cmf.getWhole() == null && cmf.getNum() == null && cmf.getDenom() == null)
        {
          acToOp(ac);
          reset();
          addToCEP(createMixedFractionPanel(eval));
          addToCEP(new JLabel(ac));

          return;
        }
      }

      MixedFraction mf;
      try
      {
        mf = new MixedFraction(cmf);
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
      else if (cop == Op.MED)
      {
        try
        {
          eval = MixedFraction.mediant(eval, mf);
        }
        catch (IllegalArgumentException ae)
        {
          JOptionPane.showMessageDialog(null, ae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
      else if (cop == Op.DIV)
      {
        try
        {
          eval = MixedFraction.div(eval, mf);
        }
        catch (ArithmeticException ae)
        {
          JOptionPane.showMessageDialog(null, ae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }
      }
      else if (cop == Op.EQUAL)
      {
        eval = mf;

        clearCEP();
      }

      addToCEP(createMixedFractionPanel(mf));
      addToCEP(new JLabel(ac));

      if (ac.equals(CalculatorButtons.EQUALS))
      {
        pcw.draw(eval);
        addToCEP(createMixedFractionPanel(eval));

      }

      clear();

      acToOp(ac);
    }
  }

  public MixedFractionPanel createMixedFractionPanel(final MixedFraction mf)
  {
    return new MixedFractionPanel(mf, fractionStylePublisher.getStyle(),
        fractionModePublisher.getProper(), fractionModePublisher.getReduced());
  }
}
