package edu.neumont.cryptmakers.controllers;

import edu.neumont.cryptmakers.models.Maze;

public class Game {
    private int score = 0;
    private int mazeSize; //TODO: Randomize a maze size within the params listed in the spec
    private Maze maze = new Maze(mazeSize);
    private int turnCount = 0;
    private boolean gameOver = false;

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
    }

}
