package edu.neumont.cryptmakers.models;

import java.util.ArrayList;

public class Tile {

    boolean visible = true;


    // Tile object that makes it easy to store multiple pieces of information in one array


    // Stores the graphical label of the cell

    //private JLabel label;

    // Stores the identifier for the cell

    private TileEnum type;

    // Stores the x-coordinate of the cell

    private int x;

    //
    private int y;

    //
    private boolean visited;

    // Creates a new Tile and stores given information

    public Tile(TileEnum type, int x, int y)
    {
        setType(type);
        //setLabel(label);
        setX(x);
        setY(y);
    }

    // Returns the tile's neighbors in grids/maps

    public ArrayList<Tile> neighborTiles(Tile[][] maze, int xSize, int ySize)
    {
        //stores all neighbors
        ArrayList<Tile> neighbors = new ArrayList<Tile>();
        //left neighbor translation
        int left = getX()-1;
        //right neighbor translation
        int right = getX()+1;
        //up neighbor translation
        int up = getY()-1;
        //down neighbor translation
        int down = getY()+1;

        //cycles though all cells
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                //if the current maze cell matches any of the translated cells save it aproprietly
                //also ignores coordinates outside the level
                if (maze[x][y].getX() == left && maze[x][y].getY() == getY() && left >= 0 )
                    neighbors.add(maze[x][y]);
                if (maze[x][y].getX() == right && maze[x][y].getY() == getY() && right < xSize)
                    neighbors.add(maze[x][y]);
                if (maze[x][y].getX() == getX() && maze[x][y].getY() == up && up >= 0)
                    neighbors.add(maze[x][y]);
                if (maze[x][y].getX() == getX() && maze[x][y].getY() == down && down < ySize)
                    neighbors.add(maze[x][y]);
            }
        }
        //returns found neighbours
        return neighbors;
    }

    // Sets the location on the x-axis

    public void setX(int x)
    {
        this.x = x;
    }

    // Gets the location on the x-axis

    public int getX()
    {
        return x;
    }

    // Sets the location on the y-axis

    public void setY(int y)
    {
        this.y = y;
    }

    // Gets the location on the y-axis

    public int getY()
    {
        return y;
    }

    // Sets the tile's label

//        public void setLabel(JLabel label)
//        {
//            this.label = label;
//        }
//
//        // Gets the tile's label
//
//        public JLabel getLabel()
//        {
//            return label;
//        }

    // Sets the identifier for the tile

    public void setType(TileEnum type)
    {
        this.type = type;
    }

    //Returns the identifier used for the tile

    public TileEnum getType()
    {
        return type;
    }

    // Sets if the tile has been visited

    public void setVisited(boolean walkable)
    {
        this.visited = walkable;
    }

    // Returns if the tile has been visited

    public boolean getVisited()
    {
        return visited;
    }

    public boolean isVisible(){
        return visible;
    }
}
