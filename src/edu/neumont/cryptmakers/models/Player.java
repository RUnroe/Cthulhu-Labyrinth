package edu.neumont.cryptmakers.models;

import edu.neumont.cryptmakers.controllers.Game;

public class Player extends Character {
    private int xPos = 0, yPos = 0;
    private boolean hasTreasure = false;

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public boolean hasTreasure() {
        return hasTreasure;
    }

    public void setTreasure(boolean hasTreasure) {
        this.hasTreasure = hasTreasure;
    }

    public void collectTreasure() {
        setTreasure(true);
    }

    public int checkDistanceToMonster() {
        //TODO
        return 0;
    }

//    @Override
//    public void move(Maze curMaze, int xTrans, int yTrans) {
//
//    }
}
