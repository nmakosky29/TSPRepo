package com.tsp4.library.Battleship;

import java.util.Random;

public class BotFunctions {

    private static char[][] outgoingShots;
    private static char[][] incomingShots;

    private static final Random random = new Random();

    /**
     * Creates the bot's outgoing shots board and incoming shots board. They live in this class for the game.
     * Should be no need to access them for the player.
     * @param cols
     * @param rows
     * @param waterChar
     * @param shipChar
     */
    protected static void aiInit(int cols, int rows, char waterChar, char shipChar) {
        incomingShots = new char[rows][cols];
        outgoingShots = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                incomingShots[i][j] = waterChar;
                outgoingShots[i][j] = waterChar;
            }
        }

        placeShips(shipChar);
    }

    /**
     * For each ship, randomly place it horizontally or vertically
     * @param shipChar
     */
    private static void placeShips(char shipChar) {
        int[] ships = {5, 4, 3, 3, 2};

        for (int i = 0; i < ships.length; i++) {
            int r = random.nextInt(2);

            if (r == 1) placeHorizontal(ships[i], shipChar);
            else placeVertical(ships[i], shipChar);
        }
    }

    /**
     * Places ships horizontally on the board.
     * @param length
     * @param shipChar
     */
    private static void placeHorizontal(int length, char shipChar) {
        int row;
        int col;
        boolean flag;

        do {
            row = (int) (Math.random() * 100) % 10;
            col = (int) (Math.random() * 100) % 10;

            if ((col + length) > 10) {
                flag = true;
                continue;
            }

            flag = false;
            for (int i = 0; i < length; i++) {
                if (incomingShots[row][col + i] == shipChar) {
                    flag = true;
                    break;
                }
            }
        } while (flag);

        for (int i = 0; i < length; i++) {
            incomingShots[row][col + i] = shipChar;
        }
    }

    /**
     * Places ships vertically on the board.
     * @param length
     * @param shipChar
     */
    private static void placeVertical(int length, char shipChar) {
        int row;
        int col;
        boolean flag;

        do {
            row = (int) (Math.random() * 100) % 10;
            col = (int) (Math.random() * 100) % 10;

            if ((row + length) > 10) {
                flag = true;
                continue;
            }

            flag = false;
            for (int i = 0; i < length; i++) {
                if (incomingShots[row + i][col] == shipChar) {
                    flag = true;
                    break;
                }
            }
        } while (flag);

        for (int i = 0; i < length; i++) {
            incomingShots[row + i][col] = shipChar;
        }
    }

    /**
     * Prints incomingShots and outgoingShots for testing purposes.
     */
    protected static void printShipBoards() {

        for (int i = 0; i < 10; i++) {
            System.out.print(" ");
            for (int j = 0; j < 10; j++) {
                System.out.print(outgoingShots[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        System.out.println();

        for (int i = 0; i < 10; i++) {
            System.out.print(" ");
            for (int j = 0; j < 10; j++) {
                System.out.print(incomingShots[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        System.out.flush();
    }
}
