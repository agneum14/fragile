package gui.mf;

import calculating.FractionStylePublisher.FractionStyle;
import calculating.FractionStyleSubscriber;
import calculating.MixedFraction;
import gui.Display;
import gui.HorizontalLine;
import gui.JPanelBuilder;

import javax.swing.*;
import java.awt.*;

/**
 * This class visually represents a MixedFraction with a JPanel. The minus sign (if it exists) is
 * displayed leftmost, followed by the whole number, followed by the fraction. The fraction (as in
 * not the negative sign or the whole number) is comprised the numerator, solidus, and denominator
 * in a vertical stack. The fraction will not be displayed if the numerator is 0.
 *
 * This work complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class MixedFractionPanel extends JPanel implements FractionStyleSubscriber
{
  protected static final int MINUS_SIZE = 10;
  private final int sign;
  private Integer whole;
  private Integer num;
  private Integer denom;
  protected JPanel signPanel;
  protected JPanel wholePanel;
  protected JPanel numPanel;
  protected JPanel denomPanel;
  protected JPanel fractionPanel;

  /**
   * This constructor constructs the JPanel from the given MixedFraction.
   *
   * @param mf
   *     The MixedFraction to display
   */
  public MixedFractionPanel(final MixedFraction mf)
  {
    this(mf.getSign(), mf.getWhole(), mf.getNum(), mf.getDenom());
  }

  /**
   * This constructor constructs the JPanel given the sign, whole, num, and denom of a mixed
   * fraction. For this class, this constructor is called from the constructor that accepts a
   * MixedFraction, the Integer values will never be null. This exists for compatbility with the
   * CurrentMixedFractionPanel class, which extends this class
   *
   * @param sign
   *     The sign of the mixed fraction
   * @param whole
   *     The whole number of the mixed fraction
   * @param num
   *     The numerator of the mixed fraction
   * @param denom
   *     The denominator of the mixed fraction
   */
  protected MixedFractionPanel(final int sign, final Integer whole, final Integer num,
      final Integer denom)
  {
    this.sign = sign;
    this.whole = whole;
    this.num = num;
    this.denom = denom;

    setLayout(new FlowLayout(FlowLayout.LEFT));
    setBackground(Display.POWDER_BLUE);

    setSignPanel();
    setWholePanel();
    setNumPanel();
    setDenomPanel();

    styleAsBar();

    draw();
  }

  /**
   * Add all the panels to the JPanel and repaint. The sign panel is only added if sign is -1, and
   * the fraction panel is only added if num isn't 0
   *
   * @param sign
   *     The sign of the mixed fraction
   * @param num
   *     The numerator of the mixed fraction
   */
  protected void draw()
  {
    removeAll();

    if (sign == -1)
    {
      add(signPanel);
    }
    add(wholePanel);
    if (num != 0)
    {
      add(fractionPanel);
    }

    revalidate();
    repaint();
  }

  /**
   * Set the denom panel.
   *
   * @param denom
   *     The denominator of the mixed fraction
   */
  protected void setDenomPanel()
  {
    final Font fracFont = new Font(getFont().getName(), Font.PLAIN, 18);
    denomPanel = new JPanel(new BorderLayout());
    final JLabel denomLabel = new JLabel(String.valueOf(denom));
    denomLabel.setFont(fracFont);
    denomPanel.add(denomLabel);
    denomPanel.setBackground(Display.POWDER_BLUE);
  }

  /**
   * Set the num panel.
   *
   * @param num
   *     The numerator of the mixed fraction
   */
  protected void setNumPanel()
  {
    final Font fracFont = new Font(getFont().getName(), Font.PLAIN, 18);
    numPanel = new JPanel(new BorderLayout());
    final JLabel numLabel = new JLabel(String.valueOf(num));
    numLabel.setFont(fracFont);
    numPanel.add(numLabel);
    numPanel.setBackground(Display.POWDER_BLUE);
  }

  /**
   * Set the sign panel.
   *
   * @param sign
   *     The sign of the mixed fraction
   */
  protected void setSignPanel()
  {
    if (sign == -1)
    {
      signPanel = new HorizontalLine(MINUS_SIZE);
    }
    else
    {
      signPanel = new JPanel();
    }
    signPanel.setBackground(Display.POWDER_BLUE);
  }

  /**
   * Set the whole panel.
   *
   * @param whole
   *     The sign of the mixed fraction
   */
  protected void setWholePanel()
  {
    wholePanel = new JPanel(new BorderLayout());
    final JLabel wholeLabel = new JLabel(String.valueOf(whole));
    final Font wholeLabelFont = new Font(wholeLabel.getFont().getName(), Font.PLAIN, 25);
    wholeLabel.setFont(wholeLabelFont);
    wholePanel.add(wholeLabel);
    wholePanel.setBackground(Display.POWDER_BLUE);
  }

  private void styleAsBar()
  {
    fractionPanel = new JPanel(new GridBagLayout());
    final GridBagConstraints c = new GridBagConstraints();

    c.gridx = 0;
    c.gridy = 0;
    fractionPanel.add(numPanel, c);

    c.gridy = 1;
    final JPanel denomFullPanel = new JPanel();
    denomFullPanel.setLayout(new BoxLayout(denomFullPanel, BoxLayout.PAGE_AXIS));
    denomFullPanel.add(new JSeparator(JSeparator.HORIZONTAL));
    denomFullPanel.add(denomPanel);
    fractionPanel.add(denomFullPanel, c);
    fractionPanel.setBackground(Display.POWDER_BLUE);
  }

  private void styleAsSlash()
  {
    fractionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    fractionPanel.add(numPanel);
    JLabel solidus = new JLabel("/");
    fractionPanel.add(solidus);
    fractionPanel.add(denomPanel);
    fractionPanel.setBackground(Display.POWDER_BLUE);
  }

  private void styleAsSolidus()
  {
    fractionPanel = new JPanel()
    {
      @Override
      protected void paintComponent(Graphics g)
      {
        super.paintComponent(g);

        g.drawLine(0, getHeight(), getWidth(), 0);
      }
    };

    fractionPanel.setLayout(new GridLayout(0, 2));
    fractionPanel.add(numPanel);
    fractionPanel.add(new JPanelBuilder().transparent());
    fractionPanel.add(new JPanelBuilder().transparent());
    fractionPanel.add(denomPanel);
    fractionPanel.setBackground(Display.POWDER_BLUE);
  }

  @Override
  public void handleStyle(final FractionStyle style)
  {
    switch (style)
    {
      case BAR -> styleAsBar();
      case SLASH -> styleAsSlash();
      default -> styleAsSolidus();
    }

    draw();
  }

  /**
   * Getter for denom.
   *
   * @return denom
   */
  public Integer getDenom()
  {
    return denom;
  }

  /**
   * Getter for num.
   *
   * @return num
   */
  public Integer getNum()
  {
    return num;
  }

  /**
   * Getter for sign.
   *
   * @return sign
   */
  public int getSign()
  {
    return sign;
  }

  /**
   * Getter for whole.
   *
   * @return whole
   */
  public Integer getWhole()
  {
    return whole;
  }
}
