package edu.neumont.cryptmakers.views;

import edu.neumont.cryptmakers.controllers.Game;
import edu.neumont.cryptmakers.controllers.Main;
import edu.neumont.cryptmakers.models.Maze;
import edu.neumont.cryptmakers.models.Sprite;
import edu.neumont.cryptmakers.models.Tile;
import edu.neumont.cryptmakers.models.TileEnum;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;

import static edu.neumont.cryptmakers.controllers.Game.*;

public class GameView {
    private static BufferedReader buffy;

    private static JFrame frame = new JFrame();
    private static JPanel clickContainer = new JPanel();
    private static JPanel mapContainer = new JPanel();
    private static JTextPane TextDisplay = new JTextPane();
    private static JTextPane MazeDisplay = new JTextPane();
    private static JTextPane ScoreDisplay = new JTextPane();
    private static JTextPane TreasureDisplay = new JTextPane();
    private static JTextPane SpeedDisplay = new JTextPane();
    private static JLabel[][] imageLabels;
    public static final ImageIcon[] playerSprites = new ImageIcon[5];
    public static ImageIcon playerSprite;
    public static final ImageIcon monsterSprite = new ImageIcon("images/sprite_mage.png");
    public static final ImageIcon pathSprite = new ImageIcon("images/sprite_floor.png");
    public static final ImageIcon[] wallSprites = {new ImageIcon("images/sprite_wall.png"), new ImageIcon("images/sprite_water.png")};
    public static final ImageIcon treasureSprite = new ImageIcon("images/sprite_treasure.png");
    public static final ImageIcon startSprite = new ImageIcon("images/sprite_door.png");
    public static final ImageIcon undiscoveredSprite = new ImageIcon("images/sprite_darkness.png");
    public static final String[] playerSpritePaths = new String[5];
    public static final String playerSpritePath = "src/images/sprite_player.png";
    public static final String monsterSpritePath = "src/images/sprite_mage.png";
    public static final String floorSpritePath = "src/images/sprite_floor.png";
    public static final String[] wallSpritePaths = {"src/images/sprite_wall.png",};
    public static final String treasureSpritePath = "src/images/sprite_treasure.png";
    public static final String startSpritePath = "src/images/sprite_door.png";
    public static final String undiscoveredSpritePath = "src/images/sprite_mage.png";

    private final int JFrameWidth = 1000/*700*/;
    private final int JFrameHeight = 1200/*730*/;

    private final CustomCanvas canvas = new CustomCanvas();

    public GameView() {
//        createIntroWindow();
        setupFrameView();
        for (int i = 0; i < 5; i++) {
            playerSprites[i] = new ImageIcon("images/sprite_player_" + (i + 1) + ".png");
            int pImgNum = genRandNum(1, 10);
            playerSprite = playerSprites[pImgNum <= 2 ? 0 : pImgNum <= 4 ? 1 :
                    pImgNum <= 6 ? 2 : pImgNum <= 8 ? 3 : 4];
        }

    }

    public static JTextPane getSpeedDisplay() {
        return SpeedDisplay;
    }

    public static void setSpeedDisplay(JTextPane speedDisplay) {
        SpeedDisplay = speedDisplay;
    }

    public static JTextPane getTreasureDisplay() {
        return TreasureDisplay;
    }

    public static void setTreasureDisplay(JTextPane treasureDisplay) {
        TreasureDisplay = TreasureDisplay;
    }

    public static JPanel getMapContainer() {
        return mapContainer;
    }

    public static void setMapContainer(JPanel mapContainer) {
        GameView.mapContainer = mapContainer;
    }

    public static JLabel[][] getImageLabels() {
        return imageLabels;
    }

    public static void setImageLabels(JLabel[][] imageLabels) {
        GameView.imageLabels = imageLabels;
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static void setFrame(JFrame frame) {
        GameView.frame = frame;
    }

    public static JPanel getClickContainer() {
        return clickContainer;
    }

    public static void setClickContainer(JPanel clickContainer) {
        GameView.clickContainer = clickContainer;
    }

    public static JTextPane getTextDisplay() {
        return TextDisplay;
    }

    public static void setTextDisplay(JTextPane textDisplay) {
        TextDisplay = textDisplay;
    }

    public static JTextPane getMazeDisplay() {
        return MazeDisplay;
    }

    public static void setMazeDisplay(JTextPane mazeDisplay) {
        MazeDisplay = mazeDisplay;
    }

    public static JTextPane getScoreDisplay() {
        return ScoreDisplay;
    }

    public static void setScoreDisplay(JTextPane scoreDisplay) {
        ScoreDisplay = scoreDisplay;
    }


    public void setupFrameView() {
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(JFrameWidth, JFrameHeight));
        clickContainer.setLayout(new BoxLayout(clickContainer, BoxLayout.Y_AXIS));
        clickContainer.setFocusable(true);
        clickContainer.setMinimumSize(new Dimension(JFrameWidth, JFrameHeight));
        mapContainer.setLayout(new GridLayout(getMazeSize(), getMazeSize()));
//        mapContainer.
//        mapContainer.setSize(16*16, 16*16);
//        JPanel tilePanel = new JPane( new GridLayout(0, 2, 5, 5) );
//        tilePanel.add( new GameTile(...) );
//        tilePanel.add( new GameTile(...) );

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(mapContainer, new GridBagConstraints());
//        frame.add(mapContainer, BorderLayout.NORTH);
//        mapContainer.setSize(700, 700);
//        frame.add(mapContainer);
        frame.add(wrapper, BorderLayout.NORTH);

//        setupJTextPaneComponent(MazeDisplay, BorderLayout.NORTH, 350);
        setupJTextPaneComponent(ScoreDisplay, BorderLayout.CENTER, 30);
        setupJTextPaneComponent(TextDisplay, BorderLayout.CENTER, 30);
        setupJTextPaneComponent(TreasureDisplay, BorderLayout.CENTER, 200);
        setupJTextPaneComponent(SpeedDisplay, BorderLayout.CENTER, 300);

        frame.add(clickContainer);
//        frame.add(mapContainer);
//        mapContainer.add(new JLabel(new ImageIcon("src/images/sprite_floor.png")));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public ImageIcon getScaledImage(ImageIcon imageIcon) {
        return new ImageIcon(imageIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
    }

    //    private void setupKeyPressEventListener(JComponent component) {
//        component.addKeyListener(new KeyAdapter() {
//            public void keyPressed(KeyEvent e) {
//                int keyCode = e.getKeyCode();
//                switch(keyCode) {
//                    case KeyEvent.VK_UP:
//                        //Try to move up
//                        displayText("up");
//                        break;
//                    case KeyEvent.VK_DOWN:
//                        //Try to move down
//                        displayText("down");
//                        break;
//                    case KeyEvent.VK_LEFT:
//                        //Try to move left
//                        displayText("left");
//                        break;
//                    case KeyEvent.VK_RIGHT :
//                        //Try to move right
//                        displayText("right");
//                        break;
//                }
//            }
//        });
//    }
    private void setupJTextPaneComponent(JTextPane component, String pos, int height) {
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attrs, StyleConstants.ALIGN_CENTER);
        clickContainer.add(component, pos);
        component.setEditable(false);
        component.setFocusable(false);
        component.setPreferredSize(new Dimension(JFrameWidth, height));
        StyledDocument doc = (StyledDocument) component.getDocument();
        doc.setParagraphAttributes(0, doc.getLength() - 1, attrs, false);
        component.setFont(new Font("Courier", Font.PLAIN, 16));
    }


    //Stubbed out from UML. Feel free to add any more methods that you need
    public void displayMaze(Maze m) {
        /*String mazeString = "";
        for (Tile[] tiles : m.getMazeArray()) {
            for (Tile t : tiles) {
                if (t.isVisible()) {
                    if (t.getType() == TileEnum.WALL) {
                        mazeString += "█████";
                    } else if (t.getType() == TileEnum.PLAYER) {
                        mazeString += "  P  ";
                    } else if (t.getType() == TileEnum.TREASURE) {
                        mazeString += "  T  ";
                    } else if (t.getType() == TileEnum.ENEMY) {
                        mazeString += "  E  ";
                    } else if (t.getType() == TileEnum.PATH) {
                        mazeString += "  ░  ";
                    }
                } else {
                    mazeString += ("\u2589");
                }

            }
            mazeString += "\n";
        }
        this.MazeDisplay.setText(mazeString);*/

        String mazeString = "";
//        frame = new JFrame("Help");
//        frame.add(new Sprite("src/images/sprite_player.png"));
//        JPanel panel = new JPanel();
//        frame.add(panel);
        for (Tile[] tiles : m.getMaze()) {
//            for (JLabel[] l : labels)
//            for (int i = 0; i < l.length; i++) {
//                l[i].setIcon(tiles[i].getImage());
//            }
            for (Tile t : tiles) {
//                t.setX(t.getX()*16);
//                t.setY(t.getY()*16);
//                t.sprite.getImg().getScaledInstance(32, 32, Image.SCALE_SMOOTH)
//                mapContainer.add(t);
//                t.setLocation(t.getX(), t.getY());
            }
        }

        for (Tile[] tiles : m.getMaze()) {
            for (Tile t : tiles) {
                TileEnum type = t.getType();
                if (t.isVisible() || Game.isMapShown()) {
                    switch (type) {
                        case PLAYER:
//                            mazeString += " P";
//                            t.setImage(is64x() ? getScaledImage(playerSprite) : playerSprite);
//                            t.sprite.loadImg(playerSpritePath);
//                            t.label.setIcon(getScaledImage(playerSprite));
                            t.changeImage(playerSpritePath);

                            break;
                        case ENEMY:
//                            mazeString += "M";
//                            t.setImage(is64x() ? getScaledImage(monsterSprite) : monsterSprite);
//                            t.sprite.loadImg(monsterSpritePath);
//                            t.label.setIcon(getScaledImage(monsterSprite));
                            t.changeImage(monsterSpritePath);
                            break;
                        case TREASURE:
//                            mazeString += "T";
//                            t.setImage(is64x() ? getScaledImage(treasureSprite) : treasureSprite);
//                            t.sprite.loadImg(treasureSpritePath);
//                            t.label.setIcon(getScaledImage(treasureSprite));
                            t.changeImage(treasureSpritePath);
                            break;
                        case START:
//                            mazeString += "S";
//                            t.setImage(is64x() ? getScaledImage(startSprite) : startSprite);
//                            t.sprite.loadImg(startSpritePath);
//                            t.label.setIcon(getScaledImage(startSprite));
                            t.changeImage(startSpritePath);
                            break;
                        case PATH:
//                            mazeString += "'~,";
//                            t.setImage(is64x() ? getScaledImage(pathSprite) : pathSprite);
//                            t.sprite.loadImg(floorSpritePath);
//                            t.label.setIcon(getScaledImage(pathSprite));
                            t.changeImage(floorSpritePath);
                            break;
                        case WALL:
//                            mazeString += "\u2589 ";
//                            t.setImage(is64x() ? getScaledImage(wallSprites[(t.imageNum >= 4 ? 0 : 1)]) : wallSprites[(t.imageNum >= 4 ? 0 : 1)]);
//                            t.sprite.loadImg(wallSpritePaths[0]);
//                            t.label.setIcon(getScaledImage(wallSprites[(t.imageNum >= 4 ? 0 : 1)]));
                            t.changeImage(wallSpritePaths[0]);
                            break;
                        default:
//                            mazeString += "#";
//                            t.setImage(is64x() ? getScaledImage(undiscoveredSprite) : undiscoveredSprite);
//                            t.sprite.loadImg(undiscoveredSpritePath);
//                            t.label.setIcon(getScaledImage(undiscoveredSprite));
                            t.changeImage(undiscoveredSpritePath);
                            break;
                    }
                } else if (t.getType() == TileEnum.PLAYER || t.getType() == TileEnum.START) {
                    t.discover();
                } else {
//                    t.setImage(is64x() ? getScaledImage(undiscoveredSprite) : undiscoveredSprite);
//                    t.sprite.loadImg(undiscoveredSpritePath);
//                    t.label.setIcon(t.getImage());
                    t.changeImage(undiscoveredSpritePath );
                }
            }
//            frame.repaint();


//            mazeString += "\n";
        }
//        this.MazeDisplay.setText(mazeString);
//        mapContainer.removeAll();
//        for (int vPos = 0; vPos < m.getXSize(); vPos++) {
//            for (int hPos = 0; hPos < m.getYSize(); hPos++) {
//                Tile t = m.getMaze()[vPos][hPos];
//                JLabel imageLabel = new JLabel(t.getImage());
//                imageLabel.setHorizontalAlignment(SwingConstants.LEFT);
//                imageLabel.setVerticalAlignment(SwingConstants.TOP);
//                GameView.getMapContainer().add(imageLabel);
//                GameView.getFrame().revalidate();
//                GameView.getFrame().repaint();
//
//
//            }
//            this.MazeDisplay.setText(mazeString);
//        mapContainer.removeAll();
//        for (int vPos = 0; vPos < m.getXSize(); vPos++) {
//            for (int hPos = 0; hPos < m.getYSize(); hPos++) {
//                Tile t = m.getMaze()[vPos][hPos];
////                t.setX(hPos);
////                t.setY(vPos);
////                t.sprite.getImg().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
////                mapContainer.add(t);
//                getMapContainer().add(t);
////                JLabel imageLabel = new JLabel(t.getImage());
////                imageLabel.setHorizontalAlignment(SwingConstants.LEFT);
////                imageLabel.setVerticalAlignment(SwingConstants.TOP);
////                GameView.getMapContainer().add(imageLabel);
////                GameView.getFrame().revalidate();
//                getFrame().repaint();
//
//
//            }

//        }
    }

//        for (Tile[] tiles : m.getMaze()) {
//            for (Tile t : tiles) {
//                t.getImage().paintIcon(frame, frame.getGraphics(), 0,0);
//            }
//    }

//    }
    static JLabel[][] labels;
    public static void createMapDisplay(Maze m) {
//        labels = new JLabel[m.getMaze().length][m.getMaze().length];
//        mapContainer.removeAll();
        for (Tile[] tiles : m.getMaze()) {
            for (Tile t : tiles) {
                t.label.setHorizontalAlignment(SwingConstants.LEFT);
                t.label.setVerticalAlignment(SwingConstants.TOP);
                GameView.getMapContainer().add(t.label);
            }
//            for (JLabel[] l : labels) {
//                for (int i = 0; i < l.length; i++) {
//                    l[i] = new JLabel(tiles[i].getImage());
//                }
//            }
        }
//        mapContainer.removeAll();
//        for (JLabel l[] : labels) {
//            for (int i = 0; i < l.length; i++) {
//                l[i].setHorizontalAlignment(SwingConstants.LEFT);
//                l[i].setVerticalAlignment(SwingConstants.TOP);
//                GameView.getMapContainer().add(l[i]);
//            }
//        }
//        getMapContainer().repaint();

//        for (int vPos = 0; vPos < m.getXSize(); vPos++) {
//            for (int hPos = 0; hPos < m.getYSize(); hPos++) {
//                Tile t = m.getMaze()[vPos][hPos];
//                JLabel imageLabel = new JLabel(t.getImage());
//                imageLabel.setHorizontalAlignment(SwingConstants.LEFT);
//                imageLabel.setVerticalAlignment(SwingConstants.TOP);
//                GameView.getMapContainer().add(imageLabel);
//                GameView.getFrame().revalidate();
//                GameView.getFrame().repaint();
//
//            }
//        }
    }


    public void displayTurnCount(int turnCount) {
        this.ScoreDisplay.setText("Turn count: " + turnCount);
    }
//        canvas.paint(frame.getGraphics(), new ImageIcon("images/treasure.png"), 0,0);
//        frame.validate();
//        frame.repaint();


    public static void displayText(String text) {
        TextDisplay.setText(text);
    }

    public static int promptForInt(String prompt, int min, int max) {
        if (max < min) {
            throw new IllegalArgumentException("The min can't be greater than the max");
        } else {
            int userNum = 0;
            boolean isInvalid = true;

            do {
                String input = promptForString(prompt);

                try {
                    userNum = Integer.parseInt(input);
                    isInvalid = userNum < min || userNum > max;
                } catch (NumberFormatException var7) {
                }

                if (isInvalid) {
                    System.out.println("You must enter a number between " + min + " and " + max + ". Please, try again.");
                }
            } while (isInvalid);

            return userNum;
        }
    }

    public static String promptForString(String prompt) {
        if (prompt != null && !prompt.trim().isEmpty()) {
            String input = null;
            boolean isInvalid = true;

            do {
                System.out.print(prompt);

                try {
                    input = buffy.readLine();
                    isInvalid = input == null || input.trim().isEmpty();
                    if (isInvalid) {
                        System.out.println("Your input cannot be null, empty, or just white space. Please, try again.");
                    }
                } catch (IOException var4) {
                    System.out.println("There was a technical issue. Please, try again.");
                }
            } while (isInvalid);

            return input;
        } else {
            throw new IllegalArgumentException("The prompt can't be null, empty, or whitespace-only.");
        }
    }

    public int displayMenu(String[] options, boolean withQuit) {
        if (options == null) {
            options = new String[0];
        }

        if (options.length == 0 && !withQuit) {
            throw new IllegalArgumentException("There must be at least one option to select.");
        } else {
            StringBuilder sb = new StringBuilder("Please Choose one of the Following: \n\n");

            for (int i = 0; i < options.length; ++i) {
                sb.append(i + 1).append(") ").append(options[i]).append("\n");
            }

            if (options.length > 0) {
                sb.append("\n");
            }

            if (withQuit) {
                sb.append("0) Quit\n\n");
            }

            sb.append("Enter the number of your selection: ");
            String menuString = sb.toString();
            int min = withQuit ? 0 : 1;
            int max = options.length;
            return promptForInt(menuString, min, max);
        }
    }

    public static void createEndWindow(String fileName) {
        frame.getContentPane().removeAll();
        //        frame.getContentPane().add(getTextDisplay());
        Container c = new JPanel();
        c.add(new JLabel(new ImageIcon(fileName)));
        JButton button = new JButton("Restart");
        button.addActionListener(e -> restartGame());
        button.setBounds(300, 550, 100, 50);

        frame.getContentPane().add(button);
        frame.getContentPane().add(c);


        frame.revalidate();
        frame.repaint();
    }

    private static void restartGame() {
        frame.getContentPane().removeAll();
        frame.dispose();
        Main.restart();
    }

    public static void createIntroWindow() {
//        frame.getContentPane().removeAll();
        //        frame.getContentPane().add(getTextDisplay());
        Container c = new JPanel();
        JTextPane textDisplay = new JTextPane();
        String text = "Welcome to Cthulhu's Labyrinth";
        text += "\nYou have entered the treacherous dungeon in search" +
                "\nof mystical treasures that will make you reich and famous." +
                "\nYour goal is to find the treasure and make it back to the entrance." +
                "There is an ancient guardian sleeping somewhere. If you manage to wake " +
                "\nthe beast, run for your life back to the entrance. It will kill you" +
                "\notherwise. Good luck, adventurer!" +
                "\n\nControls: \nMovement: Arrow keys or WASD keys\nVolume: Minus and Equals keys" +
                "\nTip: Pay attention to the text at the bottom of the screen!";
        textDisplay.setText(text);
//        c.add(textDisplay);
        JButton button = new JButton("Begin");
        button.addActionListener(e -> frame.getContentPane().removeAll());
        button.setBounds(300, 550, 100, 50);

        frame.getContentPane().add(textDisplay);
        frame.getContentPane().add(button);
        frame.getContentPane().add(c);


        frame.revalidate();
        frame.repaint();
    }


}
