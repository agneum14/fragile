package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import utilities.MapFormatter;
import utilities.ShortcutManager;

/**
 * The ShortcutChooser class
 */
public class ShortcutChooser extends JFrame implements ActionListener
{

  private static final long serialVersionUID = 1L;
  private JComboBox<String> comboBox;
  private TextFieldHint text;
  private JButton set;
  private ShortcutManager manager = ShortcutManager.newInstance();
  private JLabel field;

  public ShortcutChooser()
  {
    this.setTitle("Shortcut Helper");
    this.setSize(300, 300);
    this.setResizable(false);
    this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
  }

  public void makeGUI()
  {
    String[] comp = {Menu.exitMenuItem.getActionCommand(), Menu.aboutMenuItem.getActionCommand(),
        Menu.helpMenuItem.getActionCommand(), Menu.printMenuItem.getActionCommand(),
        Menu.newCalcMenuItem.getActionCommand(), Menu.shortcutsMenuItem.getActionCommand(),
        Menu.pieChartMenuItem.getActionCommand(), Menu.barMenuItem.getActionCommand(),
        Menu.slashMenuItem.getActionCommand(), Menu.solidusMenuItem.getActionCommand(),
        Menu.properMenuItem.getActionCommand(), Menu.reducedMenuItem.getActionCommand()};
    comboBox = new JComboBox<String>(comp);
    field = new JLabel();
    field.setVisible(false);
    String prompt = "Please enter in a key for your keyboard shortcut\n\n(note the key will be paired with the Control key)";
    text = new TextFieldHint(prompt);
    set = new JButton("Set");
    set.addActionListener(this);
    comboBox.addActionListener(this);
    setLayout(new FlowLayout());
    this.add(comboBox);
    this.add(text);
    this.add(set);
    this.add(field);

    addWindowListener(new WindowAdapter()
    {
      /**
       * Toggle visibility instead of closing the window.
       */
      @Override
      public void windowClosed(final WindowEvent e)
      {
        toggleVisibility();
      }

    });

  }

  public void toggleVisibility()
  {
    setVisible(!isVisible());

  }

  public static void setKeybind(String menuItem, char c)
  {
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
      ShortcutManager sh = ShortcutManager.newInstance();
      if (sh.getMap().containsValue(character))
      {
        JOptionPane.showMessageDialog(null, "the key " + character + " is already set", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
      else
      {
        setKeybind(menuItemText, character.charAt(0));
        manager.setKeybind(menuItemText, character.charAt(0));
        field
            .setText(String.format("Your keybind for %s is Control + %s", menuItemText, character));
        field.setVisible(true);

      }
    }
    else if (e.getSource().equals(comboBox))
    {
      comboBox.setSelectedItem(comboBox.getSelectedItem());
      System.out.println(comboBox.getSelectedItem());
    }

  }
}
