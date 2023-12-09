package gui;

import calculating.ExpressionElement;
import calculating.MixedFraction;
import gui.Display.Operator;
import utilities.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * This class implements the pie chart window, which visualizes the current mixed fraction
 * evaluation as pies. The whole number is represented by multiple full pies,and the fractional
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
  private final JPanel expression;

  /**
   * Construct the pie chart window.
   */
  public PieChartWindow()
  {
    super("Pie Chart");
    final GridBagConstraints g = new GridBagConstraints();

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
    final var gridLayout = new GridLayout(0, 3);
    gridLayout.setVgap(20);
    expression = new JPanel(new FlowLayout(FlowLayout.LEFT));
    expression.setOpaque(false);
    add(expression, g);

    g.gridy = 2;
    g.weighty = 1;
    add(new JPanel(), g);

    addWindowListener(new WindowAdapter()
    {
      /**
       * Toggle visibility instead of closing the window.
       */
      @Override
      public void windowClosed(final WindowEvent e)
      {
        toggleVisibility();
      }
    });
  }

  /**
   * Update the pie chart window to reflect the current expression.
   *
   * @param currentExpression
   *     The current expression
   */
  public void update(final List<ExpressionElement> currentExpression)
  {
    expression.removeAll();

    for (final ExpressionElement ee : currentExpression)
    {
      if (ee instanceof MixedFraction)
      {
        final MixedFraction mf = (MixedFraction) ee;
        addOperand(mf);
      }
      else
      {
        final Operator operator = (Operator) ee;
        final String operatorString = switch (operator)
        {
          case EQUAL -> CalculatorButtons.EQUALS;
          case ADD -> CalculatorButtons.ADDITION;
          case SUB -> CalculatorButtons.SUBTRACTION;
          case MULT -> CalculatorButtons.MULTIPLICATION;
          case MED -> CalculatorButtons.MEDIANT;
          case DIV -> CalculatorButtons.DIVISION;
          case GREATER -> CalculatorButtons.GREATER_THAN;
          case LESS -> CalculatorButtons.LESS_THAN;
          case EQUAL_TO -> CalculatorButtons.EQUAL_TO;
          case OPEN_PAREN -> CalculatorButtons.OPEN_PAREN;
          default -> CalculatorButtons.CLOSE_PAREN;
        };
        addOperator(operatorString);
      }
    }

    revalidate();
    repaint();
  }

  /**
   * Add a JLabel with the operator to the expression.
   *
   * @param operator
   *     The operator to add
   */
  private void addOperator(final String operator)
  {
    final JLabel operatorLabel = new JLabel(operator);
    final Font font = new Font("SansSerif", Font.PLAIN, 24);
    operatorLabel.setFont(font);
    expression.add(operatorLabel);
  }

  /**
   * Add a mixed fraction (an operand) to the expression.
   *
   * @param mf
   *     The mixed fraction to add
   */
  private void addOperand(final MixedFraction mf)
  {
    final MixedFraction reduced = new MixedFraction(mf).reduce();
    final int numExists = (reduced.getNum() == 0) ? 0 : 1;
    final JPanel pies = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
    expression.add(pies);

  }

  /**
   * Clear the expression and repaint.
   */
  public void reset()
  {
    expression.removeAll();
    revalidate();
    repaint();
  }

  /**
   * Toggle the visibility.
   */
  public void toggleVisibility()
  {
    setVisible(!isVisible());
  }
}
