package edu.neumont.cryptmakers.controllers;

import edu.neumont.cryptmakers.models.*;
import edu.neumont.cryptmakers.views.GameView;



public class Game {

    private int score = 0;
    private int mazeSize = 5; //TODO: Randomize a maze size within the params listed in the spec
    private Maze maze = new Maze(mazeSize, mazeSize);
    private int turnCount = 0;
    private boolean gameOver = false;

    private GameView view = new GameView();

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

        updateDisplay();
        updateText("There is a wall");


    }

    private void updateDisplay() {
        view.displayMaze(maze);
        view.displayTurnCount(turnCount);
    }
    private void updateText(String output) {
        view.displayText(output);
    }

    public boolean detectValidMove(GameCharacter character, int xTrans, int yTrans) {
        /*int x = character.getXPos() + xTrans;
        int y = character.getYPos() + yTrans;
        boolean isInsideMaze = x >= 0 && x < getMaze().getSize() &&
                y >= 0 && y < getMaze().getSize();
        if (isInsideMaze) {
            //Tile tile = getMaze().getMazeArray()[x][y];
            TileEnum tileType = tile.getType();

            if (character instanceof Player) {
                if (tileType == TileEnum.PATH || tileType == TileEnum.WALL) {
                    character.move(xTrans, yTrans);
                    tile.setType(TileEnum.PLAYER);
                    tile.setVisible();
                    return true;
                }
            } else if (character instanceof Monster) {
                if (((Monster) character).isAwake()) {
                   // getMaze().getMazeArray()[character.getXPos()][character.getYPos()] = monsterTile;
                    //monsterTile = tile;
                    character.move(xTrans, yTrans);
                    tile.setType(TileEnum.ENEMY);
                    tile.setVisible();
                    return true;
                }
            }
        }*/
        return false;
    }

}
