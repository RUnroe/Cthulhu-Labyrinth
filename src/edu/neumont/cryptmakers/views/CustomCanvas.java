package edu.neumont.cryptmakers.views;

import javax.swing.*;
import java.awt.*;

public class CustomCanvas extends Canvas {
//    public void paint(Graphics g, ImageIcon imageIcon, int hPos, int vPos) {
//
//        Toolkit t = Toolkit.getDefaultToolkit();
//        Image i = imageIcon.getImage();
//        g.drawImage(i, hPos, vPos, this);
//
//    }

    public void paint(Graphics g, Image image, int x, int y) {

        // Retrieve the graphics context; this object is used to paint shapes

        Graphics2D g2d = (Graphics2D) g;

        /**
         * Draw an Image object
         * The coordinate system of a graphics context is such that the origin is at the
         * northwest corner and x-axis increases toward the right while the y-axis increases
         * toward the bottom.
         */

        g.drawImage(image, x, y, this);

    }


}
