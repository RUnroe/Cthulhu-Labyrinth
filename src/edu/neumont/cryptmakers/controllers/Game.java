package edu.neumont.cryptmakers.controllers;

import edu.neumont.cryptmakers.models.*;
import edu.neumont.cryptmakers.views.GameView;


import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import static edu.neumont.cryptmakers.views.GameView.*;


public class Game {

    private int score = 0;


    private Random random = new Random();
    //private int mazeSize = random.nextInt(13) + 8;
    private static int mazeSize = 16; //TODO: Randomize a maze size within the params listed in the spec

    private Maze maze = new Maze(mazeSize, mazeSize);
    public static int turnCount = 0;
    private int turnSpeed = 3;
    private int moveCount = 1;
    private static boolean mapShown = false;
    private static boolean is64x = false;
    private boolean gameOver = false;
    public static boolean isGameRunning = false;
    private boolean hasEscaped = false;
    private boolean isValidMove = false;
    private String direction = "still";
    Player player = new Player();
    Monster monster = new Monster();
    Tile monsterTile = maze.getMaze()[monster.getVPos()][monster.getHPos()];


    private GameView view = new GameView();

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

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



    AudioTrack lostMine = new AudioTrack("music/98_Lost_Mine.wav");
    AudioTrack sleepingOgre = new AudioTrack("music/186_Haunted.wav");
    AudioTrack treasurePickup = new AudioTrack("music/treasure_found.wav");

    public void run() {
        //TODO: This will be the main controller to control the game
        isGameRunning = true;
        updateText("There is a wall");
        lostMine.play();
        sleepingOgre.play();
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

    public static int genRandNum(int min, int max) {
        int range = max - min + 1;
        return new Random().nextInt(range) + min;
    }

    private void updateDisplay() {
        GameView.getSpeedDisplay().setText("Speed: " + turnSpeed + " tiles");
        view.displayMaze(maze);
        GameView.displayText((hasEscaped ? "You've escaped!" : direction + isValidMove));
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
                if (tileType != TileEnum.WALL && tileType != TileEnum.START) {
                    maze.getMaze()[player.getVPos()][player.getHPos()].setType(TileEnum.PATH);
                    character.move(hTrans, vTrans);
                    tile.setType(TileEnum.PLAYER);
                    if(tileType == TileEnum.TREASURE){
                        ((Player) character).setTreasure(true);
                        treasurePickup.play();
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
                } else {
                    incrementTurn();
                }
                if (tileType == TileEnum.START) {
                    hasEscaped = true;
                }
            } else if (character instanceof Monster) {
                if (((Monster) character).isAwake()) {
                    getMaze().getMaze()[character.getVPos()][character.getHPos()] = monsterTile;
                    monsterTile = tile;
                    character.move(hTrans, vTrans);
                    tile.setType(TileEnum.ENEMY);
                    return true;
                }
            }
        }
        if (hasEscaped) {
            isGameRunning = false;
            updateDisplay();
            GameView.createEndWindow();
            lostMine.stop();
            sleepingOgre.stop();
        }
        return false;
    }

    public void setupKeyPressEventListener(JComponent component) {
        component.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch(keyCode) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        //Try to move up
                        isValidMove = detectValidMove(player, 0, -1);
//                        displayText("up " + isValidMove);
                        direction = "up";
                        updateDisplay();
                        break;
                    case KeyEvent.VK_DOWN:
                        //Try to move down
                        isValidMove = detectValidMove(player, 0, 1);
//                        displayText("down " + isValidMove);
                        direction = "down";
                        updateDisplay();
                        break;
                    case KeyEvent.VK_LEFT:
                        //Try to move left
                        isValidMove = detectValidMove(player, -1, 0);
//                        displayText("left " + isValidMove);
                        direction = "left";
                        updateDisplay();
                        break;
                    case KeyEvent.VK_RIGHT:
                        //Try to move right
                        isValidMove = detectValidMove(player, 1, 0);
//                        displayText("right " + isValidMove);
                        direction = "right";
                        updateDisplay();
                        break;
//                    case KeyEvent.VK_S:
//                        //Change speed
//                        if (moveCount > 1) {
//                            incrementTurn();
//                        }
//                        if (turnSpeed == 1) {
//                            turnSpeed = 3;
//                        } else {
//                            turnSpeed = 1;
//                        }
//                        updateDisplay();
//                        break;
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
        updateDisplay();
    }

}
