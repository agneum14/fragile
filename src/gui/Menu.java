package gui;

import calculating.FractionModePublisher;
import calculating.FractionStylePublisher;
import calculating.FractionStylePublisher.FractionStyle;
import utilities.Language;
import utilities.MapFormatter;
import utilities.ResourceManager;
import utilities.ShortcutManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 
 */
public class Menu extends JMenuBar implements ActionListener
{
  public static JMenuItem exitMenuItem, aboutMenuItem, helpMenuItem, printMenuItem, newCalcMenuItem;
  public static JCheckBoxMenuItem shortcutsMenuItem;
  public static JCheckBoxMenuItem pieChartMenuItem;
  public static JRadioButtonMenuItem barMenuItem;
  public static JRadioButtonMenuItem slashMenuItem;
  public static JRadioButtonMenuItem solidusMenuItem;
  public static JCheckBoxMenuItem properMenuItem;
  public static JCheckBoxMenuItem reducedMenuItem;
  public static JCheckBoxMenuItem thousandSeparator;
  private static final long serialVersionUID = 1L;
  private PieChartWindow pcw;
  private ShortcutChooser shortcut;

  private CalculatorWindow window; // TODO get rid of this coupling somehow.
  private History history;

  /**
   * Creates JMenuBar with all necessary drop downs for calculator.
   * 
   * @param pcw
   *          the piechart window.
   * @param history
   *          the main calculators window for storying the past expressions.
   * @param window
   *          the main calculator window for the calculator.
   * @param shortcut
   *          the keyboardshortcut for the calculator.
   */
  public Menu(final PieChartWindow pcw, final History history, final CalculatorWindow window,
      final ShortcutChooser shortcut)
  {
    this.window = window;
    this.pcw = pcw;
    this.history = history;
    this.shortcut = shortcut;
    // Creating the main menu objects
    JMenu fileDropDown = new JMenu(Language.translate("File", "Déposer", "Datei"));
    JMenu modeDropDown = new JMenu("Mode");
    JMenu viewDropDown = new JMenu(Language.translate("View", "Voir", "Ansehen"));
    JMenu styleDropDown = new JMenu(Language.translate("Style", "Modèle", "Stil"));
    JMenu helpDropDown = new JMenu(Language.translate("Help", "Aide", "Hilfe"));
    JMenu shortcuts = new JMenu(Language.translate("Shortcuts", "Raccourcis", "Verknüpfungen"));
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
    englishText = "Shortcuts";
    shortcutsMenuItem = new JCheckBoxMenuItem(Language.translate(englishText, "", ""));
    shortcutsMenuItem.setActionCommand(englishText);
    // style menu items
    barMenuItem = new JRadioButtonMenuItem(Language.translate("Bar", "Bar", "Bar"));
    barMenuItem.setSelected(true);
    slashMenuItem = new JRadioButtonMenuItem("Slash");
    solidusMenuItem = new JRadioButtonMenuItem("Solidus");
    ButtonGroup styleGroup = new ButtonGroup();
    styleGroup.add(barMenuItem);
    styleGroup.add(slashMenuItem);
    styleGroup.add(solidusMenuItem);
    styleDropDown.add(barMenuItem);
    styleDropDown.add(slashMenuItem);
    styleDropDown.add(solidusMenuItem);

    // mode menu items
    JCheckBoxMenuItem properMenuItem = new JCheckBoxMenuItem("Proper");
    Menu.properMenuItem = properMenuItem;
    modeDropDown.add(properMenuItem);
    JCheckBoxMenuItem reducedMenuItem = new JCheckBoxMenuItem("Reduced");
    Menu.reducedMenuItem = reducedMenuItem;
    modeDropDown.add(reducedMenuItem);

    englishText = "Separator";
    JCheckBoxMenuItem thousandSeparator = new JCheckBoxMenuItem(
        Language.translate(englishText, "Séparateur", "Trennzeichen"));
    thousandSeparator.setActionCommand(englishText);
    Menu.thousandSeparator = thousandSeparator;

    JButton load = new JButton("Load");
    load.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        // Create a file picker dialog after the calculator is shown
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        // Check if a file is selected
        if (result == JFileChooser.APPROVE_OPTION)
        {
          // Get the selected file
          File selectedFile = fileChooser.getSelectedFile();
          String selectedFilePath = selectedFile.getAbsolutePath();

          // Read the content of the file and store each character in a separate string in an array
          try (BufferedReader reader = new BufferedReader(new FileReader(selectedFilePath)))
          {
            ArrayList<String> charList = new ArrayList<>();
            int charValue;
            while ((charValue = reader.read()) != -1)
            {
              charList.add(String.valueOf((char) charValue));
            }

            // Convert the list to an array
            String[] charArray = charList.toArray(new String[0]);
            Menu.checkBoxes(charArray);

          }
          catch (IOException ex)
          {
            ex.printStackTrace();
          }
        }
      }
    });

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
    shortcuts.add(shortcutsMenuItem);
    loadPrefDown.add(load);
    loadPrefDown.add(save);
    loadPrefDown.add(thousandSeparator);

    // Adding main menu objects to menu
    add(fileDropDown);
    add(modeDropDown);
    add(viewDropDown);
    add(styleDropDown);
    add(helpDropDown);
    add(shortcuts);
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
    shortcutsMenuItem.addActionListener(this);
    properMenuItem.addActionListener(this);
    reducedMenuItem.addActionListener(this);
    thousandSeparator.addActionListener(this);
    // Adding Keyboard shortcuts
    shortcut.makeGUI();
    menuShortcuts();
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
        "Fragile v1.0\n\nFragile is a modern, easy-to-use mixed-fraction "
            + "calculator.\nIt is a product of Sagacious Media tha" + "t was developed "
            + "by:\n\nJoshua, Andrew, Logan, Ray, Asa, Zach",
        "Fragile v1.0\n\nFragile est une calculatrice de "
            + "fractions mixtes moderne et facile à utiliser.\nC'est un produit de"
            + " Sagacious Media qui a été développé par:\n\nJoshua, Andrew, Logan, Ray, Asa, Zach",
        "Fragile v1.0\n\nFragile ist ein moderner, einfach zu bedienender"
            + " Rechner für gemischte Brüche.\nEr ist ein Produkt von Sagacious"
            + " Media, das entwickelt wurde von:\n\nJoshu" + "a, Andrew, Logan, Ray, Asa, Zach");

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

  /**
   * ActionPerformed checks for which menuitem was selected.
   */
  @Override
  public void actionPerformed(final ActionEvent e)
  {
    switch (e.getActionCommand())
    {
      case "Exit" ->
      {
        Menu.saveEncodingToFile();
        window.destroy();
      }
      case "New Calculator" -> new CalculatorWindow();
      case "Pie Chart" -> pcw.toggleVisibility();
      case "Print Session" -> history.actionPerformed();
      case "About" -> displayAboutDialog();
      case "Shortcuts" -> shortcut.toggleVisibility();
      case "Help" -> openHelpPage();
      case "Bar" -> FractionStylePublisher.getInstance().notifyStyle(FractionStyle.BAR);
      case "Slash" -> FractionStylePublisher.getInstance().notifyStyle(FractionStyle.SLASH);
      case "Solidus" -> FractionStylePublisher.getInstance().notifyStyle(FractionStyle.SOLIDUS);
      case "Proper" -> FractionModePublisher.getInstance()
          .notifyProperMode(properMenuItem.isSelected());
      case "Reduced" -> FractionModePublisher.getInstance()
          .notifyReducedMode(reducedMenuItem.isSelected());
      case "Separator" -> FractionStylePublisher.getInstance()
          .notifySeparated(thousandSeparator.isSelected());
      default -> System.out.println("unknown menu option");
    }
  }

  /**
   * private method for loading the KeyBoard shortcuts.
   */
  private void menuShortcuts()
  {
    ShortcutManager sh = ShortcutManager.newInstance();
    Map<String, String> map = sh.getMap();
    for (String key : map.keySet())
    {
      ShortcutChooser.setKeybind(key, map.get(key).charAt(0));
    }
  }

  // opens up the help page.
  private static void openHelpPage()
  {
    try
    {
      String pathString;
      ResourceManager rm = ResourceManager.newInstance();
      Path path = rm.getResourcePath();
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
   * @param charArray
   *          string of encoded letters that are linked to a button.
   */
  public static void checkBoxes(final String[] charArray)
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

  /**
   * Gets the encoding for preferece.
   *
   * @return a string for the encoding
   */
  public static String getEncoding()
  {
    String encoding = "";

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

    return encoding;

  }

  /**
   * creates the save file when exited.
   */
  public static void saveEncodingToFile()
  {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("calculator_last_saved.txt")))
    {
      writer.write(Menu.getEncoding());
    }
    catch (IOException e)
    {
      e.printStackTrace(); // Handle the exception
    }
  }

}
