package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * This class is for the main window of the mixed calculator.
 * 
 * @author Joshua Hairston
 * @version 10/31/2023
 * 
 * This code complies with the JMU Honor Code.
 */
public class CalculatorWindow implements ActionListener
{
  private JFrame calculatorWindow;
  private CalculatorButtons buttons;
  private Display display;

  /**
   * Constructor
   */
  public CalculatorWindow()
  {
    this.calculatorWindow = new JFrame();
    buttons = new CalculatorButtons(this);
    this.display = new Display();
    setupLayout();
    calculatorWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    calculatorWindow.setVisible(true);
    calculatorWindow.setSize(300, 300);
    calculatorWindow.pack();
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
    label.setIcon(new ImageIcon(img));
    calculatorWindow.add(label, BorderLayout.PAGE_START);

    // Adding the display
    calculatorWindow.add(display, BorderLayout.CENTER);

  }

  @Override
  public void actionPerformed(ActionEvent e)
  {

  }
}
