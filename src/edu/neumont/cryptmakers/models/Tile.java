package edu.neumont.cryptmakers.models;

enum tileEnum {
    WALL,
    PATH,
    PLAYER,
    ENEMY,
    TREASURE
}
public class Tile {
    private boolean isVisible = false;
    private tileEnum type;


    public boolean isVisible() {
        return isVisible;
    }
    //Only need the ability to show tiles. Don't ever need to hide tiles that are already showing
    public void setVisible() {
        isVisible = true;
    }

    public tileEnum getType() {
        return type;
    }

    public void setType(tileEnum type) {
        this.type = type;
    }
}
