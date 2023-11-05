package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;

import utilities.Language;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Menu extends JMenuBar implements ActionListener
{
  private PieChartWindow pcw;

  /**
   * Creates JMenuBar with all necessary drop downs for calculator.
   *
   * @return JMenuBar
   */
  public Menu(PieChartWindow pcw)
  {
    this.pcw = pcw;

    // Creating the main menu objects
    JMenu fileDropDown = new JMenu(Language.translate("File", "Déposer", "ファイル"));
    JMenu viewDropDown = new JMenu(Language.translate("View", "Voir", "ビュー"));
    JMenu helpDropDown = new JMenu(Language.translate("Help", "Aide", "ビュー"));

    // Creating sub menu objects
    String englishText = "Exit";
    JMenuItem exitMenuItem = new JMenuItem(Language.translate(englishText, "Sortie", "出口"));
    exitMenuItem.setActionCommand(englishText);
    englishText = "About";
    JMenuItem aboutMenuItem = new JMenuItem(
        Language.translate(englishText, "À Propos", "について"));
    aboutMenuItem.setActionCommand(englishText);
    englishText = "Help";
    JMenuItem helpMenuItem = new JMenuItem(Language.translate(englishText, "Aide", "ヘルプ"));
    helpMenuItem.setActionCommand(englishText);
    englishText = "Pie Chart";
    JCheckBoxMenuItem pieChartMenuItem = new JCheckBoxMenuItem(
        Language.translate(englishText, "Diagramme Circulaire", "円グラフ"));
    pieChartMenuItem.setActionCommand(englishText);

    // Adding sub menu objects to menu
    fileDropDown.add(exitMenuItem);
    viewDropDown.add(pieChartMenuItem);
    helpDropDown.add(aboutMenuItem);
    helpDropDown.add(helpMenuItem);

    // Adding main menu objects to menu
    add(fileDropDown);
    add(viewDropDown);
    add(helpDropDown);

    // add action listeners
    exitMenuItem.addActionListener(this);
    aboutMenuItem.addActionListener(this);
    helpMenuItem.addActionListener(this);
    pieChartMenuItem.addActionListener(this);
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
    String companyInfo = Language.translate(
        "Fragile v1.0\n\nFragile is a modern, easy-to-use mixed-fraction calculator.\nIt is a product of Sagacious Media that was developed by:\n\nJoshua, Andrew, Logan, Ray, Asa, Zach",
        "Fragile v1.0\n\nFragile est une calculatrice de fractions mixtes moderne et facile à utiliser.\nC'est un produit de Sagacious Media qui a été développé par:\n\nJoshua, Andrew, Logan, Ray, Asa, Zach",
        "Fragile v1.0\n\nFragileは、モダンで使いやすい混合分数計算機です。\nこれはSagacious Mediaの製品で、以下によって開発されました:\n\nJoshua, Andrew, Logan, Ray, Asa, Zach");

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

  @Override
  public void actionPerformed(ActionEvent e)
  {
    switch (e.getActionCommand())
    {
      case "Exit" -> System.exit(0);
      case "Pie Chart" -> pcw.toggleVisibility();
      case "About" -> displayAboutDialog();
      case "Help" -> openHelpPage();
      default -> System.out.println("unknown menu option");
    }
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

