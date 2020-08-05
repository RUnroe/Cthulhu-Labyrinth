package edu.neumont.cryptmakers.models;

public class Player extends Character {
    private boolean hasTreasure = false;

    public boolean getHasTreasure() {
        return this.hasTreasure;
    }

    public void collectTreasure() {
        this.hasTreasure = true;
    }

    public int checkDistanceToMonster() {
        //TODO
        return 0;
    }
}
