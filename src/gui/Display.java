package gui;

import javax.swing.*;
import java.awt.*;
/**
 * Class which represents the display of the calculator window.
 * 
 * @author Joshua Hairston
 * 
 * @version 11/2/2023
 * 
 * This code complies with the JMU Honor Code.
 */
public class Display extends JPanel
{
  private static final long serialVersionUID = 1L;

  public Display()
  {
    Dimension min = new Dimension(300, 50);
    this.setBackground(new Color(153, 204, 255, 130));
    this.setMinimumSize(min);
    this.setPreferredSize(min);
    this.setLayout(new GridBagLayout());
  }
  
  /**
   * Resets the top left and bottom right of the display.
   */
  public void reset() {
    clear();
  }
  
  /**
   * Clears the bottom right of the display.
   */
  public void clear() {
    
  }
  
  

}
