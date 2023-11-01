package gui;


import javax.swing.*;
import java.awt.*;

public class Display extends JPanel
{
  private static final long serialVersionUID = 1L;

  public Display()
  {
    Dimension min = new Dimension(300, 50);
    this.setBackground(new Color(20, 20, 20, 20));
    this.setMinimumSize(min);
    this.setPreferredSize(min);
    this.setLayout(new GridBagLayout());
  }


}
