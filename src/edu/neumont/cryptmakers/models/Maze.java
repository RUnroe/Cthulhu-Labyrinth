package edu.neumont.cryptmakers.models;

public class Maze {
    private int size;
    private Tile[][] mazeArray;

    public Maze(int size) {
        this.size = size;
        mazeArray = new Tile[this.size][this.size];
        generate();
    }

    public Tile[][] getMazeArray() {
        return mazeArray;
    }

    private Maze generate() {
        //TODO implement
        return null;
    }
}
