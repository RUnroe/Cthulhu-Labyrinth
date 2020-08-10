package edu.neumont.cryptmakers.models;

import java.util.ArrayList;
import java.util.Random;

public class Maze {

    private int xSize;
    private int ySize;

    private Tile[][] maze;
    private boolean accesible;

    public Maze(int xSize, int ySize)
    {
        maze = new Tile[xSize][ySize];
        setXSize(xSize);
        setYSize(ySize);
        generateMaze();
        System.out.println("printing maze...");
        printMaze();
    }

    public void generateMaze()
    {
        initializeMaze();
        growingTree();
        placeTreasure();
        placeMonster();
        placeStart();

        System.out.println("Maze generated");
    }

    public void placeStart()
    {
        Random random = new Random();
        //random x value
        int x = random.nextInt(getXSize());
        //random percent
        int randomNum = random.nextInt(100)+1;
        //random binary choice between top or bottom of maze (50/50)
        int y = 0;
        if (randomNum > 50)
            y = 0;
        else
            y = getYSize()-1;
        //stores the direct neighbors of the random cell
        ArrayList<Tile> neighbors;
        //stores the direct adjacent neighbors of each neighbor
        ArrayList<Tile> adjacents;
        boolean open = false;

        //Repeats unless the cell is valid, which means it is open and not a pathway
        while(maze[x][y].getType() == TileEnum.PATH||open==false)
        {
            //stores if all necessary neighbors of the randomly chosen coordinate are open
            open = false;
            //random x value
            x = random.nextInt(getXSize());
            //determines the neighbors of the random cell
            neighbors = maze[x][y].neighborTiles(getMaze(), getXSize(), getYSize());
            //cycles though the neighbors of the random cell
            for (int neighbor = 0; neighbor < neighbors.size(); neighbor++)
            {
                //if the neighbor is a pathway it is open and vice versa
                open = neighbors.get(neighbor).getType() == TileEnum.PATH;
                //if the neighbor is a pathway/is open then continue
                if (open == true)
                {
                    //determine the adjacent Cells of the open neighbor
                    adjacents = neighbors.get(neighbor).neighborTiles(getMaze(), getXSize(), getYSize());
                    //cycle though the adjacents Cells of the open neighbor
                    for (Tile adjacent : adjacents)
                    {
                        //if a adjacent of the open neighbor is also a pathway/open this is a valid location and exit the loop
                        if (adjacent.getType() == TileEnum.PATH)
                        {
                            open = true;
                            break;
                        }
                        //vice versa
                        else
                            open = false;
                    }
                }
                //if both a neighbor and a adjacent are open this is a valid location and exit the loop
                if (open == true)
                    break;
            }
        }
        //valid location has been found
        //place the player
        placePlayer(x,y);
        //mark the start of the maze on the map
        maze[x][y].setType(TileEnum.START);
    }

    public Tile getStart()
    {
        for (int y = 0; y < getYSize(); y++)
        {
            for (int x = 0; x < getXSize(); x++)
            {
                if (maze[x][y].getType().equals(TileEnum.START))
                    return maze[x][y];
            }
        }
        return null;
    }

    public void placePlayer(int x, int y)
    {
        ArrayList<Tile> neighbors = maze[x][y].neighborTiles(maze, getXSize(), getYSize());

        for (Tile neighbor : neighbors)
        {
            if (neighbor.getType() == TileEnum.PATH)
            {
                maze[neighbor.getX()][neighbor.getY()].setType(TileEnum.PLAYER);
                return;
            }
        }
    }

    public void growingTree()
    {
        //stores all the carvable tiles
        ArrayList<Tile> totalTiles = new ArrayList<Tile>();
        //loops through carvable tiles
        for (int y = 1; y < getYSize()-1; y++)
        {
            for (int x = 1; x < getXSize()-1; x++)
            {
                totalTiles.add(maze[x][y]);
            }
        }
        //stores the carved tiles
        ArrayList<Tile> tree = new ArrayList<Tile>();
        //stores the neighbors of the current tile
        ArrayList<Tile> neighbors = new ArrayList<Tile>();
        //stores the adjacents of all the neighbor tiles
        ArrayList<Tile> adjacents = new ArrayList<Tile>();
        //stores the current tile
        Tile currentTile;
        //random object
        Random random = new Random();
        //stores the x coordinate
        int x;
        //stores the y coordinate
        int y;
        //stores random index value for carvable tiles
        int randomIndex;

        //pick a random tile
        randomIndex = random.nextInt(totalTiles.size());
        //save that random tile
        currentTile = totalTiles.get(randomIndex);
        //add that random tile to a list of carved tiles
        tree.add(currentTile);
        //save the coordinate of that random tile
        x = currentTile.getX();
        y = currentTile.getY();
        //carve into cell
        maze[x][y].setType(TileEnum.PATH);
        //repeat while the list of carved tiles/tree is not empty and the currentlySelected tile still has neighbors
        while (tree.size()>0 && currentTile.neighborTiles(maze, getXSize(), getYSize()).size() > 0)
        {
            //if there is still tiles in the tree currentTile becomes the most recent carved tile
            if (tree.size()>0)
                currentTile = tree.get(tree.size()-1);
            //neighbors become the neighbors of the currentTile
            neighbors = currentTile.neighborTiles(maze, getXSize(), getYSize());
            //cycle though the neighbors
            for (int neighbor = 0; neighbor < neighbors.size(); neighbor++)
            {
                //adjacents become the adjacent tiles to the current neighbor cycle
                adjacents = neighbors.get(neighbor).neighborTiles(maze, getXSize(), getYSize());
                //counts the number of carvable tiles
                int carvable = 0;
                //loops though the adjacent tiles of the current neighbor cycle
                for (Tile adjacent : adjacents)
                {
                    //if the adjacent is a wall it is potentially carvable so add it to the count
                    if (adjacent.getType() == TileEnum.WALL )
                        carvable++;
                }
                //if there are three potentially carvable adjacents in one neighbor that is carvable, that neighbor is carvable
                if (carvable >= 3 && neighbors.get(neighbor).getType() == TileEnum.WALL)
                {
                    //current tile is equal to one of its random neighbors that is carvable
                    currentTile = randomNeighbor(neighbors);
                    //add that random neighbor/ currentCell to the list of carved tiles
                    tree.add(currentTile);
                    //save the coordinates of the random neighbor/currentTile
                    x = currentTile.getX();
                    y = currentTile.getY();
                    //carve into the random neighbor/currentTile
                    maze[x][y].setType(TileEnum.PATH);
                    //exit loop/stop checking neighbors of the old currentTile
                    break;
                }
                //if a neighbor doesnt have 3 carvable adjacents remove the currentTile
                else
                    tree.remove(currentTile);
            }
        }
    }

    public void initializeMaze()
    {
        for (int y = 0; y < getYSize(); y++)
        {
            for (int x = 0; x < getXSize(); x++)
            {
                maze[x][y] = new Tile(TileEnum.WALL, x, y);
            }
        }
    }

    public Tile randomNeighbor(ArrayList<Tile> neighbors)
    {
        Tile neighbor;
        ArrayList<Tile> adjacents;
        Random random = new Random();
        int index;
        int carvable;

        index = random.nextInt(neighbors.size());
        neighbor = neighbors.get(index);

        adjacents = neighbor.neighborTiles(maze, getXSize(), getYSize());
        carvable = 0;
        for (Tile adjacent : adjacents)
        {
            if (adjacent.getType() == TileEnum.WALL)
                carvable++;
        }

        while (neighbor.getType() != TileEnum.WALL || carvable < 3)
        {
            index = random.nextInt(neighbors.size());
            neighbor = neighbors.get(index);
            adjacents = neighbor.neighborTiles(maze, getXSize(), getYSize());
            carvable = 0;
            for (Tile adjacent : adjacents)
            {
                if (adjacent.getType() == TileEnum.WALL)
                    carvable++;
            }
        }
        return neighbor;
    }

    public void placeTreasure()
    {
        Random random = new Random();

            int x = random.nextInt(getXSize());
            int y = random.nextInt(getYSize());
            boolean accessible = false;
            ArrayList<Tile> neighbors = maze[x][y].neighborTiles(getMaze(), getXSize(), getYSize());
            while (maze[x][y].getType() != TileEnum.WALL || accessible == false)
            {
                accessible = false;
                x = random.nextInt(getXSize());
                y = random.nextInt(getYSize());
                neighbors = maze[x][y].neighborTiles(getMaze(), getXSize(), getYSize());
                for (int neighbor = 0; neighbor < neighbors.size(); neighbor++)
                {
                    if (maze[x][y].getType() == TileEnum.WALL && neighbors.get(neighbor).getType() == TileEnum.PATH)
                    {
                        accesible = true;
                        break;
                    }
                }
                if (accesible == true)
                    break;
            }
            maze[x][y].setType(TileEnum.TREASURE);
            printMaze();
    }

    public void placeMonster()
    {
        Random random = new Random();

        int x = random.nextInt(getXSize());
        int y = random.nextInt(getYSize());
        boolean accessible = false;
        ArrayList<Tile> neighbors = maze[x][y].neighborTiles(getMaze(), getXSize(), getYSize());
        while (maze[x][y].getType() != TileEnum.WALL|| accessible == false)
        {
            accessible = false;
            x = random.nextInt(getXSize());
            y = random.nextInt(getYSize());
            neighbors = maze[x][y].neighborTiles(getMaze(), getXSize(), getYSize());
            for (int neighbor = 0; neighbor < neighbors.size(); neighbor++)
            {
                if (maze[x][y].getType() == TileEnum.WALL && neighbors.get(neighbor).getType() == TileEnum.PATH)
                {
                    accesible = true;
                    break;
                }
            }
            if (accesible == true)
                break;
        }
        maze[x][y].setType(TileEnum.ENEMY);
        printMaze();
    }

    public void printMaze()
    {
        //loops though all y-values
        for (int y = 0; y < getYSize(); y++)
        {
            //loops though all x-values
            for (int x = 0; x < getXSize(); x++)
            {
                //not last id in row
                if (x < getXSize()-1) {
                    if (maze[x][y].getType() == TileEnum.WALL) {
                        System.out.print("*");
                    } else if(maze[x][y].getType() == TileEnum.PATH){
                        System.out.print(".");
                    } else if(maze[x][y].getType() == TileEnum.PLAYER){
                        System.out.print("P");
                    } else if(maze[x][y].getType() == TileEnum.TREASURE){
                        System.out.print("T");
                    } else if(maze[x][y].getType() == TileEnum.ENEMY){
                        System.out.print("M");
                    }else{
                        System.out.print("S");
                    }
                    //last id in row
                }else
                    System.out.println("*");

            }
        }
    }

    public Tile[][] getMaze()
    {
        return maze;
    }

    //Sets the size of the maze on the y-axis

    public void setYSize(int size)
    {
        ySize = size;
    }

    // Returns the size of the maze on the y-axis

    public int getYSize()
    {
        return ySize;
    }

    // Sets the size of the maze on the x-axis

    public void setXSize(int size)
    {
        xSize = size;
    }

    // Returns the size of the maze on the x-axis

    public int getXSize()
    {
        return xSize;
    }
}

