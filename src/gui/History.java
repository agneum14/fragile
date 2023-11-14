package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class History extends JFrame implements ActionListener
{
  private static final long serialVersionUID = 1L;
  private static final int CLOSED = 0;
  private static final int OPENED = 1;
  private int status;
  private JButton button;
  private CalculatorWindow window;

  public History(CalculatorWindow window)
  {
    super();
    button = new JButton(">");
    this.status = CLOSED;
    setBackground(Display.POWDER_BLUE);
    setupLayout();
    setSize(30, 300);
    setUndecorated(true);
    setResizable(false);
    this.window = window;
    setVisible(true);
  }

  private void setupLayout()
  {
    setLayout(new BorderLayout());
    button.addActionListener(this);
    add(button, BorderLayout.EAST);

  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    String ac = e.getActionCommand();
    if (ac.equals(">"))
    {
      if (status == CLOSED)
      {
        setSize(300, 300);
        status = OPENED;
        button.setText("<");

      }

    }
    else
    {
      setSize(30, 300);
      status = CLOSED;
      button.setText(">");
    }
  }

  public void setHistoryLocation(int x, int y)
  {
    this.setLocation(x, y);

  }
}
