package com.tsp4.library.Battleship;

public class BotFunctions {

    protected static char[][] aiInit(int cols, int rows, char waterChar, char shipChar) {
        char[][] b = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                b[i][j] = waterChar;
            }
        }

        b = placeShips(b, shipChar, cols, rows);

        return b;
    }

    private static char[][] placeShips(char[][] b, char shipChar, int cols, int rows) {
        return null;
    }
}
