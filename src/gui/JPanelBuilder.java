package gui;

import javax.swing.*;
import java.awt.*;

/**
 * JPanelBuilder
 */
public class JPanelBuilder extends JPanel
{

  public JPanelBuilder()
  {
    super();
  }

  public JPanelBuilder transparent()
  {
    setBackground(new Color(0, 0, 0, 0));
    return this;
  }
}
