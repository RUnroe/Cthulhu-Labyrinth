package edu.neumont.cryptmakers.views;

import edu.neumont.cryptmakers.models.Maze;
import edu.neumont.cryptmakers.models.Tile;
import edu.neumont.cryptmakers.models.TileEnum;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataInput;
import java.io.IOException;

public class GameView {
    private static BufferedReader buffy;

    private JFrame frame = new JFrame();
    private JPanel clickContainer = new JPanel();
    private JTextPane TextDisplay = new JTextPane();
    private JTextPane MazeDisplay = new JTextPane();
    private JTextPane ScoreDisplay = new JTextPane();

    private final int JFrameWidth = 700;
    private final int JFrameHeight = 730;

    public GameView() {
        setupFrameView();
    }

    public void setupFrameView() {
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(JFrameWidth, JFrameHeight));
        clickContainer.setLayout(new BoxLayout(clickContainer, BoxLayout.Y_AXIS));
        clickContainer.setFocusable(true);
        clickContainer.setMinimumSize(new Dimension(JFrameWidth, JFrameHeight));
        setupKeyPressEventListener(clickContainer);
        setupKeyPressEventListener(TextDisplay);
        setupKeyPressEventListener(MazeDisplay);
        setupKeyPressEventListener(ScoreDisplay);



        setupJTextPaneComponent(MazeDisplay, BorderLayout.NORTH, 350);
        setupJTextPaneComponent(ScoreDisplay, BorderLayout.CENTER, 30);
        setupJTextPaneComponent(TextDisplay, BorderLayout.SOUTH, 350);

        frame.add(clickContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void setupKeyPressEventListener(JComponent component) {
        component.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch(keyCode) {
                    case KeyEvent.VK_UP:
                        //Try to move up
                        displayText("up");
                        break;
                    case KeyEvent.VK_DOWN:
                        //Try to move down
                        displayText("down");
                        break;
                    case KeyEvent.VK_LEFT:
                        //Try to move left
                        displayText("left");
                        break;
                    case KeyEvent.VK_RIGHT :
                        //Try to move right
                        displayText("right");
                        break;
                }
            }
        });
    }
    private void setupJTextPaneComponent(JTextPane component, String pos, int height) {
        SimpleAttributeSet attrs=new SimpleAttributeSet();
        StyleConstants.setAlignment(attrs,StyleConstants.ALIGN_CENTER);
        clickContainer.add(component, pos);
        component.setEditable(false);
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

        String mazeString = "";
        for(Tile[] tiles : m.getMaze()) {
            for (Tile t : tiles) {
                mazeString += "\u2589";
            }


            mazeString += "\n";
        }
        this.MazeDisplay.setText(mazeString);
    }

    public void displayTurnCount(int turnCount) {
        this.ScoreDisplay.setText("Turn count: " + turnCount);
    }



    public void displayText(String text) {
        this.TextDisplay.setText(text);
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
}
