package gui.mf;

import calculating.FractionModeSubscriber;
import calculating.FractionStylePublisher.FractionStyle;
import calculating.FractionStyleSubscriber;
import calculating.MixedFraction;
import gui.Display;
import gui.HorizontalLine;
import gui.JPanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class visually represents a MixedFraction with a JPanel. The sign is displayed leftmost,
 * followed by the whole and the fraction panel. The sign panel isn't displayed unless the sign
 * component is -1. The whole panel isn't displayed if the whole component is 0, unless the
 * numerator component is also 0. The numerator panel isn't displayed if the numerator component is
 * 0. There are 3 fraction panel styles as detailed in the Domain Glossary: bar, slash, and solidus.
 * MixedFractionPanels listen for changes in this style and update the fraction panel accordingly.
 * There are two fraction modes: proper and reduced, also defined in the Domain Glossary. Changes in
 * these modes are listened for as well.
 *
 * This work complies with the JMU Honor Code
 *
 * @author Andrew G. Neumann
 * @version 1.0
 */
public class MixedFractionPanel extends JPanel
    implements FractionStyleSubscriber, FractionModeSubscriber, MouseListener, ActionListener
{
  protected static final int MINUS_SIZE = 10;
  protected JPanel signPanel;
  protected JPanel wholePanel;
  protected JPanel numPanel;
  protected JPanel denomPanel;
  protected JPanel fractionPanel;

  private int sign;
  private Integer whole;
  private Integer num;
  private Integer denom;

  private final FractionStyle style;
  private boolean proper;
  private boolean reduced;
  
  private JPopupMenu cnp;
  private JMenuItem cnpItem;
  

  /**
   * This constructor constructs the JPanel from the given MixedFraction, fraction style, proper
   * mode and reduced mode.
   *
   * @param mf
   *     The MixedFraction to display
   * @param style
   *     The fraction style
   * @param proper
   *     The fraction proper mode
   * @param reduced
   *     The fraction reduced mode
   */
  public MixedFractionPanel(final MixedFraction mf, final FractionStyle style, final boolean proper,
      final boolean reduced)
  {
    this(mf.getSign(), mf.getWhole(), mf.getNum(), mf.getDenom(), style, proper, reduced);
  }

  /**
   * This constructor constructs the JPanel given the sign, whole, num, and denom of a mixed
   * fraction. The fraction style, proper mode, and reduced mode are also specified.
   *
   * @param sign
   *     The sign of the mixed fraction
   * @param whole
   *     The whole number of the mixed fraction
   * @param num
   *     The numerator of the mixed fraction
   * @param denom
   *     The denominator of the mixed fraction
   * @param style
   *     The fraction style
   * @param proper
   *     The proper mode
   * @param reduced
   *     The reduced mode
   */
  protected MixedFractionPanel(final int sign, final Integer whole, final Integer num,
      final Integer denom, final FractionStyle style, final boolean proper, final boolean reduced)
  {
    this.sign = sign;
    this.whole = whole;
    this.num = num;
    this.denom = denom;
    this.style = style;
    this.proper = proper;
    this.reduced = reduced;

    setLayout(new FlowLayout(FlowLayout.LEFT));
    setBackground(Display.POWDER_BLUE);
    
    setUpCP();

    update();
  }

  /**
   * Set all component panels from the component member variables (i.e sign, whole, num and denom)
   * and draw the panel.
   */
  protected void setPanelsAndDraw()
  {
    setSignPanel();
    setWholePanel();
    setNumPanel();
    setDenomPanel();

    handleStyle(style);

    draw();
  }

  /**
   * Add all component panels (in compliance with #3886) to the JPanel and repaint.
   */
  protected void draw()
  {
    removeAll();

    if (sign == -1)
    {
      add(signPanel);
    }

    if (whole == 0 && num != 0)
    {
      add(fractionPanel);
    }
    else
    {
      add(wholePanel);
      if (num != 0)
      {
        add(fractionPanel);
      }
    }

    revalidate();
    repaint();
  }

  /**
   * Set the denom panel.
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

  /**
   * Style the fraction panel in the bar style.
   */
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

  /**
   * Style the fraction panel in the slash style.
   */
  private void styleAsSlash()
  {
    fractionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    fractionPanel.add(numPanel);
    final JLabel solidus = new JLabel("/");
    fractionPanel.add(solidus);
    fractionPanel.add(denomPanel);
    fractionPanel.setBackground(Display.POWDER_BLUE);
  }

  /**
   * Style the fraction panel in the solidus style.
   */
  private void styleAsSolidus()
  {
    fractionPanel = new JPanel()
    {
      @Override
      protected void paintComponent(final Graphics g)
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

  /**
   * Update the panel with the current style, proper mode, and reduce mode.
   */
  protected void update()
  {
    final MixedFraction mf = new MixedFraction(sign, whole, num, denom);

    if (reduced)
    {
      mf.reduce();
    }
    else if (proper)
    {
      mf.proper();
    }
    else
    {
      mf.improper();
    }
    sign = mf.getSign();
    whole = mf.getWhole();
    num = mf.getNum();
    denom = mf.getDenom();

    setPanelsAndDraw();
  }

  /**
   * Update the fraction style.
   *
   * @param fractionStyle
   *     The fraction style
   */
  @Override
  public void handleStyle(final FractionStyle fractionStyle)
  {
    switch (fractionStyle)
    {
      case BAR -> styleAsBar();
      case SLASH -> styleAsSlash();
      default -> styleAsSolidus();
    }

    draw();
  }

  /**
   * Update the fraction proper mode.
   *
   * @param properMode
   *     The updated proper mode
   */
  @Override
  public void handleProperMode(final boolean properMode)
  {
    this.proper = properMode;
    update();
  }

  /**
   * Update the fraction reduced mode.
   *
   * @param reducedMode
   *     The updated reduced mode
   */
  @Override
  public void handleReducedMode(final boolean reducedMode)
  {
    this.reduced = reducedMode;
    update();
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

  @Override
  public void mouseClicked(MouseEvent e)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mousePressed(MouseEvent e)
  {
    // TODO Auto-generated method stub
    if(SwingUtilities.isRightMouseButton(e))
    {
      cnp.show(this, e.getX(), e.getY());
    }
    
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseEntered(MouseEvent e)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseExited(MouseEvent e)
  {
    // TODO Auto-generated method stub
    
  }
  
  private void setUpCP() {
    this.cnp = new JPopupMenu();
    this.cnpItem = new JMenuItem("Copy Operand");
    cnpItem.addActionListener(this);
    cnp.add(cnpItem);
    addMouseListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub
    String ac = e.toString();
    if(ac.equals("Copy Operand"))
    {
      //display.copy(this);
      //Wanted to have the The display sent in to the constructor so I could copy directly
    }
  }
}
