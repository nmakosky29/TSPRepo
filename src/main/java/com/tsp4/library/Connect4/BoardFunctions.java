package com.tsp4.library.Connect4;

public class BoardFunctions {

    /**
     * Clears the console.
     */
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
        char[][] b = new char[rows][cols];

        // Fills board with empty char
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                b[i][j] = emptyChar;
            }
        }

        return b;
    }

    /**
     * Prints the game board.
     */
    protected static void printBoard(char[][] b, int rows, int cols) {

        // Prints the b to terminal
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(" | " + b[i][j]);
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

    /**
     * Checks for a win in all legal directions at a given point.
     * @param b
     * @param row
     * @param col
     * @param team
     * @return the longest piece string.
     */
    protected static int checkWin(char[][] b, int row, int col, char team) {
        int vert  = 1 + countDirection(b, row, col, -1,  0, team) + countDirection(b, row, col, 1,  0, team);
        int horiz = 1 + countDirection(b, row, col,  0, -1, team) + countDirection(b, row, col, 0,  1, team);
        int diagA = 1 + countDirection(b, row, col, -1, -1, team) + countDirection(b, row, col, 1,  1, team);
        int diagB = 1 + countDirection(b, row, col,  1, -1, team) + countDirection(b, row, col, -1, 1, team);
        return Math.max(Math.max(vert, horiz), Math.max(diagA, diagB));
    }

    /**
     * Counts the piece string in a given direction.
     * @param b
     * @param row
     * @param col
     * @param dRow
     * @param dCol
     * @param team
     * @return the length of the string.
     */
    private static int countDirection(char[][] b, int row, int col, int dRow, int dCol, char team) {
        int count = 0;
        for (int i = 1; i <= 3; i++) {
            int r = row + dRow * i, c = col + dCol * i;
            if (r < 0 || r >= b.length || c < 0 || c >= b[0].length || b[r][c] != team) break;
            count++;
        }
        return count;
    }

    /**
     * Copies the board for bot manipulation
     * @param b
     * @return copied board.
     */
    protected static char[][] copyBoard(char[][] b) {
        char[][] copy = new char[b.length][];
        for (int i = 0; i < b.length; i++) copy[i] = b[i].clone();
        return copy;
    }

    /**
     * Row a piece would land in, or -1 if the column is full. Row 0 is the top.
     * */
    protected static int dropRow(char[][] b, int col, char emptyChar) {
        for (int r = b.length - 1; r >= 0; r--) {
            if (b[r][col] == emptyChar) return r;
        }
        return -1;
    }


}
