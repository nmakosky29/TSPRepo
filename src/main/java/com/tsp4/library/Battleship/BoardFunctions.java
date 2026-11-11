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

    /**
     * Method from the web to clear the console.
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
     * Method to redraw board.
     * @param b
     */
    protected static void redraw(char[][] b) {
        BoardFunctions.clearConsole();
        BoardFunctions.printBoard(b, b.length, b[0].length);
    }

}
