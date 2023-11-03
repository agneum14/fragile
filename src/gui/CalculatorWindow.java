package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class is for the main window of the mixed calculator.
 *
 * @author Joshua Hairston
 * @version 10/31/2023
 *
 *     This code complies with the JMU Honor Code.
 */
public class CalculatorWindow extends JFrame
{
  private CalculatorButtons buttons;
  private Display display;
  private PieChartWindow pcw;

  /**
   * Constructor
   */
  public CalculatorWindow()
  {
    pcw = new PieChartWindow();
    display = new Display(pcw); // creation of the display obviously.
    buttons = new CalculatorButtons(
        display); // creation of the calculators buttons and the actions hold the display to make changes
    setupLayout(); // creating the layout of the window.
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
    setJMenuBar(new Menu(pcw));
    add(buttons, BorderLayout.SOUTH);

    // Putting image of Fragile in the window
    JLabel label = new JLabel();
    BufferedImage img = null;
    try
    {
      img = ImageIO.read(new File("res/Fragile_Logo.png"));
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
}
