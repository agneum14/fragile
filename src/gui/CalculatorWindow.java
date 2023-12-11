package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class is for the main window of the mixed calculator.
 *
 * @author Joshua Hairston
 * @version 10/31/2023
 *
 * This code complies with the JMU Honor Code.
 */
public class CalculatorWindow extends JFrame implements ComponentListener
{
  private static final long serialVersionUID = 1L;
  private CalculatorButtons buttons;
  private Display display;
  private PieChartWindow pcw;
  private History history;

  /**
   * Constructor
   */
  public CalculatorWindow()
  {
    pcw = new PieChartWindow();
    history = new History(this);
    display = new Display(pcw, history); // creation
    // of the
    // display
    // obviously.
    buttons = new CalculatorButtons(display); // creation of the calculators buttons and the actions
    this.addComponentListener(this); // hold the display to make changes
    setupLayout(); // creating the layout of the window.


    // has the save action 
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    addWindowListener((WindowListener) new WindowAdapter()
    {
      @Override
      public void windowClosed(WindowEvent e)
      {

        Menu.saveEncodingToFile();


      }
    });


    setPreferredSize(new Dimension(400, 500));
    pack(); // sets the size of the window to the components preferred size.
    setVisible(true);
  }

  private void setupLayout()
  {

    // Setting the layout
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout(2, 2));
    // Adding the Menu
    setJMenuBar(new Menu(pcw, history, this));
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
    } catch (IOException e)
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

  // IMPLEMENTATION FOR THE HISTORY WINDOW TRACING THE MAIN WINDOW.

  /**
   * Method changes the history classes position dependent on the position of the window.
   */
  @Override
  public void componentMoved(ComponentEvent e)
  {
    int x = this.getX() + this.getWidth() - 8;
    int y = this.getY() + 110;
    history.setLocation(x, y);

  }

  @Override
  public void componentShown(ComponentEvent e)
  {
    return;
  }

  @Override
  public void componentHidden(ComponentEvent e)
  {
    return;
  }

  @Override
  public void componentResized(ComponentEvent e)
  {
    componentMoved(e);

  }


}
