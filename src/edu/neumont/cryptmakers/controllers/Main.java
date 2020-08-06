package edu.neumont.cryptmakers.controllers;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Starting up application...");
        run();
    }
    public static void run() {
        boolean keepPlaying = true;
        while(keepPlaying) {
            //TODO: Starting menu
            Game game = new Game();
            game.run();
            //TODO: prompt user if they want to play again. set returned bool to keepPlaying variable
        }

    }
}
