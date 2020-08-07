package edu.neumont.cryptmakers.models;

public class Tile {
    private boolean isVisible = false;
    private TileEnum type;
    private int x;
    private int y;
    public enum Walls {
        LEFT, RIGHT, TOP, BOTTOM
    };

    private boolean visited;
    // booleans for if these walls exist (for drawing)
    private boolean left, right, top, bottom;

    public Tile(int x, int y) {
        this.visited = false;
        this.left = true;
        this.right = true;
        this.top = true;
        this.bottom = true;
        this.x = x;
        this.y = y;
    }

    // remove a wall from the cell
    public void removeWall(Walls w) {
        switch (w) {
            case LEFT:
                this.left = false;
                break;
            case RIGHT:
                this.right = false;
                break;
            case TOP:
                this.top = false;
                break;
            case BOTTOM:
                this.bottom = false;
                break;
        }
        visited = true;
    }

    // mark a wall as visited
    public void mark() {
        this.visited = true;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isBottom() {
        return bottom;
    }

    public boolean isTop() {
        return top;
    }

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
