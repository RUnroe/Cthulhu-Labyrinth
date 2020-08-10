package edu.neumont.cryptmakers.models;

public abstract class GameCharacter {
    private int vPos;
    private int hPos;

    public int getVPos() {
        return vPos;
    }

    public void setVPos(int xPos) {
        this.vPos = xPos;
    }

    public int getHPos() {
        return hPos;
    }

    public void setHPos(int yPos) {
        this.hPos = yPos;
    }

    public void move(int hTrans, int vTrans) {
        setHPos(getHPos() + hTrans);
        setVPos(getVPos() + vTrans);
    }

}
