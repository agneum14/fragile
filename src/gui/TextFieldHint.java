package gui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Custom JTextArea class where a hint appears if the field is empty.
 * 
 * @author Joshua Hairston
 * 
 * @version 12/11/2023
 */
public class TextFieldHint extends JTextArea implements FocusListener
{
  private static final long serialVersionUID = 1L;
  private final String hint;
  private boolean showingHint;

  /**
   * Contructs a TextFieldHint with the specified hint.
   * 
   * @param hint
   *          the hint text to display.
   */
  public TextFieldHint(final String hint)
  {
    super(hint);
    this.hint = hint;
    this.showingHint = true;
    super.addFocusListener(this);
  }

  /**
   * Sets the text to empty and showing hint to false.
   */
  @Override
  public void focusGained(final FocusEvent e)
  {
    if (this.getText().isEmpty())
    {
      super.setText("");
      showingHint = false;
    }
  }

  /**
   * sets the hint to the field and makes showing hint true.
   */
  @Override
  public void focusLost(final FocusEvent e)
  {
    if (this.getText().isEmpty())
    {
      super.setText(hint);
      showingHint = true;
    }
  }

  /**
   * Override version of get text which returns either a empty string or a hint depending on the
   * showinghint value.
   */
  @Override
  public String getText()
  {
    return showingHint ? "" : super.getText();
  }

}
