package gui;

import calculating.ExpressionElement;
import calculating.FractionModePublisher;
import calculating.MixedFraction;
import gui.Display.Operator;
import gui.mf.MixedFractionPanel;
import utilities.Algorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class IntermediateSteps extends JWindow implements ActionListener
{
  private static final long serialVersionUID = 1L;
  private static final int HEIGHT = 300;
  private static final int MAX_WIDTH = 450;
  private static final int MIN_WIDTH = 30;
  private static final int Y_OFFSET = 110;
  private static final String LESS = "<";

  private JFrame parent;
  private boolean opened;
  private JButton button;
  private Timer timer;
  private JPanel content;
  private List<MixedFractionPanel> mfps;

  /**
   * Constructor for the History class.
   *
   * @param parent The parent frame to attach the history to.
   */
  public IntermediateSteps(final JFrame parent)
  {
    super(parent);
    this.parent = parent;
    opened = false; // Tracking the status of the window.
    mfps = new ArrayList<>();

    setBackground(Display.displayColor);
    setLayout(new BorderLayout());
    button = new JButton(LESS);
    button.addActionListener(this);
    add(button, BorderLayout.WEST);
    // Initialize history panel
    content = new JPanel();
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
    final JScrollPane scrollPane = new JScrollPane(content);
    add(scrollPane, BorderLayout.CENTER);
    // Animation
    timer = new Timer(10, e -> animate());

    setSize(MIN_WIDTH, HEIGHT);
    setVisible(true);
  }

  public void setPosition(int x, int y)
  {
    setLocation(x - getWidth(), y + Y_OFFSET);
  }

  /**
   * Add a new expression to the history and repaint.
   *
   * @param expressionList The expression (as a list of mixed fractions and operators) to add
   * @param display        The display. This needs to be passed to create new mixed fraction panels
   */
  public void update(List<List<ExpressionElement>> steps, Display display)
  {
    for (List<ExpressionElement> step : steps)
    {
      MixedFraction b = (MixedFraction) step.get(0);
      Operator operator = (Operator) step.get(1);
      MixedFraction a = (MixedFraction) step.get(2);
      MixedFraction result = (MixedFraction) step.get(3);

      JPanel stepContent = new JPanel(new GridBagLayout());
      GridBagConstraints g = new GridBagConstraints();
      g.gridx = 0;
      g.gridy = -1;
      g.anchor = GridBagConstraints.WEST;

      JPanel first = new JPanel();
      first.add(new JLabel("STEPPING THROUGH"));
      first.add(createMFP(b, display));
      first.add(new JLabel(operator.toString()));
      first.add(createMFP(a, display));
      g.gridy++;
      stepContent.add(first, g);

      JPanel fracAndReduce = new JPanel();
      fracAndReduce.add(new JLabel("Reduce and fractionalize the operands:"));
      MixedFractionPanel bRed = new MixedFractionPanel(b, display);
      MixedFractionPanel aRed = new MixedFractionPanel(a, display);
      bRed.handleReducedMode(true);
      aRed.handleReducedMode(true);
      bRed.handleReducedMode(false);
      aRed.handleReducedMode(false);
      bRed.handleProperMode(false);
      aRed.handleProperMode(false);
      fracAndReduce.add(bRed);
      fracAndReduce.add(aRed);
      g.gridy++;
      stepContent.add(fracAndReduce, g);

      if (operator == Operator.ADD || operator == Operator.SUB)
      {
        g.gridy++;
        int gcd = Algorithms.gcd(bRed.getDenom(), aRed.getDenom());
        stepContent.add(new JLabel(
                String.format("The GCD of the denominators %d and %d is %d", bRed.getDenom(),
                        aRed.getDenom(), gcd)), g);

        g.gridy++;
        JPanel scale = new JPanel();
        scale.add(new JLabel("Scale the operands so the denomintor is the GCD:"));
      }

      g.weighty = 1;
      g.fill = GridBagConstraints.BOTH;
      g.gridy++;
      stepContent.add(new JPanel(), g);

      content.add(stepContent);
    }

    content.revalidate();
    content.repaint();
  }

  private MixedFractionPanel createMFP(MixedFraction mf, Display display)
  {
    MixedFractionPanel mfp = new MixedFractionPanel(mf, display);
    FractionModePublisher.getInstance().removeSubscriber(mfp);
    mfps.add(mfp);
    return mfp;
  }

  /**
   * Method for checking to see if the window is either closed or open.
   */
  @Override
  public void actionPerformed(final ActionEvent e)
  {
    final String ac = e.getActionCommand();
    if (ac.equals(LESS))
    {
      if (!opened)
      {
        opened = true;
        button.setText(">");
        timer.start();
      }
    } else
    {
      opened = false;
      button.setText(LESS);
      timer.start();
    }
  }

  /**
   * Animates the button.
   */
  private void animate()
  {
    final int targetWidth = (opened) ? MAX_WIDTH : MIN_WIDTH;
    final int currentWidth = getWidth();
    if (currentWidth != targetWidth)
    {
      int change = (currentWidth < targetWidth) ? 5 : -5;
      int newWidth = currentWidth + change;
      setSize(newWidth, HEIGHT);
      setPosition(parent.getX(), parent.getY());
      if (currentWidth < targetWidth && newWidth >= targetWidth)
      {
        timer.stop();
      } else if (currentWidth > targetWidth && newWidth <= targetWidth)
      {
        timer.stop();
      }
    }
  }
}
