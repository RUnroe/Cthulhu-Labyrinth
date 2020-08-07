package edu.neumont.cryptmakers.models;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze {
    private int size;
//    private Tile[][] mazeArray = {
//            {new Tile(TileEnum.WALL), new Tile(TileEnum.WALL), new Tile(TileEnum.WALL), new Tile(TileEnum.WALL), new Tile(TileEnum.WALL)},
//            {new Tile(TileEnum.WALL), new Tile(TileEnum.PATH), new Tile(TileEnum.WALL), new Tile(TileEnum.PATH), new Tile(TileEnum.PATH)},
//            {new Tile(TileEnum.WALL), new Tile(TileEnum.PATH), new Tile(TileEnum.WALL), new Tile(TileEnum.PATH), new Tile(TileEnum.WALL)},
//            {new Tile(TileEnum.WALL), new Tile(TileEnum.PATH), new Tile(TileEnum.WALL), new Tile(TileEnum.PATH), new Tile(TileEnum.WALL)},
//            {new Tile(TileEnum.WALL), new Tile(TileEnum.PATH), new Tile(TileEnum.WALL), new Tile(TileEnum.PATH), new Tile(TileEnum.WALL)},
//            {new Tile(TileEnum.WALL), new Tile(TileEnum.PATH), new Tile(TileEnum.PATH), new Tile(TileEnum.PATH), new Tile(TileEnum.WALL)},
//            {new Tile(TileEnum.WALL), new Tile(TileEnum.WALL), new Tile(TileEnum.WALL), new Tile(TileEnum.WALL), new Tile(TileEnum.WALL)}
//};

    private Tile[][] maze;
    private int scale = 32; // size of a "square" of a maze
    private int offset = 10; // offset from edges of screen

    public Maze(int x, int y) {
        // initialize everywhere in the maze with an empty cell
        maze = new Tile[x][y];
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++)
                maze[i][j] = new Tile(i, j);

        generateMaze();
    }

    /*
     * This next function generates the maze using a depth first search algorithm
     * Definition: valid cell - a cell that is inside of the maze and has yet to be visited
     * 1) mark the current cell as visited
     * 2) create an array of all valid cells adjacent to the current cell
     * 3) pick a random cell and move to that cell
     * 4) mark that cell as being visited
     * 5) add the cell to the stack
     *
     * if there are no valid cells, get the most recently visited cell (from the stack) and backtrack to it
     */
    public void generateMaze() {
        Stack<Tile> stack = new Stack<Tile>(); // stack to keep track of backtracking

        // start in the upper right corner
        int x = length() - 1;
        int y = 0;
        Tile current = maze[x][y];
        current.mark(); // mark the current cell as visited
        stack.add(current); // add the cell to the stack
        Tile next;

        // while there are cells in the stack
        while (!stack.isEmpty()) {
            // pick a random neighboring cell
            Tile tile = randomTile(current);
            // if we picked a cell (will be null if no viable options)
            if (tile != null) {
                // get the x and y coordinates of the next cell
                int tempx = tile.getX();
                int tempy = tile.getY();
                next = maze[tempx][tempy];

                // remove the appropriate walls based on the direction we will be traveling in
                if (x < tempx) {
                    next.removeWall(Tile.Walls.LEFT);
                    current.removeWall(Tile.Walls.RIGHT);
                } else if (y < tempy) {
                    next.removeWall(Tile.Walls.TOP);
                    current.removeWall(Tile.Walls.BOTTOM);
                } else if (x > tempx) {
                    next.removeWall(Tile.Walls.RIGHT);
                    current.removeWall(Tile.Walls.LEFT);
                } else if (y > tempy) {
                    next.removeWall(Tile.Walls.BOTTOM);
                    current.removeWall(Tile.Walls.TOP);
                }

                // add the next cell to the stack
                stack.add(next);
                next.mark(); // mark the next cell as visited
                // make the next cell the current cell (effectively moving to the cell)
                x = tempx;
                y = tempy;
                current = maze[x][y];
            } else {
                // if there are no possible moves, get the next cell from the stack
                next = stack.pop();
                x = next.getX();
                y = next.getY();
                current = maze[x][y];
            }
        }
        // have the start be in the upper left
        maze[0][0].removeWall(Tile.Walls.LEFT);
        // have the end be in the lower right
        maze[length() - 1][length() - 1].removeWall(Tile.Walls.RIGHT);
    }

    // pick a random cell that neighbors the cell with the given coordinates
    private Tile randomTile(Tile t) {
        int x = t.getX();
        int y = t.getY();
        ArrayList<Tile> viableOptions = new ArrayList<Tile>();

        // determine if the cell to the right of the current cell is viable
        if (x + 1 != length() && !maze[x + 1][y].isVisited()) {
            viableOptions.add(maze[x + 1][y]);
        }

        // determine if the cell to the left of the current cell is viable
        if (x - 1 != -1 && !maze[x - 1][y].isVisited()) {
            viableOptions.add(maze[x - 1][y]);
        }

        // determine if the cell below the current cell is viable
        if (y + 1 != length() && !maze[x][y + 1].isVisited()) {
            viableOptions.add(maze[x][y + 1]);
        }

        // determine if the cell above the current cell is viable
        if (y - 1 != -1 && !maze[x][y - 1].isVisited()) {
            viableOptions.add(maze[x][y - 1]);
        }

        Tile tile = null;
        // pick a random cell from the current set of cells (null if there aren't any)
        if (!viableOptions.isEmpty()) {
            Random randomizer = new Random();
            tile = viableOptions.get(randomizer.nextInt(viableOptions.size()));
        }

        return tile;
    }

    private int length() {
        return this.maze[0].length;
    }
    public int getSize(){
        return maze.length;
    }
}
