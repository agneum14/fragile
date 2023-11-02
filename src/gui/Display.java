package gui;

import calculating.CurrentMixedFraction;
import calculating.MixedFraction;
import gui.mf.CurrentMixedFractionPanel;
import gui.mf.MixedFractionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Class which represents the display of the calculator window.
 *
 * @author Joshua Hairston
 * @version 11/2/2023
 *
 *          This code complies with the JMU Honor Code.
 */
public class Display extends JPanel {
    public enum Op {
        ADD, SUB, DIV, MULT
    }

    private static final Color POWDER_BLUE = new Color(210, 237, 255);
    private JPanel cep;
    private JPanel cmfp;
    private CurrentMixedFraction cmf;
    private MixedFraction eval;
    private Op cop;

    public Display() {
        this.setBackground(POWDER_BLUE);
        this.setLayout(new GridBagLayout());

        eval = new MixedFraction(1, 0, 0, 1);
        cep = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cmf = new CurrentMixedFraction();
        cmfp = new CurrentMixedFractionPanel(cmf);
        cop = null;

        draw();
    }

    private void draw() {
        GridBagConstraints c;
        removeAll();

        // add current expression panel
        {
            c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.anchor = GridBagConstraints.NORTHWEST;
            add(cep, c);
        }

        // float current expression panel west
        {
            c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 0;
            c.weightx = 1;
            add(new JPanel(), c);
        }

        // float current expression panel north
        {
            c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 1;
            c.weighty = 1;
            add(new JPanel(), c);
        }

        // add current mixed fraction panel
        {
            c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 2;
            c.anchor = GridBagConstraints.SOUTHEAST;
            add(cmfp, c);
        }

        {
            c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 2;
            c.weightx = 1;
            add(new JPanel(), c);
        }

        revalidate();
        repaint();
    }

    public void addToCEP(JPanel p) {
        cep.add(p);
        draw();
    }

    public void addToCEP(JLabel l) {
        cep.add(l);
        draw();
    }

    public void clearCEP() {
        cep = new JPanel(new FlowLayout(FlowLayout.LEFT));
        draw();
    }

    public void updateCMFP() {
        cmfp = new CurrentMixedFractionPanel(cmf);
        draw();
    }

    public void handleButton(ActionEvent e) {
        final String ac = e.getActionCommand();

        Integer digit;
        try {
            digit = Integer.parseInt(ac);
        } catch (NumberFormatException nfe) {
            digit = null;
        }

        if (digit != null) {
            cmf.addDigit(digit);
            updateCMFP();
        } else if (ac.equals(CalculatorButtons.BACKSPACE)) {
            cmf.removeDigit();
            updateCMFP();
        } else if (ac.equals(CalculatorButtons.SIGN)) {
            cmf.changeSign();
            updateCMFP();
        } else if (ac.equals(CalculatorButtons.POSITION)) {
            cmf.nextPos();
            updateCMFP();
        }
    }
}
