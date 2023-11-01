package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Display extends JTextArea implements ActionListener
{
  private static final long serialVersionUID = 1L;

  public Display()
  {
    super(4, 4);
    setupLayout();
  }

  private void setupLayout()
  {
    setBorder(new EmptyBorder(5, 5, 5, 5));
  }

  public void reset()
  {
    this.setText("");
  }

  public Dimension getMaximumSize()
  {
    return getPreferredSize();
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

}
