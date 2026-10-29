package com.tsp4.library.Connect4;

public class BoardFunctions {

    protected static void clearConsole() {
        try {
            // 1. Attempt ANSI escape codes
            System.out.print("\033[H\033[2J");
            System.out.flush();

            // 2. Attempt OS-specific clear command
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO()
                        .start()
                        .waitFor();
            } else {
                new ProcessBuilder("clear")
                        .inheritIO()
                        .start()
                        .waitFor();
            }

        } catch (Exception e) {
            // 3. Fallback: print blank lines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    /**
     * Creates a game board filled with the empty character.
     * @return game board filled with the empty character.
     */
    protected static char[][] initBoard(int rows, int cols, char emptyChar) {
        char[][] board = new char[rows][cols];

        // Fills board with empty char
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = emptyChar;
            }
        }

        return board;
    }

    /**
     * Prints the game board.
     */
    protected static void printBoard(char[][] board, int rows, int cols) {

        // Prints the board to terminal
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(" | " + board[i][j]);
            }

            System.out.print(" |");
            System.out.print("\n");
        }

        // Prints the bottom of the board
        printBoardBottom(cols);
    }

    /**
     * Prints the bottom of the board.
     */
    private static void printBoardBottom(int cols) {

        // This section prints dashes in-between the pieces and col nums
        System.out.print(" ");

        for (int j = 0; j < cols; j++) {
            System.out.print("----");
        }

        System.out.print("-");
        System.out.print("\n");

        // This section prints the col nums
        for (int j = 0; j < cols; j++) {
            System.out.print(" | " + (j + 1));
        }

        System.out.print(" |");

        System.out.flush();
    }

    protected static int checkWin(char[][] b, int row, int col, char team) {
        int vert  = 1 + countDirection(b, row, col, -1,  0, team) + countDirection(b, row, col, 1,  0, team);
        int horiz = 1 + countDirection(b, row, col,  0, -1, team) + countDirection(b, row, col, 0,  1, team);
        int diagA = 1 + countDirection(b, row, col, -1, -1, team) + countDirection(b, row, col, 1,  1, team);
        int diagB = 1 + countDirection(b, row, col,  1, -1, team) + countDirection(b, row, col, -1, 1, team);
        return Math.max(Math.max(vert, horiz), Math.max(diagA, diagB));
    }

    private static int countDirection(char[][] b, int row, int col, int dRow, int dCol, char team) {
        int count = 0;
        for (int i = 1; i <= 3; i++) {
            int r = row + dRow * i, c = col + dCol * i;
            if (r < 0 || r >= b.length || c < 0 || c >= b[0].length || b[r][c] != team) break;
            count++;
        }
        return count;
    }


}
