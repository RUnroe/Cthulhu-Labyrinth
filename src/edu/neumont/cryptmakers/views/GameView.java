package edu.neumont.cryptmakers.views;

import edu.neumont.cryptmakers.models.Maze;
import edu.neumont.cryptmakers.models.Tile;
import edu.neumont.cryptmakers.models.TileEnum;

public class GameView {
    //Stubbed out from UML. Feel free to add any more methods that you need
    public void displayMaze(Maze m) {
        System.out.println();
        for (Tile[] tiles : m.getMazeArray()) {
            for (Tile t : tiles) {
                if (t.isVisible()) {
                    if (t.getType() == TileEnum.WALL) {
                        System.out.print("█████");
                    }else if(t.getType() == TileEnum.PLAYER) {
                        System.out.print("  P  ");
                    }else if(t.getType() == TileEnum.TREASURE) {
                        System.out.print("  T  ");
                    }else if(t.getType() == TileEnum.ENEMY) {
                        System.out.print("  E  ");
                    }else if(t.getType() == TileEnum.PATH) {
                        System.out.print("  ░  ");
                    }
                } else{
                    System.out.println("   ");
                }

            }
            System.out.println();
        }
    }
    public void displayScore() {

    }
    public void displayMenu(String[] options) {

    }
}
