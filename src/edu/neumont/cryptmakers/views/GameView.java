package edu.neumont.cryptmakers.views;

import edu.neumont.cryptmakers.models.Maze;
import edu.neumont.cryptmakers.models.Tile;
import edu.neumont.cryptmakers.models.TileEnum;

import java.io.BufferedReader;
import java.io.IOException;

public class GameView {
    private static BufferedReader buffy;

    //Stubbed out from UML. Feel free to add any more methods that you need
    public void displayMaze(Maze m) {
        System.out.println();
        for (Tile[] tiles : m.getMazeArray()) {
            for (Tile t : tiles) {
                if (t.isVisible()) {
                    if (t.getType() == TileEnum.WALL) {
                        System.out.print("█████");
                    } else if (t.getType() == TileEnum.PLAYER) {
                        System.out.print("  P  ");
                    } else if (t.getType() == TileEnum.TREASURE) {
                        System.out.print("  T  ");
                    } else if (t.getType() == TileEnum.ENEMY) {
                        System.out.print("  E  ");
                    } else if (t.getType() == TileEnum.PATH) {
                        System.out.print("  ░  ");
                    }
                } else {
                    System.out.println("   ");
                }

            }
            System.out.println();
        }
    }

    public void displayScore() {

    }

    public static int promptForInt(String prompt, int min, int max) {
        if (max < min) {
            throw new IllegalArgumentException("The min can't be greater than the max");
        } else {
            int userNum = 0;
            boolean isInvalid = true;

            do {
                String input = promptForString(prompt);

                try {
                    userNum = Integer.parseInt(input);
                    isInvalid = userNum < min || userNum > max;
                } catch (NumberFormatException var7) {
                }

                if (isInvalid) {
                    System.out.println("You must enter a number between " + min + " and " + max + ". Please, try again.");
                }
            } while (isInvalid);

            return userNum;
        }
    }

    public static String promptForString(String prompt) {
        if (prompt != null && !prompt.trim().isEmpty()) {
            String input = null;
            boolean isInvalid = true;

            do {
                System.out.print(prompt);

                try {
                    input = buffy.readLine();
                    isInvalid = input == null || input.trim().isEmpty();
                    if (isInvalid) {
                        System.out.println("Your input cannot be null, empty, or just white space. Please, try again.");
                    }
                } catch (IOException var4) {
                    System.out.println("There was a technical issue. Please, try again.");
                }
            } while (isInvalid);

            return input;
        } else {
            throw new IllegalArgumentException("The prompt can't be null, empty, or whitespace-only.");
        }
    }

    public int displayMenu(String[] options, boolean withQuit) {
        if (options == null) {
            options = new String[0];
        }

        if (options.length == 0 && !withQuit) {
            throw new IllegalArgumentException("There must be at least one option to select.");
        } else {
            StringBuilder sb = new StringBuilder("Please Choose one of the Following: \n\n");

            for (int i = 0; i < options.length; ++i) {
                sb.append(i + 1).append(") ").append(options[i]).append("\n");
            }

            if (options.length > 0) {
                sb.append("\n");
            }

            if (withQuit) {
                sb.append("0) Quit\n\n");
            }

            sb.append("Enter the number of your selection: ");
            String menuString = sb.toString();
            int min = withQuit ? 0 : 1;
            int max = options.length;
            return promptForInt(menuString, min, max);
        }
    }
}
