package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import actions.ButtonActions;

/**
 * This class is for the main window of the mixed calculator.
 * 
 * @author Joshua Hairston
 * @version 10/31/2023
 * 
 *          This code complies with the JMU Honor Code.
 */
public class CalculatorWindow
{
  private JFrame calculatorWindow;
  private CalculatorButtons buttons;
  private Display display;

  /**
   * Constructor
   */
  public CalculatorWindow()
  {
    this.calculatorWindow = new JFrame(); // creation of the main window
    this.display = new Display(); // creation of the display obviously.
    buttons = new CalculatorButtons(new ButtonActions(display)); // creation of the calculators buttons and the actions hold the display to make changes
    setupLayout(); // creating the layout of the window.
    calculatorWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    calculatorWindow.setVisible(true);
    calculatorWindow.pack(); // sets the size of the window to the components preferred size.
  }

  private void setupLayout()
  {

    // Setting the layout
    Container contentPane = calculatorWindow.getContentPane();
    contentPane.setLayout(new BorderLayout(2, 2));
    // Adding the Menu
    calculatorWindow.setJMenuBar(Menu.createMenuBarForTopOfCalculator());
    calculatorWindow.add(buttons, BorderLayout.SOUTH);

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
    BufferedImage resizedImage = new BufferedImage(150, 30, BufferedImage.SCALE_SMOOTH);
    Graphics2D graphics2D = resizedImage.createGraphics();
    graphics2D.drawImage(img, 0, 0, 150, 30, null);
    graphics2D.dispose();
    // Setting the Fragile logo to the top left of the window.
    label.setHorizontalAlignment(SwingConstants.LEFT);
    calculatorWindow.add(label, BorderLayout.NORTH);
    label.setIcon(new ImageIcon(resizedImage));

    // Adding the display
    calculatorWindow.add(display, BorderLayout.CENTER);

  }

}
