package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


public class Menu
{

  /**
   * Creates JMenuBar with all necessary drop downs for calculator.
   * 
   * @return JMenuBar
   */
  public static JMenuBar createMenuBarForTopOfCalculator()
  {
    // Creating Menu bar
    JMenuBar menuBar = new JMenuBar();

    // Creating the main menu objects
    JMenu fileDropDown = new JMenu("File");
    JMenu viewDropDown = new JMenu("View");
    JMenu helpDropDown = new JMenu("Help");

    // Creating sub menu objects
    JMenuItem exitMenuItem = new JMenuItem("Exit");
    JMenuItem aboutMenuItem = new JMenuItem("About");
    JMenuItem helpMenuItem = new JMenuItem("Help");
    JCheckBoxMenuItem pieChartMenuItem = new JCheckBoxMenuItem("Pie Chart");

    // Exiting Action
    exitMenuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        System.exit(0);
      }
    });

    // about box
    aboutMenuItem.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        displayAboutDialog();
      }
    });
    
    helpMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          openHelpPage();
      }
  });

    // Adding sub menu objects to menu
    fileDropDown.add(exitMenuItem);
    viewDropDown.add(pieChartMenuItem);
    helpDropDown.add(aboutMenuItem);
    helpDropDown.add(helpMenuItem);

    // Adding main menu objects to menu
    menuBar.add(fileDropDown);
    menuBar.add(viewDropDown);
    menuBar.add(helpDropDown);

    // Returning whole menu
    return menuBar;
  }

  /**
   * Creates Help box with everything in it.
   */
  private static void displayAboutDialog()
  {

    // Creating dialog box for help
    JDialog aboutDialog = new JDialog();
    aboutDialog.setTitle("                                                         About");
    aboutDialog.setSize(450, 200);

    // Setting the background color to grey
    aboutDialog.getContentPane().setBackground(Color.GRAY);

    // Loading the image from the URL
    BufferedImage img = null;
    try
    {
      URL imgUrl = new URL(
          "https://w3.cs.jmu.edu/bernstdh/web/CS345/project/Fragile_Icon_32x32.png");
      img = ImageIO.read(imgUrl);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    // creating a JLabel to display the image
    JLabel imgLabel = new JLabel(new ImageIcon(img));
    imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

    // Words that need to be under the logo
    String companyInfo = "                                                              Fragile v1.0\n\n"
        + "                       Fragile is a modern, easy-to-use mixed-fraction calculator.\n"
        + "                                     It is a product of Sagacious Media that was\n"
        + "                                                              developed by:\n\n"
        + "                                      Joshua, Andrew, Logan, Ray, Asa, Zach";

    // Putting the String companyInfo on the JDialog
    JTextArea textArea = new JTextArea(companyInfo);
    textArea.setEditable(false);
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);
    textArea.setCaretPosition(0);

    // Creating a panel to hold the image and text, and center the image
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(textArea, BorderLayout.CENTER);

    // Setting the background color of the panel to grey
    panel.setBackground(Color.BLACK);

    // Adding borders to make look nice
    aboutDialog.add(imgLabel, BorderLayout.NORTH);
    aboutDialog.add(panel, BorderLayout.CENTER);
    aboutDialog.setVisible(true);

    aboutDialog.setResizable(false);
    // Center the dialog on the screen
    aboutDialog.setLocationRelativeTo(null);
  }
  
  //opens up the help page.
  private static void openHelpPage() {
    try {
      File file = new File("res/help.html");
      Desktop.getDesktop().browse(file.toURI());
  } catch (IOException e) {
      e.printStackTrace();
  }
}

  // Shows how it looks
  public static void main(String[] args)
  {
    JFrame frame = new JFrame("Example of what it would look like");
    frame.setSize(400, 300);

    frame.setJMenuBar(Menu.createMenuBarForTopOfCalculator());

    frame.setVisible(true); 
  }

}

