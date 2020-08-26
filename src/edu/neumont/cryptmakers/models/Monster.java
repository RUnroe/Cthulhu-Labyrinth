package edu.neumont.cryptmakers.models;

public class Monster extends GameCharacter{
    boolean isAwake = false;
    private TileEnum previousTile;
    private int wakeTurn = -1;
    private boolean wasVisible;
    public boolean isAwake() {
        return isAwake;
    }

    public void setAwake(boolean awake) {
        isAwake = awake;
    }

    public void wakeUp() {
        setAwake(true);
    }

    //Monster will move diagonally first. This means that it will move diagonally until one or both of the offsets == 0
    public int[] getNextMove(int playerHPos, int playerVPos, boolean allowMonsterDiagonals) {

        int[] offset = offsetFromPlayer(playerHPos, playerVPos);

                System.out.println(offset[0]+","+ offset[1]);
        if(!allowMonsterDiagonals) {
            if(offset[0] != 0 && offset[1] != 0) {
                if(Math.abs(offset[0]) > Math.abs(offset[1])) offset[0] = 0;
                else offset[1] = 0;
            }
        }
        int hTrans = directionFromPath(offset[0]);
        int vTrans = directionFromPath(offset[1]);
        return new int[] {hTrans, vTrans};
    }

    private int directionFromPath(int offset) {
        if(offset > 0) return -1;
        if(offset < 0) return 1;
        return 0;
    }
    private int[] offsetFromPlayer(int playerHPos, int playerVPos) {
        int verticalOffset = this.getVPos() - playerVPos; // Positive int means move up; Negative = down
        int horizontalOffset = this.getHPos() - playerHPos;// Positive int means move left; Negative = right
        return new int[]{horizontalOffset, verticalOffset};
    }
    public int distanceFromPlayer(int playerHPos, int playerVPos) {
        int[] offset = offsetFromPlayer(playerHPos, playerVPos);
        offset[0] = Math.abs(offset[0]);
        offset[1] = Math.abs(offset[1]);

        int maxOffset = offset[1];
        if(offset[0] > offset[1]) {
            maxOffset = offset[0];
        }
        return maxOffset;

    }

    public TileEnum getPreviousTile() {
        return this.previousTile;
    }
    public void setPreviousTile(TileEnum tile) {
        this.previousTile = tile;
    }

    public void setWasVisible(Tile tile){
        wasVisible = tile.isVisible();
    }

    public boolean getWasVisible(){
        return wasVisible;
    }

    public boolean tryToWake(int turnCount) {
        return turnCount == this.wakeTurn;
    }
    public void setWakeTurn(int wakeTurn) {
        this.wakeTurn = wakeTurn;
    }

}
