package edu.neumont.cryptmakers.controllers;

import edu.neumont.cryptmakers.models.*;
import edu.neumont.cryptmakers.views.GameView;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import static edu.neumont.cryptmakers.views.GameView.createIntroWindow;
import static edu.neumont.cryptmakers.views.GameView.displayText;


public class Game {

    private int score = 0;


    private static int mazeSize;

    private Maze maze;
    public static int turnCount = 0;
    private int turnSpeed = 3;
    private int moveCount = 1;
    private static boolean mapShown = false;
    private static boolean is64x = false;
    private boolean gameOver = false;
    public static boolean isGameRunning = false;
    private boolean hasEscaped = false;
    private boolean isValidMove = false;
    private boolean m = true;
    private boolean allowMonsterDiagonals = false;
    Player player = new Player();
    Monster monster = new Monster();
    Tile monsterTile;


    private GameView view;

    public static int getMazeSize() {
        return mazeSize;
    }

    public static void setMazeSize(int mazeSize) {
        Game.mazeSize = mazeSize;
    }

    public static boolean isMapShown() {
        return mapShown;
    }

    public static void setMapShown(boolean mapShown) {
        Game.mapShown = mapShown;
    }

    public static boolean is64x() {
        return is64x;
    }

    public static void set64x(boolean is64x) {
        Game.is64x = is64x;
    }

    public Player getPlayer() {
        return player;
    }

    public Monster getMonster() {
        return monster;
    }

    public int getScore() {
        return this.score;
    }

    public Maze getMaze() {
        return this.maze;
    }

    public void setScore(int newScore) {
        this.score = newScore;
    }

    public void decrementScore(int value) {
        this.score -= value;
    }

    public void setMaze(Maze newMaze) {
        this.maze = newMaze;
    }


    AudioTrack currentBGM = null;
    AudioTrack lostMine = new AudioTrack("music/98_Lost_Mine.wav");
    AudioTrack sleepingOgre = new AudioTrack("music/186_Haunted.wav");
    AudioTrack treasurePickup = new AudioTrack("music/treasure_found.wav");
    AudioTrack deathAtMyHeels = new AudioTrack("music/Death-At-My-Heels.wav");
    AudioTrack retroNoHope = new AudioTrack("music/Retro_No hope.wav");
    AudioTrack monsterGrowl = new AudioTrack("music/monster-growl.wav");

    public Game() {
        setupKeyPressEventListener(GameView.getClickContainer());
    }

    public void restart() {
        currentBGM.stop();
        lostMine = new AudioTrack("music/98_Lost_Mine.wav");
        sleepingOgre = new AudioTrack("music/186_Haunted.wav");
        treasurePickup = new AudioTrack("music/treasure_found.wav");
        deathAtMyHeels = new AudioTrack("music/Death-At-My-Heels.wav");
        retroNoHope = new AudioTrack("music/Retro_No hope.wav");

        score = 0;
        turnCount = 0;
        turnSpeed = 3;
        moveCount = 1;
        mapShown = false;
        is64x = false;
        gameOver = false;
        isGameRunning = false;
        hasEscaped = false;
        isValidMove = false;
        m = true;
        player = new Player();
        monster = new Monster();
        displayText("");
        run();
    }
    public void run() {
//        createIntroWindow();

        currentBGM = lostMine;
        mazeSizePrompt();
        monsterTile = maze.getMaze()[monster.getVPos()][monster.getHPos()];
        monster.setPreviousTile(maze.getMaze()[monster.getVPos()][monster.getHPos()].getType());
        view = new GameView();
        set64x(true);
        isGameRunning = true;

        currentBGM.play();
//            sleepingOgre.play();

        for (int vPos = 0; vPos < maze.getXSize(); vPos++) {
            for (int hPos = 0; hPos < maze.getYSize(); hPos++) {
                Tile t = maze.getMaze()[vPos][hPos];

                switch (t.getType()) {
                    case PLAYER:
                        player.setHPos(hPos);
                        player.setVPos(vPos);
                    case START:
                        t.discover();
                        break;
                    case ENEMY:
                        monster.setHPos(hPos);
                        monster.setVPos(vPos);

                }
            }
        }

        System.out.println("Run method check");
        updateDisplay();


    }

    public void mazeSizePrompt() {
        boolean invalid = true; //store if user needs to be re-prompted
        int tempSize = 0;
        int min = 8, max = 18;
        while (invalid) {
            try {
                String sizeStr = JOptionPane.showInputDialog("Please enter the size of the maze between " + min + " and " + max); //open prompt for user input
                if (sizeStr != null && !sizeStr.isEmpty()) { //make sure the user entered something
                    //System.out.println("sizeStr is not empty");
                    tempSize = Integer.parseInt(sizeStr); //set the size to the user input
                    //System.out.println(tempSize);
                    if (tempSize < min || tempSize > max) { //the maze can be no larger than 16 and no smaller than 6
                        //error message pop up
                        JOptionPane.showMessageDialog(null, "Please enter the size of the maze between " + min + " and " + max, "BAD SIZE", JOptionPane.WARNING_MESSAGE);
                        invalid = true;
                    } else { //user entered a valid size
                        //System.out.println(tempSize);
                        invalid = false;
                    }
                } else {
                    invalid = false;
                    System.exit(1); //exit the program if the user presses cancel
                }
            } catch (NumberFormatException nfe) {
                //error message pop up
                JOptionPane.showMessageDialog(null, "Please enter the size of the maze between " + min + " and " + max, "BAD SIZE", JOptionPane.WARNING_MESSAGE);
            }

        } // end while

        tempSize += 2; //add 2 to compensate for the outer walls of the maze
        mazeSize = tempSize;

        maze = new Maze(mazeSize, mazeSize); //create the maze object with the user sizes
    }

    public static int genRandNum(int min, int max) {
        int range = max - min + 1;
        return new Random().nextInt(range) + min;
    }

    private void updateDisplay() {
        GameView.getSpeedDisplay().setText("Speed: " + turnSpeed + " tiles");
        GameView.getTreasureDisplay().setText(player.hasTreasure() ? "You have the treasure! Return to the start before the monster gets you!" : "");
        if (player.hasTreasure()) GameView.getTreasureDisplay().setForeground(Color.MAGENTA);

        view.displayMaze(maze);
        view.displayTurnCount(turnCount);
    }

    private void updateText(String output) {
        displayText(output);
    }


    public boolean detectValidMove(GameCharacter character, int hTrans, int vTrans) {
        int x = character.getHPos() + hTrans;
        int y = character.getVPos() + vTrans;
        boolean isInsideMaze = y >= 0 && y < getMaze().getXSize() &&
                x >= 0 && x < getMaze().getYSize();
        if (isInsideMaze) {
            Tile tile = getMaze().getMaze()[y][x];
            TileEnum tileType = tile.getType();

            if (character instanceof Player) {

                tile.discover();
                if (tileType != TileEnum.WALL) {
                    if (tileType != TileEnum.START) {
                        maze.getMaze()[player.getVPos()][player.getHPos()].setType(TileEnum.PATH);
                        character.move(hTrans, vTrans);
                        tile.setType(TileEnum.PLAYER);
                        if (tileType == TileEnum.TREASURE) {
                            player.setTreasure(true);
                            treasurePickup.play();
                            //Change speed
                            wakeMonsterOnDelay(3);
                            turnSpeed = 1;
                            updateDisplay();
                        }
                        if (moveCount >= turnSpeed) {
                            incrementTurn();
                        } else {
                            moveCount++;
                        }
                        return true;
                    } else {
                        if (monster.isAwake() && player.hasTreasure()) {
                            displayText("You win, well done!");
                            endGame("win");
                            changeBGM(retroNoHope);
                        }
                        if (monster.isAwake() && !player.hasTreasure()) {
                            displayText("It's a draw, next time get the treasure to win!");
                            endGame("escape");
                            changeBGM(retroNoHope);
                        }

                    }
                } else {
                    incrementTurn();
                }
                if (tileType == TileEnum.START) {
                    hasEscaped = true;
                }
            } else if (character instanceof Monster) {
                if (((Monster) character).isAwake()) {
                    maze.getMaze()[character.getVPos()][character.getHPos()].setType(((Monster) character).getPreviousTile());
                    if( !((Monster) character).getWasVisible()){
                        maze.getMaze()[character.getVPos()][character.getHPos()].setVisible(((Monster) character).getWasVisible());
                    }

                    //System.out.println(monster.getPreviousTile());
                    monsterTile = tile;
                    if (tile.getType() != TileEnum.ENEMY) ((Monster) character).setPreviousTile(tile.getType());
                    ((Monster) character).setWasVisible(tile);
                    character.move(hTrans, vTrans);
                    tile.setType(TileEnum.ENEMY);

                    tile.setVisible(true);

                    return true;
                }
            }
        }
        return false;
    }

    public void setupKeyPressEventListener(JComponent component) {
        component.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                updateText("");
                int keyCode = e.getKeyCode();
                boolean isValidMove = false;
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        //Try to move up
                        isValidMove = detectValidMove(player, 0, -1);
                        monsterController();
                        updateDisplay();
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        //Try to move down
                        isValidMove = detectValidMove(player, 0, 1);
                        monsterController();
                        updateDisplay();
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        //Try to move left
                        isValidMove = detectValidMove(player, -1, 0);
                        monsterController();
                        updateDisplay();
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        //Try to move right
                        isValidMove = detectValidMove(player, 1, 0);
                        monsterController();
                        updateDisplay();
                        break;
                    case KeyEvent.VK_M:
                        if (m) {
                            m = false;
                            currentBGM.pause();
                        } else {
                            m = true;
                            currentBGM.resume();
                        }


                        break;
//

                    case KeyEvent.VK_MINUS:
                        lowerVolume();
                        System.out.println("Current Volume: " + currentBGM.getVolume());
                        break;

                    case KeyEvent.VK_EQUALS:
                        raiseVolume();
                        System.out.println("Current Volume: " + currentBGM.getVolume());
                        break;

                    case KeyEvent.VK_Z:for (Tile[] t : maze.getMaze()) {
                           if (!mapShown) {
                                for (Tile tile : t) {
                                    tile.discover();
                                }
                            } else {
                                for (Tile tile : t) {
                                    tile.setVisible(false);
                                }
                            }
                        }
                        updateDisplay();
break;


                }

            }
        });
    }

    private void lowerVolume() {
        lostMine.decreaseVolume();
        monsterGrowl.decreaseVolume();
        deathAtMyHeels.decreaseVolume();
        treasurePickup.decreaseVolume();
        retroNoHope.decreaseVolume();
    }

    private void raiseVolume() {
        lostMine.increaseVolume();
        monsterGrowl.increaseVolume();
        deathAtMyHeels.increaseVolume();
        treasurePickup.increaseVolume();
        retroNoHope.increaseVolume();
    }

    private void incrementTurn() {
        turnCount++;
        moveCount = 1;
        moveMonster();
        updateDisplay();
    }


    private void monsterController() {

        switch (monster.distanceFromPlayer(player.getHPos(), player.getVPos())) {
            case 0:
                loseGame();
                break;
            case 1:
                wakeMonster();
                break;
            case 2:
                //Alert player that monster is close
                if (monster.isAwake()) displayText("The monster is near");
                else displayText("You hear the monster stirring!");
                break;
        }
    }

    private void moveMonster() {
        if (monster.tryToWake(turnCount)) wakeMonster();
        int[] translation = monster.getNextMove(player.getHPos(), player.getVPos(), allowMonsterDiagonals);
        detectValidMove(monster, translation[0], translation[1]);
    }

    private void wakeMonster() {
        if (!monster.isAwake()) {
            displayText("The monster is awake! Return to start to escape!");
            monster.wakeUp();
            monsterGrowl.play();
            changeBGM(deathAtMyHeels);
        }
    }

    private void wakeMonsterOnDelay(int turnDelay) {
        monster.setWakeTurn(turnCount + turnDelay);
    }

    private void loseGame() {
        displayText("The monster ate you! You lose!");
        endGame("lose");
        changeBGM(retroNoHope);
    }

    private void changeBGM(AudioTrack newBGM) {
        currentBGM.stop();
        currentBGM = newBGM;
        currentBGM.play();
    }

    private void endGame(String endGameCondition) {
        String imgSrc = "";
        switch (endGameCondition){
            case "lose":
                imgSrc = "images/youaredead.png";
                break;
            case "escape":
                imgSrc = "images/you-escaped.png";
                break;
            case "win":
                imgSrc = "images/winner.png";
                break;
        }
        view.createEndWindow(imgSrc);
    }
}
