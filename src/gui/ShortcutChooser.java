package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import calculating.FractionModePublisher;
import calculating.FractionStylePublisher;
import calculating.FractionStylePublisher.FractionStyle;

public class ShortcutChooser extends JFrame implements ActionListener
{
  private JComboBox<String> comboBox;
  private JLabel label;
  private TextFieldHint text;
  private JButton set;

  public ShortcutChooser()
  {
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
    set.addActionListener(this);
    setLayout(new FlowLayout());
    this.add(comboBox);
    this.add(text);
    this.add(set);

  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    if(e.getActionCommand().equals("Set")) {
     String menuItemText = comboBox.getSelectedItem().toString();
     KeyStroke shortcutKey = KeyStroke.getKeyStroke(text.getText().charAt(0), 0);
     System.out.println(shortcutKey);
     switch(menuItemText) {
       case "Exit" -> Menu.exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(shortcutKey.getKeyCode(), KeyEvent.CTRL_DOWN_MASK));
       default -> System.out.println("unknown menu option");
     }
    }

  }
}
