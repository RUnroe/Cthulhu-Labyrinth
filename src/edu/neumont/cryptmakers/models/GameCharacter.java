package edu.neumont.cryptmakers.models;

public abstract class GameCharacter {
    private int vTrans;
    private int hTrans;

    public int getVPos() {
        return vTrans;
    }

    public void setVPos(int xPos) {
        this.vTrans = xPos;
    }

    public int getHPos() {
        return hTrans;
    }

    public void setHPos(int yPos) {
        this.hTrans = yPos;
    }

    public void move(int hTrans, int vTrans) {
        setHPos(getHPos() + hTrans);
        setVPos(getVPos() + vTrans);
    }

}
