package edu.neumont.cryptmakers.controllers;

import edu.neumont.cryptmakers.views.GameView;
import edu.neumont.cryptmakers.views.MapScreen;

public class Main {
    private static Game game;
    public static void main(String[] args) {
        System.out.println("Starting up application...");
//        GameView.createIntroWindow();
        run();
//        MapScreen screen = new MapScreen();
    }
    public static void run() {

        game = new Game();
        game.run();

    }
    public static void restart() {
        game.restart();
    }

}
