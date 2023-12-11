package gui;

import calculating.*;
import gui.mf.CurrentMixedFractionPanel;
import gui.mf.MixedFractionPanel;
import utilities.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which represents the display of the calculator window. It also orchestrates the evaluation
 * of expressions and updates history and the pie chart window.
 *
 * This code complies with the JMU Honor Code.
 *
 * @author Andrew G. Neumann
 * @version 11/2/2023
 */
public class Display extends JPanel
{

  private static final long serialVersionUID = 1L;

  /**
   * All possible operators. These correspond directly to calculator buttons
   */
  public enum Operator implements ExpressionElement
  {
    ADD, SUB, DIV, MULT, EQUAL, MED, INV, GREATER, LESS, EQUAL_TO, OPEN_PAREN, CLOSE_PAREN;

    /**
     * Override toString to get the CalculatorButtons String.
     */
    @Override
    public String toString()
    {
      return switch (this)
      {
        case ADD -> CalculatorButtons.ADDITION;
        case SUB -> CalculatorButtons.SUBTRACTION;
        case DIV -> CalculatorButtons.DIVISION;
        case MULT -> CalculatorButtons.MULTIPLICATION;
        case EQUAL -> CalculatorButtons.EQUALS;
        case MED -> CalculatorButtons.MEDIANT;
        case INV -> CalculatorButtons.INVERSE;
        case GREATER -> CalculatorButtons.GREATER_THAN;
        case LESS -> CalculatorButtons.LESS_THAN;
        case EQUAL_TO -> CalculatorButtons.EQUAL_TO;
        case OPEN_PAREN -> CalculatorButtons.OPEN_PAREN;
        case CLOSE_PAREN -> CalculatorButtons.CLOSE_PAREN;
      };
    }
  }

  private static final String ERROR_TITLE = "Error";
  private static Color displayColor = new Color(210, 237, 255);
  private final List<ExpressionElement> currentExpression;
  private JPanel currentExpressionPanel;
  private JPanel currentMixedFractionPanel;
  private CurrentMixedFraction currentMixedFraction;
  private final PieChartWindow pieChartWindow;
  private final History history;
  private final IntermediateSteps intermediateSteps;
  private final List<List<ExpressionElement>> steps;
  private final List<MixedFractionPanel> mfps;
  private Operator previousOperator;
  private MixedFraction eval;
  private boolean condExecuted;

  /**
   * Instantiate necessary objects and set up the layout.
   *
   * @param pieChartWindow
   *          The infamous pie chart window
   * @param history
   *          The history
   * 
   * @param intermediateSteps
   *          the steps inbetween the calculation.
   */
  public Display(final PieChartWindow pieChartWindow, final History history,
      final IntermediateSteps intermediateSteps)
  {
    GuiConfig gc = GuiConfig.newInstance();
    displayColor = gc.getColor();
    this.currentExpression = new ArrayList<>();
    this.pieChartWindow = pieChartWindow;
    this.history = history;
    this.intermediateSteps = intermediateSteps;
    currentExpressionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    currentExpressionPanel.setBackground(displayColor);
    currentMixedFraction = new CurrentMixedFraction();
    currentMixedFractionPanel = new CurrentMixedFractionPanel(currentMixedFraction);
    steps = new ArrayList<>();
    mfps = new ArrayList<>();
    previousOperator = null;
    eval = null;
    condExecuted = false;

    setBackground(displayColor);
    setLayout(new GridBagLayout());
    draw();
  }

  /**
   * Gets the display color.
   * 
   * @return the display color.
   */
  public static Color getColor()
  {
    return displayColor;
  }

  /**
   * Redraw the display to match the current state. This method is called whenever one of the visual
   * components updates.
   */
  private void draw()
  {
    GridBagConstraints c;
    removeAll();
    // add current expression panel
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.anchor = GridBagConstraints.NORTHWEST;
    add(currentExpressionPanel, c);

    // float current expression panel west
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 0;
    c.weightx = 1;
    add(Box.createHorizontalStrut(10), c);

    // float current expression panel north
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 1;
    c.weighty = 1;
    add(Box.createHorizontalStrut(10), c);

    // add current mixed fraction panel
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 2;
    c.anchor = GridBagConstraints.SOUTHEAST;
    add(currentMixedFractionPanel, c);

    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 2;
    c.weightx = 1;
    add(Box.createHorizontalStrut(10), c);

    revalidate();
    repaint();
  }

  /**
   * A utility method to convert an action command to an operator.
   *
   * @param actionCommand
   *          The action command
   * @return The corresponding operator
   * @throws IllegalArgumentException
   *           If the action command isn't a valid operator
   */
  private Operator operatorFromActionCommand(final String actionCommand)
      throws IllegalArgumentException
  {
    return switch (actionCommand)
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

  /**
   * Add a mixed fraction panel to the current expression panel and redraw the display.
   *
   * @param p
   *          The mixed fraction panel to add
   */
  public void addToCurrentExpressionPanel(final MixedFractionPanel p)
  {
    currentExpressionPanel.add(p);
    mfps.add(p);
    draw();
  }

  /**
   * Add an action command (which is just a String, so really any String) to the current expression
   * panel as a JLabel.
   *
   * @param actionCommand
   *          The action command to add
   */
  public void addToCurrentExpressionPanel(final String actionCommand)
  {
    final JLabel operatorLabel = new JLabel(actionCommand);
    currentExpressionPanel.add(operatorLabel);
    draw();
  }

  /**
   * Clear the current expression panel by assigning it to a new JPanel and updating the display.
   */
  public void clearCurrentExpressionPanel()
  {
    currentExpressionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    currentExpressionPanel.setBackground(displayColor);
    for (MixedFractionPanel mfp : mfps)
    {
      FractionModePublisher.getInstance().removeSubscriber(mfp);
      FractionStylePublisher.getInstance().removeSubscriber(mfp);
    }
    draw();
  }

  /**
   * Reset the state of display. The current expression panel, current mixed fraction panel, current
   * expression are cleared, and the pie chart window are cleared. History continues to the next
   * expression.
   */
  public void reset()
  {
    eval = null;
    clearCurrentExpressionPanel();
    clearCurrentMixedFractionPanel();
    currentExpression.removeAll(currentExpression);
    draw();
    pieChartWindow.reset();
    intermediateSteps.reset();
  }

  /**
   * Update the current mixed fraction panel to reflect the current mixed fraction and update the
   * display.
   */
  public void updateCurrentMixedFractionPanel()
  {
    currentMixedFractionPanel = new CurrentMixedFractionPanel(currentMixedFraction);
    draw();
  }

  /**
   * Clear the current mixed fraction and panel, then update the display.
   */
  public void clearCurrentMixedFractionPanel()
  {
    currentMixedFraction = new CurrentMixedFraction();
    updateCurrentMixedFractionPanel();
  }

  /**
   * Set the current mixed fraction and panel to the given mixed fraction and update the display.
   * This is used for copy and paste functionality in MixedFractionPanel.
   *
   * @param mixedFraction
   *          The MixedFraction to paste
   */
  public void setCurrentMixedFractionPanel(final MixedFraction mixedFraction)
  {
    currentMixedFraction = new CurrentMixedFraction(mixedFraction);
    updateCurrentMixedFractionPanel();
  }

  /**
   * Shows a dialog for a divide by zero error.
   */
  private void showDivideByZeroDialog()
  {
    final String error = Language.translate("Divided by 0", "Divisé par 0", "Geteilt durch 0");
    JOptionPane.showMessageDialog(null, error, ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
  }

  /**
   * This method drives the core functionality of the program, handling every calculator button
   * press and responding accordingly. The basic overview is certain buttons modify the current
   * mixed fraction while others add operators to the current expression. The equals button is
   * particularly important, since it evaluates the expression and updates the pie chart window and
   * history.
   *
   * @param actionEvent
   *          The action event to respond to
   */
  public void handleButton(final ActionEvent actionEvent)
  {
    final String actionCommand = actionEvent.getActionCommand();
    Integer digit;
    Operator operator = null;

    if (condExecuted)
    {
      condExecuted = false;
      reset();
    }

    try
    {
      digit = Integer.parseInt(actionCommand);
    }
    catch (final NumberFormatException nfe)
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
      catch (final IllegalArgumentException arg)
      {
        JOptionPane.showMessageDialog(null, arg.getMessage(), ERROR_TITLE,
            JOptionPane.ERROR_MESSAGE);
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
    }
    else if (actionCommand.equals(CalculatorButtons.ADDITION)
        || actionCommand.equals(CalculatorButtons.SUBTRACTION)
        || actionCommand.equals(CalculatorButtons.MULTIPLICATION)
        || actionCommand.equals(CalculatorButtons.DIVISION)
        || actionCommand.equals(CalculatorButtons.GREATER_THAN)
        || actionCommand.equals(CalculatorButtons.LESS_THAN)
        || actionCommand.equals(CalculatorButtons.EQUAL_TO)
        || actionCommand.equals(CalculatorButtons.OPEN_PAREN)
        || actionCommand.equals(CalculatorButtons.CLOSE_PAREN)
        || actionCommand.equals(CalculatorButtons.EQUALS)
        || actionCommand.equals(CalculatorButtons.MEDIANT))
    {

      operator = operatorFromActionCommand(actionCommand);

      if (previousOperator != Operator.CLOSE_PAREN && operator != Operator.OPEN_PAREN)
      {
        // MixedFraction mf = (eval != null) ? eval : new
        // MixedFraction(currentMixedFraction);
        MixedFraction mf;
        if (eval != null)
        {
          mf = eval;
          eval = null;
          reset();
        }
        else
        {
          try
          {
            mf = new MixedFraction(currentMixedFraction);
          }
          catch (IllegalArgumentException e)
          {
            showDivideByZeroDialog();
            return;
          }
        }
        addToCurrentExpressionPanel(new MixedFractionPanel(mf, this));
        currentExpression.add(mf);
        clearCurrentMixedFractionPanel();
      }

      addToCurrentExpressionPanel(actionCommand);
      if (operator != Operator.EQUAL)
      {
        currentExpression.add(operator);

      }
      else
      {
        // determine if the expression is conditional
        if (currentExpression.contains(Operator.LESS)
            || currentExpression.contains(Operator.GREATER)
            || currentExpression.contains(Operator.EQUAL_TO))
        {
          Boolean result = null;
          condExecuted = true;

          try
          {
            result = ConditionalExpressionEvaluator.evaluate(currentExpression);
          }
          catch (final IllegalArgumentException e)
          {
            final String error = Language.translate("The conditional expression is malformed",
                "L'expression conditionnelle est mal formée",
                "Die bedingte Ausdruck ist fehlerhaft.");
            JOptionPane.showMessageDialog(null, error, ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
          }
          catch (final ArithmeticException e)
          {
            showDivideByZeroDialog();
          }

          if (result != null)
          {
            String res;
            if (result)
            {
              res = Language.translate("True", "Vrai", "Wahr");
            }
            else
            {
              res = Language.translate("False", "Faux", "Falsch");
            }
            JOptionPane.showMessageDialog(null, res, "Info", JOptionPane.ERROR_MESSAGE);
          }
        }
        else
        {
          MixedFraction result = null;
          try
          {
            final ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator(
                currentExpression, steps);
            result = expressionEvaluator.evaluate();
          }
          catch (final IllegalArgumentException e)
          {
            if (e.getMessage().equals("expression can't be empty"))
            {
              final String error = Language.translate("Expression can't be empty",
                  "L'expression ne peut pas être vide", "Der Ausdruck darf nicht leer sein");
              JOptionPane.showMessageDialog(null, error, ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
            }
          }
          catch (final IllegalStateException e)
          {
            final String error = Language.translate("The expression is malformed",
                "L'expression est mal formée", "Der Ausdruck ist fehlerhaft");
            JOptionPane.showMessageDialog(null, error, ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
          }
          catch (final ArithmeticException e)
          {
            showDivideByZeroDialog();
          }

          if (result != null)
          {
            addToCurrentExpressionPanel(new MixedFractionPanel(result, this));
            currentExpression.add(Operator.EQUAL);
            currentExpression.add(result);
            pieChartWindow.update(currentExpression);
            history.update(currentExpression, this);
            intermediateSteps.update(steps);
            eval = result;
          }
        }
      }

      if (operator != null)
      {
        previousOperator = operator;
      }
    }
  }
}
