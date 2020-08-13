package edu.neumont.cryptmakers.controllers;

import edu.neumont.cryptmakers.models.*;
import edu.neumont.cryptmakers.views.GameView;


import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.LineNumberReader;
import java.util.Random;

import static edu.neumont.cryptmakers.views.GameView.displayText;
import static edu.neumont.cryptmakers.views.GameView.getFrame;


public class Game {

    private int score = 0;


    private Random random = new Random();
    //private int mazeSize = random.nextInt(13) + 8;
    private static int mazeSize; //TODO: Randomize a maze size within the params listed in the spec

    private Maze maze;
    private int turnCount = 0;
    private int turnSpeed = 1;
    private int moveCount = 1;
    private static boolean mapShown = false;
    private static boolean is64x = false;
    private boolean gameOver = false;
    Player player = new Player();
    Monster monster = new Monster();
    Tile monsterTile;

    private GameView view ;

    public static int getMazeSize() {
        return mazeSize;
    }

    public static void setMazeSize(int mazeSize) {
        Game.mazeSize = mazeSize;
    }

    public static boolean isMapShown() {
        return mapShown;
    }

    public static void setMapShown(boolean mapShown) {
        Game.mapShown = mapShown;
    }

    public static boolean is64x() {
        return is64x;
    }

    public static void set64x(boolean is64x) {
        Game.is64x = is64x;
    }

    public Player getPlayer() {
        return player;
    }

    public Monster getMonster() {
        return monster;
    }

    public int getScore() {
        return this.score;
    }
    public Maze getMaze() {
        return this.maze;
    }
    public void setScore(int newScore) {
        this.score = newScore;
    }
    public void decrementScore(int value) {
        this.score -= value;
    }
    public void setMaze(Maze newMaze) {
        this.maze = newMaze;
    }



    public void run() {
        //TODO: This will be the main controller to control the game
        mazeSizePrompt();
        monsterTile = maze.getMaze()[monster.getVPos()][monster.getHPos()];
        monster.setPreviousTile(maze.getMaze()[monster.getVPos()][monster.getHPos()].getType());
        view = new GameView();
        updateText("There is a wall");
        for (int vPos = 0; vPos < maze.getXSize(); vPos++)
        {
            for (int hPos = 0; hPos < maze.getYSize(); hPos++)
            {
                Tile t = maze.getMaze()[vPos][hPos];
                switch (t.getType()) {
                    case PLAYER:
                        player.setHPos(hPos);
                        player.setVPos(vPos);
                    case START:
                        t.discover();
                        break;
                    case ENEMY:
                        monster.setHPos(hPos);
                        monster.setVPos(vPos);
                }
            }
        }
        updateDisplay();
        setupKeyPressEventListener(GameView.getClickContainer());
//        setupKeyPressEventListener(GameView.getMapContainer());
//        setupKeyPressEventListener(GameView.getTextDisplay());
//        setupKeyPressEventListener(GameView.getMazeDisplay());
//        setupKeyPressEventListener(GameView.getScoreDisplay());


    }

    public void mazeSizePrompt(){
        boolean invalid = true; //store if user needs to be re-prompted
        int tempSize = 0;
        while(invalid){
            try {
                String sizeStr = JOptionPane.showInputDialog("Please enter the size of the maze between 6 and 16"); //open prompt for user input
                if (sizeStr != null && !sizeStr.isEmpty()) { //make sure the user entered something
                    //System.out.println("sizeStr is not empty");
                    tempSize = Integer.parseInt(sizeStr); //set the size to the user input
                    //System.out.println(tempSize);
                    if (tempSize < 6 || tempSize > 16) { //the maze can be no larger than 16 and no smaller than 6
                        //error message pop up
                        JOptionPane.showMessageDialog(null, "Please enter a number between 6 and 16", "BAD SIZE", JOptionPane.WARNING_MESSAGE);
                        invalid = true;
                    } else { //user entered a valid size
                        //System.out.println(tempSize);
                        invalid = false;
                    }
                } else{
                    invalid = false;
                    System.exit(1); //exit the program if the user presses cancel
                }
            } catch(NumberFormatException nfe){
                //error message pop up
                JOptionPane.showMessageDialog(null, "Please enter a number between 6 and 16", "BAD SIZE", JOptionPane.WARNING_MESSAGE);
            }

        } // end while

        tempSize += 2; //add 2 to compensate for the outer walls of the maze
        mazeSize = tempSize;

        maze = new Maze(mazeSize, mazeSize); //create the maze object with the user sizes
    }

    public static int genRandNum(int min, int max) {
        int range = max - min + 1;
        return new Random().nextInt(range) + min;
    }

    private void updateDisplay() {
        GameView.getSpeedDisplay().setText("Speed: " + turnSpeed + " tiles");
        view.displayMaze(maze);
        view.displayTurnCount(turnCount);
    }
    private void updateText(String output) {
        displayText(output);
    }

    public boolean detectValidMove(GameCharacter character, int hTrans, int vTrans) {
        int x = character.getHPos() + hTrans;
        int y = character.getVPos() + vTrans;
        boolean isInsideMaze = y >= 0 && y < getMaze().getXSize() &&
                x >= 0 && x < getMaze().getYSize();
        if (isInsideMaze) {
            Tile tile = getMaze().getMaze()[y][x];
            TileEnum tileType = tile.getType();

            if (character instanceof Player) {

                tile.discover();
                if (tileType != TileEnum.WALL) {
                    if(tileType != TileEnum.START) {
                        maze.getMaze()[player.getVPos()][player.getHPos()].setType(TileEnum.PATH);
                        character.move(hTrans, vTrans);
                        tile.setType(TileEnum.PLAYER);
                        if (tileType == TileEnum.TREASURE) {
                            player.setTreasure(true);
                            //Change speed
                            turnSpeed = 1;
                            updateDisplay();
                        }
                        if (moveCount >= turnSpeed) {
                            incrementTurn();
                        } else {
                            moveCount++;
                        }
                        return true;
                    } else{
                        if (monster.isAwake() && player.hasTreasure()) {
                            displayText("You win, well done!");
                        }
                        if (monster.isAwake() && !player.hasTreasure()) {
                            displayText("It's a draw, next time get the treasure to win!");
                            GameView.createEndWindow();
                        }

                    }
                } else {
                    incrementTurn();
                }
            } else if (character instanceof Monster) {
                if (((Monster) character).isAwake()) {
                    maze.getMaze()[character.getVPos()][character.getHPos()].setType(((Monster) character).getPreviousTile());
                    System.out.println(monster.getPreviousTile());
                    monsterTile = tile;
                    if(tile.getType() != TileEnum.ENEMY) ((Monster) character).setPreviousTile(tile.getType());
                    character.move(hTrans, vTrans);
                    tile.setType(TileEnum.ENEMY);
                    return true;
                }
            }
        }
        return false;
    }

    public void setupKeyPressEventListener(JComponent component) {
        component.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                boolean isValidMove = false;
                switch(keyCode) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        //Try to move up
                        isValidMove = detectValidMove(player, 0, -1);
                        updateDisplay();
                        break;
                    case KeyEvent.VK_DOWN:
                        //Try to move down
                        isValidMove = detectValidMove(player, 0, 1);
                        updateDisplay();
                        break;
                    case KeyEvent.VK_LEFT:
                        //Try to move left
                        isValidMove = detectValidMove(player, -1, 0);
                        updateDisplay();
                        break;
                    case KeyEvent.VK_RIGHT:
                        //Try to move right
                        isValidMove = detectValidMove(player, 1, 0);
                        updateDisplay();
                        break;
                    case KeyEvent.VK_S:
                        //Change speed
                        if (moveCount > 1) {
                            incrementTurn();
                        }
                        if (turnSpeed == 1) {
                            turnSpeed = 3;
                        } else {
                            turnSpeed = 1;
                        }
                        updateDisplay();
                        break;
                    case KeyEvent.VK_M:
//                        for (Tile[] t : maze.getMaze()) {
//                            if (!mapShown) {
//                                for (Tile tile : t) {
//                                    tile.discover();
//                                }
//                            } else {
//                                for (Tile tile : t) {
//                                    tile.setVisible(false);
//                                }
//                            }
//                        }
                            setMapShown(!mapShown);
                            updateDisplay();
                            break;
                    case KeyEvent.VK_R:
                        set64x(!is64x());
                        updateDisplay();
                }
            }
        });
    }

    private void incrementTurn() {
        turnCount++;
        moveCount = 1;
        monsterController();
        updateDisplay();
    }


    private void monsterController() {
        //monster.wakeUp(); //TODO testing purposes
        int[] translation = monster.getNextMove(player.getHPos(), player.getVPos());
        detectValidMove(monster, translation[0], translation[1]);
        //System.out.println(monster.distanceFromPlayer(player.getHPos(), player.getVPos()));
        System.out.println(monster.distanceFromPlayer(player.getHPos(), player.getVPos()));
        switch(monster.distanceFromPlayer(player.getHPos(), player.getVPos())) {
            case 0:
                endGame();
                break;
            case 1:
                displayText("The monster is awake!");
                monster.wakeUp();
                break;
            case 2:
                //Alert player that monster is close
                if(monster.isAwake())displayText("The monster is near");
                else displayText("You hear the monster stirring!");
                break;
        }
    }

    private void endGame() {
        displayText("Get ganked");
        //getFrame().dispose();
    }

}
