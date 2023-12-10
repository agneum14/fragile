package gui;

import calculating.FractionModePublisher;
import calculating.FractionStylePublisher;
import calculating.FractionStylePublisher.FractionStyle;
import html.ResourceCopier;
import utilities.Language;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;

import actions.PressAction;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Locale;

public class Menu extends JMenuBar implements ActionListener
{
  private PieChartWindow pcw;
  private static JCheckBoxMenuItem properMenuItem;
  private static JCheckBoxMenuItem reducedMenuItem;
  private CalculatorWindow window; // TODO get rid of this coupling somehow.
  private JMenuItem exitMenuItem, aboutMenuItem, helpMenuItem,  printMenuItem,
      newCalcMenuItem;
  private static JCheckBoxMenuItem pieChartMenuItem;

  private static JRadioButton barMenuItem;
  private static JRadioButton slashMenuItem;
  private static JRadioButton solidusMenuItem;
  private History history;
  
  

  /**
   * Creates JMenuBar with all necessary drop downs for calculator.
   *
   * @return JMenuBar
   */
  public Menu(PieChartWindow pcw, History history, CalculatorWindow window)
  {
    this.window = window;
    this.pcw = pcw;
    this.history = history;
    // Creating the main menu objects
    JMenu fileDropDown = new JMenu(Language.translate("File", "Déposer", "Datei"));
    JMenu modeDropDown = new JMenu("Mode");
    JMenu viewDropDown = new JMenu(Language.translate("View", "Voir", "Ansehen"));
    JMenu styleDropDown = new JMenu(Language.translate("Style", "Modèle", "Stil"));
    JMenu helpDropDown = new JMenu(Language.translate("Help", "Aide", "Hilfe"));
    JMenu loadPrefDown = new JMenu(Language.translate("Preference", "Präferenz", "préférence"));


    // Creating sub menu objects
    String englishText = "Exit";
    exitMenuItem = new JMenuItem(Language.translate(englishText, "Sortie", "Ausgang"));
    exitMenuItem.setActionCommand(englishText);

    englishText = "About";
    aboutMenuItem = new JMenuItem(Language.translate(englishText, "À Propos", "Über"));
    aboutMenuItem.setActionCommand(englishText);

    englishText = "Help";
    helpMenuItem = new JMenuItem(Language.translate(englishText, "Aide", "Hilfe"));
    helpMenuItem.setActionCommand(englishText);

    englishText = "Pie Chart";
    pieChartMenuItem = new JCheckBoxMenuItem(
        Language.translate(englishText, "Diagramme Circulaire", "Kreisdiagramm"));
    pieChartMenuItem.setActionCommand(englishText);

    englishText = "Print Session";
    printMenuItem = new JMenuItem(
        Language.translate(englishText, "Session d'impression", "Sitzung drucken"));
    printMenuItem.setActionCommand("Print Session");

    englishText = "New Calculator";
    newCalcMenuItem = new JMenuItem(
        Language.translate(englishText, "Nouveau calculateur", "Neuer Taschenrechner"));
    newCalcMenuItem.setActionCommand("New Calculator");

    // style menu items
    barMenuItem = new JRadioButton(Language.translate("Bar", "Bar", "Bar"));
    barMenuItem.setSelected(true);
    slashMenuItem = new JRadioButton("Slash");
    solidusMenuItem = new JRadioButton("Solidus");
    ButtonGroup styleGroup = new ButtonGroup();
    styleGroup.add(barMenuItem);
    styleGroup.add(slashMenuItem);
    styleGroup.add(solidusMenuItem);
    styleDropDown.add(barMenuItem);
    styleDropDown.add(slashMenuItem);
    styleDropDown.add(solidusMenuItem);

    // mode menu items
    JCheckBoxMenuItem properMenuItem = new JCheckBoxMenuItem("Proper");
    this.properMenuItem = properMenuItem;
    modeDropDown.add(properMenuItem);
    JCheckBoxMenuItem reducedMenuItem = new JCheckBoxMenuItem("Reduced");
    this.reducedMenuItem = reducedMenuItem;
    modeDropDown.add(reducedMenuItem);
    
    
    
    JButton save = new JButton("Save");
    save.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        String encoding = "";

        // Create a file chooser pointing to the specified path
        JFileChooser fileChooser = new JFileChooser("d:");

        // Open the save dialog
        int userChoice = fileChooser.showSaveDialog(null);

        if (userChoice == JFileChooser.APPROVE_OPTION)
        {
          // Get the selected file
          File selectedFile = fileChooser.getSelectedFile();

          // Append ".txt" extension if not already present
          if (!selectedFile.getName().toLowerCase().endsWith(".txt"))
          {
            selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
          }

         

          if (properMenuItem.isSelected())
          {
            encoding += "a";
          }

          if (reducedMenuItem.isSelected())
          {
            encoding += "b";
          }

          if (pieChartMenuItem.isSelected())
          {
            encoding += "c";
          }

          if (barMenuItem.isSelected())
          {
            encoding += "d";
          }

          if (slashMenuItem.isSelected())
          {
            encoding += "e";
          }

          if (solidusMenuItem.isSelected())
          {
            encoding += "f";
          }

          try (FileOutputStream fos = new FileOutputStream(selectedFile);
              OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
              BufferedWriter writer = new BufferedWriter(osw))
          {

            // Write the encoding to the file
            writer.write(encoding);

            // Inform the user that the preferences have been saved
            System.out.println("Preferences saved to: " + selectedFile.getAbsolutePath());

          }
          catch (IOException ex)
          {
            ex.printStackTrace();
          }
        }
      }
    });
    

    // Adding sub menu objects to menu
    viewDropDown.add(pieChartMenuItem);

    
    fileDropDown.add(printMenuItem);
    fileDropDown.add(newCalcMenuItem);
    fileDropDown.add(exitMenuItem);
    viewDropDown.add(pieChartMenuItem);
    helpDropDown.add(aboutMenuItem);
    helpDropDown.add(helpMenuItem);
    
 // Adding save button to sub menu preference
    loadPrefDown.add(save);

    // Adding main menu objects to menu
    add(fileDropDown);
    add(modeDropDown);
    add(viewDropDown);
    add(styleDropDown);
    add(helpDropDown);
    add(loadPrefDown);


    // add action listeners
    newCalcMenuItem.addActionListener(this);
    printMenuItem.addActionListener(this);
    exitMenuItem.addActionListener(this);
    aboutMenuItem.addActionListener(this);
    helpMenuItem.addActionListener(this);
    pieChartMenuItem.addActionListener(this);
    barMenuItem.addActionListener(this);
    slashMenuItem.addActionListener(this);
    solidusMenuItem.addActionListener(this);
    properMenuItem.addActionListener(this);
    reducedMenuItem.addActionListener(this);
    // Adding Keyboard shortcuts
    MenuShortcuts();
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
        "Fragile v1.0\n\nFragile ist ein moderner, einfach zu bedienender Rechner für gemischte Brüche.\nEr ist ein Produkt von Sagacious Media, das entwickelt wurde von:\n\nJoshua, Andrew, Logan, Ray, Asa, Zach");

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
      case "Exit" -> window.dispose();
      case "New Calculator" -> new CalculatorWindow();
      case "Pie Chart" -> pcw.toggleVisibility();
      case "Print Session" -> history.actionPerformed();
      case "About" -> displayAboutDialog();
      case "Help" -> openHelpPage();
      case "Bar" -> FractionStylePublisher.getInstance().notifyStyle(FractionStyle.BAR);
      case "Slash" -> FractionStylePublisher.getInstance().notifyStyle(FractionStyle.SLASH);
      case "Solidus" -> FractionStylePublisher.getInstance().notifyStyle(FractionStyle.SOLIDUS);
      case "Proper" -> FractionModePublisher.getInstance()
          .notifyProperMode(properMenuItem.isSelected());
      case "Reduced" -> FractionModePublisher.getInstance()
          .notifyReducedMode(reducedMenuItem.isSelected());
      default -> System.out.println("unknown menu option");
    }
  }

  /**
   * private method for making keyboard shortcuts for the Menu.
   */
  private void MenuShortcuts()
  {
    //input map checking for keyboard inputs
    InputMap input = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    input.put(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK),
        helpMenuItem.getActionCommand());
    input.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK),
        printMenuItem.getActionCommand());
    input.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK),
        newCalcMenuItem.getActionCommand());
    input.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK),
        aboutMenuItem.getActionCommand());
    input.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK),
        exitMenuItem.getActionCommand());
    input.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK),
        pieChartMenuItem.getActionCommand());
    input.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.SHIFT_DOWN_MASK),
        properMenuItem.getActionCommand());
    input.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK),
        reducedMenuItem.getActionCommand());
    input.put(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK),
        barMenuItem.getActionCommand());
    input.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK),
        slashMenuItem.getActionCommand());
    input.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.SHIFT_DOWN_MASK),
        solidusMenuItem.getActionCommand());
    
    //Action map checking for the action command and the associated action
    ActionMap actions = this.getActionMap();
    actions.put(helpMenuItem.getActionCommand(), new PressAction(helpMenuItem));
    actions.put(printMenuItem.getActionCommand(), new PressAction(printMenuItem));
    actions.put(newCalcMenuItem.getActionCommand(), new PressAction(newCalcMenuItem));
    actions.put(aboutMenuItem.getActionCommand(), new PressAction(aboutMenuItem));
    actions.put(exitMenuItem.getActionCommand(), new PressAction(exitMenuItem));
    actions.put(pieChartMenuItem.getActionCommand(), new PressAction(pieChartMenuItem));
    actions.put(properMenuItem.getActionCommand(), new PressAction(properMenuItem));
    actions.put(reducedMenuItem.getActionCommand(), new PressAction(reducedMenuItem));
    actions.put(barMenuItem.getActionCommand(), new PressAction(barMenuItem));
    actions.put(slashMenuItem.getActionCommand(), new PressAction(slashMenuItem));
    actions.put(solidusMenuItem.getActionCommand(), new PressAction(solidusMenuItem));
  }

  // opens up the help page.
  private static void openHelpPage()
  {
    try
    {
      String pathString;
      Path path = ResourceCopier.copyResourcesToTemp("temp", "");
      switch (Locale.getDefault().getLanguage())
      {
        case "fr" -> pathString = path.toString() + "/helpFR.html";
        case "de" -> pathString = path.toString() + "/helpGER.html";
        default -> pathString = path.toString() + "/help.html";
      }
      File file = new File(pathString);

      switch (Locale.getDefault().getLanguage())
      {
        case "fr" -> Desktop.getDesktop().browse(file.toURI());
        case "de" -> Desktop.getDesktop().browse(file.toURI());
        default -> Desktop.getDesktop().browse(file.toURI());
      }
    }
    catch (IOException | URISyntaxException e)
    {
      e.printStackTrace();
    }
  }
  
  /**
   * See what preferences need to be pressed.
   * 
   * @param charArray string of encoded letters that are linked to a button.
   */
  public static void checkBoxes(String[] charArray)
  {

    for (String character : charArray)
    {
      if (character.contentEquals("a"))
      {

        properMenuItem.doClick();
      }

      if (character.contentEquals("b"))
      {

        reducedMenuItem.doClick();

      }

      if (character.contentEquals("c") && pieChartMenuItem != null)
      {

        pieChartMenuItem.doClick();

      }

      if (character.contentEquals("d"))
      {

        barMenuItem.doClick();

      }

      if (character.contentEquals("e"))
      {

        slashMenuItem.doClick();

      }

      if (character.contentEquals("f"))
      {

        solidusMenuItem.doClick();
      }
    }
  }

  
}
