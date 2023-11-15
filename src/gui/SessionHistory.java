package gui;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

public class SessionHistory implements ActionListener {
  
  public SessionHistory() {
    //1. Creating the frame.
    JFrame  frame = new JFrame("Session History");
    
    //button to open the window
    JButton a = new JButton(">");
    
    


    //3. Create components and put them in the frame.
    //...create emptyLabel...
    frame.getContentPane().add(a, BorderLayout.CENTER);

    frame.setSize(50, 400);
    
    frame.setLocation(500, 500);
    
    frame.setUndecorated(true);
    
    //5. Show it.
    frame.setVisible(true);
    
    
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    
    
  }
  

}