package gui;
import javax.swing.*;
import gui.mf.MixedFractionPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
* Class for the session history of the calculator
*
* @author Joshua Hairston, Zachary Buchan
* @version 11/13/2023
*
* This Code Complies with the JMU Honor code.
*/
public class History extends JWindow implements ActionListener {
  private static final long serialVersionUID = 1L;
  private static final int CLOSED = 0;
  private static final int OPENED = 1;
  private int status;
  private JButton button;
  private Timer timer;
  private JPanel historyPanel;
  private ArrayList<MixedFractionPanel> mixedFractionPanels; // List to store mixed fraction panels
  /**
   * Constructor for the History class
   */
  public History(JFrame frame) {
      super(frame);
      this.status = CLOSED; // Tracking the status of the window.
      setupLayout();
      setSize(30, 300);
      setVisible(true);
      mixedFractionPanels = new ArrayList<>();
  }
  /**
   * Setting up the background, the layout of the history window, and the animation.
   */
  private void setupLayout() {
      getContentPane().setBackground(Display.POWDER_BLUE);
      setLayout(new BorderLayout());
      button = new JButton(">");
      button.addActionListener(this);
      add(button, BorderLayout.EAST);
      // Initialize history panel
      historyPanel = new JPanel();
      historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
      JScrollPane scrollPane = new JScrollPane(historyPanel);
      add(scrollPane, BorderLayout.CENTER);
      // Animation
      timer = new Timer(10, e -> animate());
  }
  /**
   * Method for checking to see if the window is either closed or open.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
      String ac = e.getActionCommand();
      if (ac.equals(">")) {
          if (status == CLOSED) {
              status = OPENED;
              button.setText("<");
              timer.start();
          }
      } else {
          status = CLOSED;
          button.setText(">");
          timer.start();
      }
  }
  /**
   * Animates the button.
   */
  private void animate() {
      int targetWidth = (status == OPENED) ? 300 : 30;
      int currentWidth = getWidth();
      if (currentWidth != targetWidth) {
          int newWidth = (currentWidth < targetWidth) ? currentWidth + 5 : currentWidth - 5;
          setSize(newWidth, 300);
          if (currentWidth < targetWidth && newWidth >= targetWidth) {
              timer.stop();
          } else if (currentWidth > targetWidth && newWidth <= targetWidth) {
              timer.stop();
          }
      }
  }
  /**
   * Add a mixed fraction panel to the history.
   */
  public void addMixedFractionPanel(MixedFractionPanel mixedFractionPanel) {
    mixedFractionPanels.add(mixedFractionPanel);
    updateHistoryDisplay();
  }
 
  private void updateHistoryDisplay() {
    historyPanel.removeAll(); // Clear existing components in the historyPanel
    // Add all mixed fraction panels to the historyPanel
    for (MixedFractionPanel mixedFractionPanel : mixedFractionPanels) {
        historyPanel.add(mixedFractionPanel);
    }
    revalidate();
    repaint();
}
 
  /**
   * Add a label to the history.
   */
  public void addLabel(JLabel label) {
      historyPanel.add(label);
  }
  public void setHistoryLocation(int x, int y) {
      setLocation(x, y);
  }
}
