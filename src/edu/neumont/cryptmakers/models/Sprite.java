package edu.neumont.cryptmakers.models;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

/**
 * This class demonstrates how to load an Image from an external file
 */
public class Sprite extends Component {

    BufferedImage img;
    int x;
    int y;

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(img, x, y, null);
    }

    public Sprite(String imgPath) {
        setX(0);
        setY(0);
        loadImg(imgPath);
    }

    public Sprite(String imgPath, int x, int y) {
        setX(x);
        setY(y);
        loadImg(imgPath);
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void loadImg(String imgPath) {
        try {
            img = ImageIO.read(new File(imgPath));

        } catch (IOException e) {
            System.err.println("There was a problem loading the image from \"" + imgPath + "\"");
            e.printStackTrace();
        }
    }

    public Dimension getPreferredSize() {
        if (img == null) {
            return new Dimension(32, 32);
        } else {
            return new Dimension(img.getWidth(null), img.getHeight(null));
        }
    }

}