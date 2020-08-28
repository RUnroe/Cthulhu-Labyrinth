package edu.neumont.cryptmakers.views;

import edu.neumont.cryptmakers.controllers.Game;
import edu.neumont.cryptmakers.controllers.Main;
import edu.neumont.cryptmakers.models.Maze;
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
    private static JTextPane InstructionsDisplay = new JTextPane();
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
    private final int JFrameHeight = 880;

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

    public static JTextPane getInstructionsDisplay() {
        return InstructionsDisplay;
    }

    public static void setInstructionsDisplay(JTextPane instructionsDisplay) {
        InstructionsDisplay = instructionsDisplay;
    }

    public static JTextPane getTreasureDisplay() {
        return TreasureDisplay;
    }

    public static void setTreasureDisplay(JTextPane treasureDisplay) {
        TreasureDisplay = treasureDisplay;
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
        //frame.setSize(new Dimension(JFrameWidth, JFrameHeight));
        frame.setMinimumSize(new Dimension(JFrameWidth, JFrameHeight));
        clickContainer.setLayout(new BoxLayout(clickContainer, BoxLayout.Y_AXIS));
        clickContainer.setFocusable(true);
        clickContainer.setMinimumSize(new Dimension(JFrameWidth, JFrameHeight));
        mapContainer.setLayout(new GridLayout(getMazeSize(), getMazeSize()));


        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(mapContainer, new GridBagConstraints());
        frame.add(wrapper, BorderLayout.NORTH);
        setupJTextPaneComponent(ScoreDisplay, BorderLayout.CENTER, 30);
        setupJTextPaneComponent(TextDisplay, BorderLayout.CENTER, 30);
        setupJTextPaneComponent(TreasureDisplay, BorderLayout.CENTER, 200);
        setupJTextPaneComponent(SpeedDisplay, BorderLayout.CENTER, 300);
        setupJTextPaneComponent(InstructionsDisplay, BorderLayout.CENTER, 400);

        frame.add(clickContainer);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);

    }

    public ImageIcon getScaledImage(ImageIcon imageIcon) {
        return new ImageIcon(imageIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
    }
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
                }else {
                    t.setImage(is64x() ? getScaledImage(undiscoveredSprite) : undiscoveredSprite);
                }

            }


//            mazeString += "\n";
        }
//        this.MazeDisplay.setText(mazeString);
        mapContainer.removeAll();
        for (int vPos = 0; vPos < m.getXSize(); vPos++) {
            for (int hPos = 0; hPos < m.getYSize(); hPos++) {
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
        ScoreDisplay.setText("Turn count: " + turnCount);
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
        public void createEndWindow(String fileName) {
            frame.getContentPane().removeAll();

            //        frame.getContentPane().add(getTextDisplay());
            Container c = new JPanel();
            c.add(new JLabel(new ImageIcon(fileName)));
            JButton button = new JButton("Restart");
            button.addActionListener(e -> restartGame());
            button.setBounds(300,550,100,50);

            frame.getContentPane().add(button);
            frame.getContentPane().add(c);


            frame.revalidate();
            frame.repaint();
        }
        private void restartGame() {
            frame.getContentPane().removeAll();
            frame.revalidate();
            frame.repaint();
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
        button.setBounds(300,550,100,50);

        frame.getContentPane().add(textDisplay);
        frame.getContentPane().add(button);
        frame.getContentPane().add(c);


        frame.revalidate();
        frame.repaint();
    }



}
