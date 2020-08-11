package edu.neumont.cryptmakers.controllers;

import edu.neumont.cryptmakers.models.*;
import edu.neumont.cryptmakers.views.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import static edu.neumont.cryptmakers.views.GameView.displayText;
import static edu.neumont.cryptmakers.views.GameView.getFrame;


public class Game {

    private int score = 0;
    private int mazeSize = 16; //TODO: Randomize a maze size within the params listed in the spec
    private Maze maze = new Maze(mazeSize, mazeSize);
    private int turnCount = 0;
    private int turnSpeed = 1;
    private int moveCount = 1;
    private boolean mapShown = false;
    private boolean gameOver = false;
    public static boolean is64x = false;
    Player player = new Player();
    Monster monster = new Monster();
    Tile monsterTile = maze.getMaze()[monster.getVPos()][monster.getHPos()];

    private GameView view = new GameView();

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

        updateText("There is a wall");
        for (int vPos = 0; vPos < maze.getXSize(); vPos++)
        {
            for (int hPos = 0; hPos < maze.getYSize(); hPos++)
            {
                Tile t = maze.getMaze()[vPos][hPos];
                if (t.getType() == TileEnum.PLAYER) {
                    player.setHPos(hPos);
                    player.setVPos(vPos);
                    t.discover();
                }
            }
        }
        updateDisplay();
        setupKeyPressEventListener(GameView.getClickContainer());
        setupKeyPressEventListener(GameView.getTextDisplay());
        setupKeyPressEventListener(GameView.getMazeDisplay());
        setupKeyPressEventListener(GameView.getScoreDisplay());


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
                if (moveCount == turnSpeed) {
                    moveCount = 1;
                    turnCount++;
                } else {
                    moveCount++;
                }
                tile.discover();
                if (tileType == TileEnum.PATH) {
                    maze.getMaze()[player.getVPos()][player.getHPos()].setType(TileEnum.PATH);
                    character.move(hTrans, vTrans);
                    tile.setType(TileEnum.PLAYER);
                    return true;
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
                        displayText("up " + isValidMove);
                        updateDisplay();
                        break;
                    case KeyEvent.VK_DOWN:
                        //Try to move down
                        isValidMove = detectValidMove(player, 0, 1);
                        displayText("down " + isValidMove);
                        updateDisplay();
                        break;
                    case KeyEvent.VK_LEFT:
                        //Try to move left
                        isValidMove = detectValidMove(player, -1, 0);
                        displayText("left " + isValidMove);
                        updateDisplay();
                        break;
                    case KeyEvent.VK_RIGHT:
                        //Try to move right
                        isValidMove = detectValidMove(player, 1, 0);
                        displayText("right " + isValidMove);
                        updateDisplay();
                        break;
                    case KeyEvent.VK_S:
                        //Change speed
                        if (moveCount > 1) {
                            turnCount++;
                            moveCount = 1;
                            updateDisplay();
                        }
                        if (turnSpeed == 1) {
                            turnSpeed = 3;
                        } else {
                            turnSpeed = 1;
                        }
                        updateDisplay();
                        break;
                    case KeyEvent.VK_M:
                        for (Tile[] t : maze.getMaze()) {
                            if (!mapShown) {
                                for (Tile tile : t) {
                                    tile.discover();
                                }
                            } else {
                                for (Tile tile : t) {
                                    tile.setVisible(false);
                                }
                            }
                        }
                            updateDisplay();
                            mapShown = !mapShown;
                            break;
                    case KeyEvent.VK_R:
                        is64x = !is64x;
                        updateDisplay();
                }
            }
        });
    }

}
