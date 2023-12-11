package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for the main window of the mixed calculator. This class is where the Intermediate
 * steps and the history is being set to. The class is also listening for the main window to move so
 * that the other components can move with it.
 *
 * @author Joshua Hairston
 * @version 10/31/2023
 *
 *          This code complies with the JMU Honor Code.
 */
public class CalculatorWindow extends JFrame implements ComponentListener
{
  private static List<CalculatorWindow> instances;

  private static final long serialVersionUID = 1L;
  private CalculatorButtons buttons;
  private Display display;
  private PieChartWindow pcw;
  private History history;
  private IntermediateSteps intermediateSteps;
  private ShortcutChooser shortcutChooser;

  /**
   * The static constructor initializes the list of instances to know when the program should be closed.
   */
  static 
  {
      instances = new ArrayList<>();
  }

  /**
   * Constructor for CalculatorWindow, initializes various components.
   */
  public CalculatorWindow()
  {
    instances.add(this);
    shortcutChooser = new ShortcutChooser();
    pcw = new PieChartWindow();
    history = new History(this);
    intermediateSteps = new IntermediateSteps(this);
    display = new Display(pcw, history, intermediateSteps);
    buttons = new CalculatorButtons(display); // creation of the calculators buttons and the actions
    this.addComponentListener(this);
    setupLayout(); // creating the layout of the window.

    // has the save action
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    addWindowListener((WindowListener) new WindowAdapter()
    {
      @Override
      public void windowClosed(final WindowEvent e)
      {

        Menu.saveEncodingToFile();

      }
    });

    setPreferredSize(new Dimension(400, 500));
    pack(); // sets the size of the window to the components preferred size.
    setVisible(true);
  }

  /**
   * Sets up the layout of the calculator window, including menus, buttons, logo, and display.
   */
  private void setupLayout()
  {

    // Setting the layout
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout(2, 2));
    // Adding the Menu
    setJMenuBar(new Menu(pcw, history, this, shortcutChooser));
    add(buttons, BorderLayout.SOUTH);

    // Putting image of Fragile in the window
    JLabel label = new JLabel();
    BufferedImage img = null;
    GuiConfig gc = GuiConfig.newInstance();
    File logoFile = new File(gc.getLogoPath());
    if (!logoFile.exists() || !logoFile.isFile())
    {
      throw new RuntimeException(String.format("Expected file at '%s'", gc.getLogoPath()));
    }
    try
    {
      img = ImageIO.read(logoFile);
    }
    catch (IOException e)
    {
    }
    // Resizing the Fragile logo
    BufferedImage resizedImage = new BufferedImage(150, 50, BufferedImage.SCALE_SMOOTH);
    Graphics2D graphics2D = resizedImage.createGraphics();
    graphics2D.drawImage(img, 0, 0, 150, 50, null);
    graphics2D.dispose();
    // Setting the Fragile logo to the top left of the window.
    label.setHorizontalAlignment(SwingConstants.LEFT);
    add(label, BorderLayout.NORTH);
    label.setIcon(new ImageIcon(resizedImage));

    // Adding the display
    add(display, BorderLayout.CENTER);
  }

  /**
  *  Destory is called when the exit menu button is pressed. If this is the only running instance, the program terminates. Otherwise, all the GUI windows are disposed.
  */
public void destroy() 
  {
      instances.remove(this);

      if (instances.size() == 0) {
          System.exit(0);
      } else {
          pcw.dispose();
          history.dispose();
          intermediateSteps.dispose();
          this.dispose();
      }
  }

  /**
   * Method changes the history and intermediate steps position dependent on the position of the
   * window.
   */
  @Override
  public void componentMoved(final ComponentEvent e)
  {
    int x = this.getX() + this.getWidth() - 8;
    int y = this.getY() + 110;
    history.setLocation(x, y);
    x = this.getX() + 8;
    y = getY();
    intermediateSteps.setPosition(x, y);
  }

  /**
   * Does nothing but is needed due to the interface.
   */
  @Override
  public void componentShown(final ComponentEvent e)
  {
    return;
  }

  /**
   * Does nothing but is needed due to the interface.
   */
  @Override
  public void componentHidden(final ComponentEvent e)
  {
    return;
  }

  /**
   * Does nothing but is needed due to the interface.
   */
  @Override
  public void componentResized(final ComponentEvent e)
  {
    componentMoved(e);

  }
}
