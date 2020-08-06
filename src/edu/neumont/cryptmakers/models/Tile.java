package edu.neumont.cryptmakers.models;

public class Tile {
    private boolean isVisible = false;
    private TileEnum type;


    public boolean isVisible() {
        return isVisible;
    }
    //Only need the ability to show tiles. Don't ever need to hide tiles that are already showing
    public void setVisible() {
        isVisible = true;
    }

    public TileEnum getType() {
        return type;
    }

    public void setType(TileEnum type) {
        this.type = type;
    }
}
