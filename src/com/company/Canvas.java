package com.company;

import javax.swing.*;
import java.awt.*;
import static com.company.Hangman.*;

public class Canvas extends JPanel {
    Canvas() {
        setFocusable(true);
        setPreferredSize(new Dimension(550, 400));
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3f));

        switch (6 - GUESS) {
            case 6:
                g2.drawLine(325, 260, 365, 300);
                g2.drawLine(325, 260, 285, 300);
            case 5:
                g2.drawLine(325, 150, 365, 190);
                g2.drawLine(325, 150, 285, 190);
            case 4:
                g2.drawLine(325, 150, 325, 260);
            case 3:
                g2.drawOval(295, 90, 60, 60);
            case 2:
                g2.drawLine(175, 50, 325, 50);
                g2.drawLine(175, 125, 250, 50);
                g2.drawLine(325, 50, 325, 90);
            case 1:
                g2.drawLine(125, 350, 400, 350);
                g2.drawLine(175, 350, 175, 50);
                break;
        }
    }
}
