package edu.neumont.cryptmakers.models;

public class Maze {
    private int size;
    private Tile[][] mazeArray;

    public Maze(int size) {
        this.size = size;
        mazeArray = new Tile[this.size][this.size];
        generate();
    }

    private Maze generate() {
        //TODO implement
        return null;
    }
}
