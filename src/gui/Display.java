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

  public enum Operator
  {
    ADD, SUB, DIV, MULT, EQUAL, MED, INV
  }

  public static final Color POWDER_BLUE = new Color(210, 237, 255);
  private JPanel cep;
  private JPanel cmfp;
  private CurrentMixedFraction cmf;
  private MixedFraction eval;
  private Operator previousOperator;
  private String previousActionCommand;
  private PieChartWindow pcw;
  private History history;

  public Display(PieChartWindow pcw, History history)
  {
    setBackground(POWDER_BLUE);
    setLayout(new GridBagLayout());
    this.pcw = pcw;
    this.history = history;
    eval = new MixedFraction(1, 0, 0, 1);
    cep = new JPanel(new FlowLayout(FlowLayout.LEFT));
    cep.setBackground(POWDER_BLUE);
    cmf = new CurrentMixedFraction();
    cmfp = new CurrentMixedFractionPanel(cmf);
    previousOperator = null;
    this.history = history;
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

  private Operator acToOp(String ac) throws IllegalArgumentException
  {
    return switch (ac)
    {
      case CalculatorButtons.EQUALS -> Operator.EQUAL;
      case CalculatorButtons.ADDITION -> Operator.ADD;
      case CalculatorButtons.SUBTRACTION -> Operator.SUB;
      case CalculatorButtons.MULTIPLICATION -> Operator.MULT;
      case CalculatorButtons.MEDIANT -> Operator.MED;
      case CalculatorButtons.DIVISION -> Operator.DIV;
      default -> throw new IllegalArgumentException("action command isn't an operator");
    };
  }

  public void addToCEP(MixedFractionPanel p)
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
    cep.setBackground(POWDER_BLUE);
    draw();
  }

  public void reset()
  {
    clearCEP();
    clearCMFP();
    draw();
    previousActionCommand = null;
    previousOperator = null;
    pcw.reset();
    history.nextExpression();
  }

  public void updateCMFP()
  {
    cmfp = new CurrentMixedFractionPanel(cmf);
    draw();
  }

  public void clearCMFP()
  {
    cmf = new CurrentMixedFraction();
    updateCMFP();
  }

  public void setCMFP(MixedFraction mf)
  {
    cmf = new CurrentMixedFraction(mf);
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
    else if (ac.equals(CalculatorButtons.INVERSE))
    {
      cmf.invert();
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
      previousOperator = null;
      eval = new MixedFraction(1, 0, 0, 1);
    }
    else if (ac.equals(CalculatorButtons.CLEAR))
    {
      clearCMFP();
    }
    else if (ac.equals(CalculatorButtons.EQUALS) || ac.equals(
        CalculatorButtons.ADDITION) || ac.equals(CalculatorButtons.SUBTRACTION) || ac.equals(
        CalculatorButtons.MULTIPLICATION) || ac.equals(CalculatorButtons.DIVISION) || ac.equals(
        CalculatorButtons.MEDIANT))
    {
      MixedFraction mf = new MixedFraction(cmf);
      if (previousOperator == null)
      {
        eval = mf;
      }
      else if (previousOperator == Operator.ADD)
      {
        eval = MixedFraction.add(eval, mf);
      }
      else if (previousOperator == Operator.SUB)
      {
        eval = MixedFraction.sub(eval, mf);
      }
      else if (previousOperator == Operator.MED)
      {
        eval = MixedFraction.mediant(eval, mf);
      }
      else if (previousOperator == Operator.MULT)
      {
        eval = MixedFraction.mult(eval, mf);
      }
      else if (previousOperator == Operator.DIV)
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

      Operator currentOperator = acToOp(ac);
      if (previousOperator == Operator.EQUAL)
      {
        reset();
        addToCEP(createMixedFractionPanel(eval));
        addToCEP(new JLabel(ac));
        pcw.addCell(eval, null, this);
        history.nextExpression();
        history.addToExpression(createMixedFractionPanel(eval), null);
      }
      else
      {
        MixedFractionPanel mfp = createMixedFractionPanel(mf);
        addToCEP(mfp);
        addToCEP(new JLabel(ac));
        history.addToExpression(createMixedFractionPanel(mf), previousActionCommand);
        pcw.addCell(mf, previousActionCommand, this);

        if (currentOperator == Operator.EQUAL)
        {
          addToCEP(createMixedFractionPanel(eval));
          pcw.addCell(eval, ac, this);
          history.addToExpression(createMixedFractionPanel(eval), ac);
        }
      }

      clearCMFP();
      previousOperator = currentOperator;
      previousActionCommand = ac;
    }
  }

  public MixedFractionPanel createMixedFractionPanel(final MixedFraction mf)
  {
    MixedFractionPanel mfp = new MixedFractionPanel(mf, this);
    return mfp;
  }

  public void copy(final JPanel panel)
  {
    this.cmfp = (MixedFractionPanel) panel;
  }

}
