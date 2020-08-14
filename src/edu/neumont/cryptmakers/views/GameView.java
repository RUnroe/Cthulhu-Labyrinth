package edu.neumont.cryptmakers.views;

import edu.neumont.cryptmakers.controllers.Game;
import edu.neumont.cryptmakers.models.Maze;
import edu.neumont.cryptmakers.models.Tile;
import edu.neumont.cryptmakers.models.TileEnum;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
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

    private final int JFrameWidth = 700;
    private final int JFrameHeight = 730;

    public GameView() {
        setupFrameView();
        for (int i = 0; i < 5; i++) {
            playerSprites[i] = new ImageIcon("images/sprite_player_"+ (i+1) +".png");
            int pImgNum = genRandNum(1, 10);
            playerSprite = playerSprites[pImgNum <= 2 ? 0 : pImgNum <= 4 ? 1 :
                    pImgNum <= 6 ? 2 : pImgNum <= 8 ? 3 : 4 ];
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

        JPanel wrapper = new JPanel( new GridBagLayout() );
        wrapper.add(mapContainer, new GridBagConstraints() );
        frame.add(wrapper, BorderLayout.NORTH );
//        frame.add(wrapper, BorderLayout.LINE_END);

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
        SimpleAttributeSet attrs=new SimpleAttributeSet();
        StyleConstants.setAlignment(attrs,StyleConstants.ALIGN_CENTER);
        clickContainer.add(component, pos);
        component.setEditable(false);
        component.setFocusable(false);
        component.setPreferredSize(new Dimension(JFrameWidth, height));
        StyledDocument doc=(StyledDocument)component.getDocument();
        doc.setParagraphAttributes(0,doc.getLength()-1,attrs,false);
        component.setFont( new Font("Courier", Font.PLAIN,16));
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

//        String mazeString = "";
        for (Tile[] tiles : m.getMaze()) {
            for (Tile t : tiles) {
                TileEnum type = t.getType();
                if (t.isVisible() || Game.isMapShown()) {
                    switch (type) {
                        case PLAYER:
//                            mazeString += " P";
                            t.setImage(is64x() ? getScaledImage(playerSprite) : playerSprite);
                            break;
                        case ENEMY:
//                            mazeString += "M";
                            t.setImage(is64x() ? getScaledImage(monsterSprite) : monsterSprite);
                            break;
                        case TREASURE:
//                            mazeString += "T";
                            t.setImage(is64x() ? getScaledImage(treasureSprite) : treasureSprite);
                            break;
                        case START:
//                            mazeString += "S";
                            t.setImage(is64x() ? getScaledImage(startSprite) : startSprite);
                            break;
                        case PATH:
//                            mazeString += "'~,";
                            t.setImage(is64x() ? getScaledImage(pathSprite) : pathSprite);
                            break;
                        case WALL:
//                            mazeString += "\u2589 ";
                            t.setImage(is64x() ? getScaledImage(wallSprites[(t.imageNum >= 4 ? 0 : 1)]) : wallSprites[(t.imageNum >= 4 ? 0 : 1)]);
                            break;
                        default:
//                            mazeString += "#";
                        t.setImage(is64x() ? getScaledImage(undiscoveredSprite) : undiscoveredSprite);
                            break;
                    }
                } else if (t.getType() == TileEnum.PLAYER || t.getType() == TileEnum.START) {
                    t.discover();
                } else {
                    t.setImage(is64x() ? getScaledImage(undiscoveredSprite) : undiscoveredSprite);
                }

            }


//            mazeString += "\n";
        }
//        this.MazeDisplay.setText(mazeString);
        mapContainer.removeAll();
        for (int vPos = 0; vPos < m.getXSize(); vPos++) {
            for (int hPos = 0; hPos < m.getYSize(); hPos++)
            {
                Tile t = m.getMaze()[vPos][hPos];
                JLabel imageLabel = new JLabel(t.getImage());
                imageLabel.setHorizontalAlignment(SwingConstants.LEFT);
                imageLabel.setVerticalAlignment(SwingConstants.TOP);
                GameView.getMapContainer().add(imageLabel);
                GameView.getFrame().revalidate();
                GameView.getFrame().repaint();

            }
        }
    }

    public void displayTurnCount(int turnCount) {
        this.ScoreDisplay.setText("Turn count: " + turnCount);
    }



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
        public static void createEndWindow() {
            frame.getContentPane().removeAll();
            //        frame.getContentPane().add(getTextDisplay());
            Container c = new JPanel();
            c.add(new JLabel(new ImageIcon("images/you-escaped.png")));
            frame.getContentPane().add(c);

            frame.revalidate();
            frame.repaint();
    }



}
