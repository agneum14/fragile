package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Menu extends JMenuBar
{

  /**
   * Creates JMenuBar with all necessary drop downs for calculator.
   *
   * @return JMenuBar
   */
  public Menu(PieChartWindow pcw)
  {
    // Creating the main menu objects
    JMenu fileDropDown = new JMenu("File");
    JMenu viewDropDown = new JMenu("View");
    JMenu helpDropDown = new JMenu("Help");

    // Creating sub menu objects
    JMenuItem exitMenuItem = new JMenuItem("Exit");
    JMenuItem aboutMenuItem = new JMenuItem("About");
    JMenuItem helpMenuItem = new JMenuItem("Help");
    JCheckBoxMenuItem pieChartMenuItem = new JCheckBoxMenuItem("Pie Chart");

    pieChartMenuItem.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        System.out.println(pcw);
        pcw.toggleVisibility();
      }
    });

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

    helpMenuItem.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        openHelpPage();
      }
    });

    // Adding sub menu objects to menu
    fileDropDown.add(exitMenuItem);
    viewDropDown.add(pieChartMenuItem);
    helpDropDown.add(aboutMenuItem);
    helpDropDown.add(helpMenuItem);

    // Adding main menu objects to menu
    add(fileDropDown);
    add(viewDropDown);
    add(helpDropDown);
  }

  /**
   * Creates Help box with everything in it.
   */
  private static void displayAboutDialog()
  {

    // Creating dialog box for help
    JDialog aboutDialog = new JDialog();
    aboutDialog.setTitle("About");
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
    String companyInfo = "Fragile v1.0\n\n" + "Fragile is a modern, easy-to-use mixed-fraction calculator.\n" + "It is a product of Sagacious Media that was developed by:\n\n" + "Joshua, Andrew, Logan, Ray, Asa, Zach";

    // Putting the String companyInfo on the JDialog
    // Creating the pane to hold the companyInfo (centered)
    JTextPane textPane = new JTextPane();
    textPane.setEditable(false);
    textPane.setCaretPosition(0);
    // Setting the style attributes of the pane to center the contents
    StyledDocument doc = textPane.getStyledDocument();
    SimpleAttributeSet center = new SimpleAttributeSet();
    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
    doc.setParagraphAttributes(0, doc.getLength(), center, false);
    // Setting foreground text color
    Style style = textPane.addStyle("Color Style", null);
    StyleConstants.setForeground(style, Color.BLACK);
    try
    {
      doc.insertString(doc.getLength(), companyInfo, style);
    }
    catch (BadLocationException e)
    {
      e.printStackTrace();
    }

    // Creating a panel to hold the image and text, and center the image
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(textPane, BorderLayout.CENTER);

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

  //opens up the help page method.
  private static void openHelpPage()
  {
    try
    {
      File file = new File("res/help.html");
      Desktop.getDesktop().browse(file.toURI());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}

