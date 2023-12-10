package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

import utilities.MapFormatter;

public class ShortcutChooser extends JFrame implements ActionListener
{
  private JComboBox<String> comboBox;
  private JLabel label;
  private TextFieldHint text;
  private JButton set;
  private JButton save;
  private Map<String, String> keybinds;

  public ShortcutChooser()
  {
    keybinds = new HashMap<>();
    makeGUI();
    this.setTitle("Shortcut Helper");
    this.setSize(300, 300);
    this.setResizable(false);
    this.setVisible(true);
  }

  private void makeGUI()
  {
    String[] comp = {Menu.exitMenuItem.getActionCommand(), Menu.aboutMenuItem.getActionCommand(),
        Menu.helpMenuItem.getActionCommand(), Menu.printMenuItem.getActionCommand(),
        Menu.newCalcMenuItem.getActionCommand(), Menu.shortcutsMenuItem.getActionCommand(),
        Menu.pieChartMenuItem.getActionCommand(), Menu.barMenuItem.getActionCommand(),
        Menu.slashMenuItem.getActionCommand(), Menu.solidusMenuItem.getActionCommand()};
    comboBox = new JComboBox<String>(comp);
    String prompt = "Please enter in a key for your keyboard shortcut\n\n(note the key will be paired with the Control key)";
    text = new TextFieldHint(prompt);
    set = new JButton("Set");
    save = new JButton("Save");
    set.addActionListener(this);
    save.addActionListener(this);
    comboBox.addActionListener(this);
    setLayout(new FlowLayout());
    this.add(comboBox);
    this.add(text);
    this.add(save);
    this.add(set);

  }

  public static void setKeybind(String menuItem, String character)
  {
    char c = character.charAt(0);
    switch (menuItem)
    {
      case "Exit" -> Menu.exitMenuItem
          .setAccelerator(KeyStroke.getKeyStroke(c, KeyEvent.CTRL_DOWN_MASK));
      case "New Calculator" -> Menu.newCalcMenuItem
          .setAccelerator(KeyStroke.getKeyStroke(c, KeyEvent.CTRL_DOWN_MASK));
      case "Pie Chart" -> Menu.pieChartMenuItem
          .setAccelerator(KeyStroke.getKeyStroke(c, KeyEvent.CTRL_DOWN_MASK));
      case "Print Session" -> Menu.printMenuItem
          .setAccelerator(KeyStroke.getKeyStroke(c, KeyEvent.CTRL_DOWN_MASK));
      case "About" -> Menu.aboutMenuItem
          .setAccelerator(KeyStroke.getKeyStroke(c, KeyEvent.CTRL_DOWN_MASK));
      case "Shortcuts" -> Menu.shortcutsMenuItem
          .setAccelerator(KeyStroke.getKeyStroke(c, KeyEvent.CTRL_DOWN_MASK));
      case "Help" -> Menu.helpMenuItem
          .setAccelerator(KeyStroke.getKeyStroke(c, KeyEvent.CTRL_DOWN_MASK));
      case "Bar" -> Menu.barMenuItem
          .setAccelerator(KeyStroke.getKeyStroke(c, KeyEvent.CTRL_DOWN_MASK));
      case "Slash" -> Menu.slashMenuItem
          .setAccelerator(KeyStroke.getKeyStroke(c, KeyEvent.CTRL_DOWN_MASK));
      case "Solidus" -> Menu.solidusMenuItem
          .setAccelerator(KeyStroke.getKeyStroke(c, KeyEvent.CTRL_DOWN_MASK));
      case "Proper" -> Menu.properMenuItem
          .setAccelerator(KeyStroke.getKeyStroke(c, KeyEvent.CTRL_DOWN_MASK));
      case "Reduced" -> Menu.reducedMenuItem
          .setAccelerator(KeyStroke.getKeyStroke(c, KeyEvent.CTRL_DOWN_MASK));
      default -> System.out.println("unknown menu option");
    }
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals("Set"))
    {
      System.out.println(comboBox.getSelectedItem());
      String menuItemText = comboBox.getSelectedItem().toString();
      String character = text.getText().toUpperCase();
      setKeybind(menuItemText, character);
      keybinds.put(menuItemText,character);
    }
    else if (e.getSource().equals(comboBox))
    {
      comboBox.setSelectedItem(comboBox.getSelectedItem());
      System.out.println(comboBox.getSelectedItem());
    }
    else if (e.getActionCommand().equals("Save"))
    {
      try
      {
        MapFormatter.write(keybinds, "shortcuts.txt");

      }
      catch (IOException e1)
      {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }

  }
}
