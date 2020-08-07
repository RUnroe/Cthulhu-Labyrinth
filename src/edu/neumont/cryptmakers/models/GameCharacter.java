package edu.neumont.cryptmakers.models;

public abstract class GameCharacter {
    private int xPos;
    private int yPos;

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public void move(int xTranslation, int yTranslation) {
        setXPos(getXPos() + xTranslation);
        setYPos(getYPos() + yTranslation);
    }

}
