package edu.neumont.cryptmakers.controllers;

public class Main {
    private static Game game;
    public static void main(String[] args) {
//        System.out.println("Starting up application...");
        run();
    }
    public static void run() {

        game = new Game();
        game.run();

    }
    public static void restart() {
        game.restart();
    }

}
