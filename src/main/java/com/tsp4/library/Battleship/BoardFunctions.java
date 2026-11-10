package com.tsp4.library.Battleship;

public class BoardFunctions {
    protected static char[][] initBoard(int cols, int rows, char waterChar) {
        char[][] b = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                b[i][j] = waterChar;
            }
        }

        return b;
    }

    protected static void printBoard(char[][] b, int rows, int cols) {
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

        System.out.print("  ");
        for (int i = 0; i < letters.length; i++) {
            System.out.print(letters[i]);
            System.out.print(" ");
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print(i + 1);
            if ((i + 1) < 10) System.out.print(" ");

            for (int j = 0; j < cols; j++) {
                System.out.print(b[i][j]);
                System.out.print(" ");
            }

            System.out.println();
        }

        System.out.flush();
    }

}
