package gui;

import javax.swing.*;
import java.awt.*;

/**
 * JPanelBuilderich , a class which is specifically for building the builders.
 */
public class JPanelBuilder extends JPanel
{
  private static final long serialVersionUID = 1L;

  /**
   * Contructor for building Jpanles.
   */
  public JPanelBuilder()
  {
    super();
  }

  /**
   * This method is returning the JPanel but with a transparent color.
   * 
   * @return JpanelBuilder the Jpanel being specified.
   */
  public JPanelBuilder transparent()
  {
    setBackground(new Color(0, 0, 0, 0));
    return this;
  }
}
