package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JOptionPane;
import javax.swing.JWindow;

/**
 * Class for printing the history window's components.
 * 
 * @author Joshua Hairston
 * 
 * @version 11/16/2023
 * 
 *          This code complies with the JMU Honor Code.
 */
public class PrintableWrapper implements Printable, ActionListener
{
  private JWindow component;

  /**
   * Constructor for the PrintableWrapper class which takes in a JWindow (the history).
   * 
   * @param component
   *          the History component.
   */
  public PrintableWrapper(final JWindow component)
  {
    this.component = component;
  }

  /**
   * method which is called by the printing system for all pages until the print method returns
   * NO_SUCH_PAGE.
   * 
   * @param graphics
   *          the graphics of the page.
   * @param pageFormat
   *          describes the page orientation and its size.
   * @param pageIndex
   *          zero-based page number that will be rendered.
   * @throws PrinterException
   *           for conditions that go wrong in the printer system.
   */
  @Override
  public int print(final Graphics graphics, final PageFormat pageFormat, final int pageIndex)
      throws PrinterException
  {
    double componentHeight, componentWidth, height, scale, width, x, y;
    Graphics2D g;
    int status;

    g = (Graphics2D) graphics;

    status = Printable.NO_SUCH_PAGE;

    if (pageIndex == 0)
    {
      x = pageFormat.getImageableX();
      y = pageFormat.getImageableY();
      g.translate(x, y);

      // Scaling
      height = pageFormat.getImageableHeight();
      width = pageFormat.getImageableWidth();
      componentWidth = (double) component.getWidth();
      componentHeight = (double) component.getHeight();
      scale = Math.min(width / componentWidth, height / componentHeight);
      g.scale(scale, scale);

      component.printAll(g);

      status = Printable.PAGE_EXISTS;
    }

    return status;
  }

  /**
   * Method for when the Print Session menu item is pressed.
   * 
   * @param e
   *          the ActionEvent.
   */
  @Override
  public void actionPerformed(final ActionEvent e)
  {
    PrinterJob job = PrinterJob.getPrinterJob();
    try
    {
      job.setPrintable(this);
      boolean shouldPrint = job.printDialog();
      if (shouldPrint)
      {
        job.print();
      }
    }
    catch (PrinterException pe)
    {
      JOptionPane.showMessageDialog(component, "Cannot print", "Error", JOptionPane.ERROR_MESSAGE);
    }

  }
}
