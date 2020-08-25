package edu.neumont.cryptmakers.views;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static edu.neumont.cryptmakers.controllers.Game.genRandNum;

public class MapScreen extends JPanel {

    public static final String[] playerSprites = new String[5];
    public static String playerSprite;
    public static final String monsterSprite = "images/sprite_mage.png";
    public static final String pathSprite = "images/sprite_floor.png";
    public static final String[] wallSprites = {"images/sprite_wall.png", "images/sprite_water.png"};
    public static final String treasureSprite = "images/sprite_treasure.png";
    public static final String startSprite = "images/sprite_door.png";
    public static final String undiscoveredSprite = "images/sprite_darkness.png";
    public static final CustomCanvas canvas = new CustomCanvas();
    BufferedImage[] images = new BufferedImage[6];
    String[] imagePaths = {
    monsterSprite,
    pathSprite,
    wallSprites[genRandNum(0, 1)],
    treasureSprite,
    startSprite,
    undiscoveredSprite
};



    public MapScreen() {
//
//        for (int i = 0; i < 5; i++) {
//            playerSprites[i] = "images/sprite_player_"+ (i+1) +".png";
//        }
//        int pImgNum = genRandNum(1, 10);
//        playerSprite = playerSprites[pImgNum <= 2 ? 0 : pImgNum <= 4 ? 1 :
//                    pImgNum <= 6 ? 2 : pImgNum <= 8 ? 3 : 4 ];
//
//        for (int i = 0; i < images.length; i++) {
//            try {
//                images[i] = ImageIO.read(getClass().getResourceAsStream(imagePaths[i]));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        CustomCanvas canvas =new CustomCanvas();
//        JFrame frame = new JFrame();
//        frame.add(canvas);
//        frame.setSize(400,400);
//        frame.setVisible(true);
//    }
        Image image;

// The image URL - change to where your image file is located!

        String imageURL = "images/sprite_treasure.png";

// This call returns immediately and pixels are loaded in the background

        image = Toolkit.getDefaultToolkit().getImage(imageURL);

// Create a frame

        JFrame frame = new JFrame();

// Add a component with a custom paint method
        JPanel panel = new JPanel();
        frame.add(panel);
        panel.add(canvas);

// Display the frame

        int frameWidth = 300;

        int frameHeight = 300;

        frame.setSize(frameWidth, frameHeight);

        canvas.paint(panel.getGraphics(), image, 0, 0);

        frame.setVisible(true);
    }
}
