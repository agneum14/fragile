package gui;

import calculating.CurrentMixedFraction;
import calculating.ExpressionElement;
import calculating.ExpressionEvaluator;
import calculating.MixedFraction;
import gui.mf.CurrentMixedFractionPanel;
import gui.mf.MixedFractionPanel;
import utilities.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

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

  public enum Operator implements ExpressionElement
  {
    ADD, SUB, DIV, MULT, EQUAL, MED, INV, GREATER, LESS, EQUAL_TO, OPEN_PAREN, CLOSE_PAREN
  }

  public static final Color POWDER_BLUE = new Color(210, 237, 255);
  private List<ExpressionElement> currentExpression;
  private JPanel currentExpressionPanel;
  private JPanel currentMixedFractionPanel;
  private CurrentMixedFraction currentMixedFraction;
  private PieChartWindow pieChartWindow;
  private History history;
  private List<List<ExpressionElement>> steps;

  public Display(PieChartWindow pieChartWindow, History history)
  {
    this.currentExpression = new ArrayList<>();
    this.pieChartWindow = pieChartWindow;
    this.history = history;
    currentExpressionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    currentExpressionPanel.setBackground(POWDER_BLUE);
    currentMixedFraction = new CurrentMixedFraction();
    currentMixedFractionPanel = new CurrentMixedFractionPanel(currentMixedFraction);
    steps = new ArrayList<>();

    setBackground(POWDER_BLUE);
    setLayout(new GridBagLayout());
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
      add(currentExpressionPanel, c);
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
      add(currentMixedFractionPanel, c);
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

  private Operator operatorFromActionCommand(String ac) throws IllegalArgumentException
  {
    return switch (ac)
    {
      case CalculatorButtons.EQUALS -> Operator.EQUAL;
      case CalculatorButtons.ADDITION -> Operator.ADD;
      case CalculatorButtons.SUBTRACTION -> Operator.SUB;
      case CalculatorButtons.MULTIPLICATION -> Operator.MULT;
      case CalculatorButtons.MEDIANT -> Operator.MED;
      case CalculatorButtons.DIVISION -> Operator.DIV;
      case CalculatorButtons.GREATER_THAN -> Operator.GREATER;
      case CalculatorButtons.LESS_THAN -> Operator.LESS;
      case CalculatorButtons.EQUAL_TO -> Operator.EQUAL_TO;
      case CalculatorButtons.OPEN_PAREN -> Operator.OPEN_PAREN;
      case CalculatorButtons.CLOSE_PAREN -> Operator.CLOSE_PAREN;
      default -> throw new IllegalArgumentException("action command isn't an operator");
    };
  }

  public void addToCurrentExpressionPanel(MixedFractionPanel p)
  {
    currentExpressionPanel.add(p);
    draw();
  }

  public void addToCurrentExpressionPanel(String actionCommand)
  {
    JLabel operatorLabel = new JLabel(actionCommand);
    currentExpressionPanel.add(operatorLabel);
    draw();
  }

  public void clearCurrentExpressionPanel()
  {
    currentExpressionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    currentExpressionPanel.setBackground(POWDER_BLUE);
    draw();
  }

  public void reset()
  {
    clearCurrentExpressionPanel();
    clearCurrentMixedFractionPanel();
    draw();
    pieChartWindow.reset();
    history.nextExpression();
  }

  public void updateCurrentMixedFractionPanel()
  {
    currentMixedFractionPanel = new CurrentMixedFractionPanel(currentMixedFraction);
    draw();
  }

  public void clearCurrentMixedFractionPanel()
  {
    currentMixedFraction = new CurrentMixedFraction();
    updateCurrentMixedFractionPanel();
  }

  public void setCurrentMixedFractionPanel(MixedFraction mf)
  {
    currentMixedFraction = new CurrentMixedFraction(mf);
    updateCurrentMixedFractionPanel();
  }

  public void handleButton(ActionEvent ae)
  {
    final String actionCommand = ae.getActionCommand();
    Integer digit;
    try
    {
      digit = Integer.parseInt(actionCommand);
    }
    catch (NumberFormatException nfe)
    {
      digit = null;
    }
    if (digit != null)
    {
      currentMixedFraction.addDigit(digit);
      updateCurrentMixedFractionPanel();
    }
    else if (actionCommand.equals(CalculatorButtons.BACK_SPACE))
    {
      currentMixedFraction.removeDigit();
      updateCurrentMixedFractionPanel();
    }
    else if (actionCommand.equals(CalculatorButtons.SIMPLIFY))
    {
      try
      {
        currentMixedFraction.simplify();
        updateCurrentMixedFractionPanel();
      }
      catch (IllegalArgumentException arg)
      {
        JOptionPane.showMessageDialog(null, arg.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
    else if (actionCommand.equals(CalculatorButtons.INVERSE))
    {
      currentMixedFraction.invert();
      updateCurrentMixedFractionPanel();
    }
    else if (actionCommand.equals(CalculatorButtons.SIGN))
    {
      currentMixedFraction.changeSign();
      updateCurrentMixedFractionPanel();
    }
    else if (actionCommand.equals(CalculatorButtons.POSITION))
    {
      currentMixedFraction.nextPos();
      updateCurrentMixedFractionPanel();
    }
    else if (actionCommand.equals(CalculatorButtons.RESET))
    {
      reset();
    }
    else if (actionCommand.equals(CalculatorButtons.CLEAR))
    {
      clearCurrentMixedFractionPanel();
      // ADD, SUB, DIV, MULT, EQUAL, MED, INV, GREATER, LESS, EQUAL_TO, OPEN_PAREN,
      // CLOSE_PAREN
    }
    else if (actionCommand.equals(CalculatorButtons.ADDITION) || actionCommand.equals(
        CalculatorButtons.SUBTRACTION) || actionCommand.equals(
        CalculatorButtons.MULTIPLICATION) || actionCommand.equals(
        CalculatorButtons.DIVISION) || actionCommand.equals(
        CalculatorButtons.GREATER_THAN) || actionCommand.equals(
        CalculatorButtons.LESS_THAN) || actionCommand.equals(
        CalculatorButtons.EQUAL_TO) || actionCommand.equals(
        CalculatorButtons.OPEN_PAREN) || actionCommand.equals(CalculatorButtons.CLOSE_PAREN))
    {
      Operator operator = operatorFromActionCommand(actionCommand);
      addToCurrentExpressionPanel(actionCommand);
      currentExpression.add(operator);
    }
    else if (actionCommand.equals(CalculatorButtons.SEND))
    {
      MixedFraction mf = new MixedFraction(currentMixedFraction);
      addToCurrentExpressionPanel(new MixedFractionPanel(mf, this));
      currentExpression.add(mf);
      clearCurrentMixedFractionPanel();
    }
    else if (actionCommand.equals(CalculatorButtons.EQUALS))
    {
      MixedFraction result = null;
      try
      {
        ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator(currentExpression, steps);
        result = expressionEvaluator.evaluate();
      }
      catch (IllegalArgumentException e)
      {
        if (e.getMessage() == "expression can't be empty")
        {
          String error = Language.translate("Expression can't be empty",
              "L'expression ne peut pas être vide", "Der Ausdruck darf nicht leer sein");
          JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
          throw e;
        }
      }
      catch (IllegalStateException e)
      {
        String error = Language.translate("The expression is malformed",
            "L'expression est mal formée", "Der Ausdruck ist fehlerhaft");
        JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
      }

      if (result != null)
      {
        addToCurrentExpressionPanel("=");
        addToCurrentExpressionPanel(new MixedFractionPanel(result, this));
        currentExpression.add(Operator.EQUAL);
        currentExpression.add(result);
      }
    }
  }

  public MixedFractionPanel createMixedFractionPanel(final MixedFraction mf)
  {
    MixedFractionPanel mfp = new MixedFractionPanel(mf, this);
    return mfp;
  }

  public void copy(final JPanel panel)
  {
    this.currentMixedFractionPanel = (MixedFractionPanel) panel;
  }

}
