package edu.neumont.cryptmakers.models;

public class Player extends GameCharacter {
    private boolean hasTreasure = false;



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
}
