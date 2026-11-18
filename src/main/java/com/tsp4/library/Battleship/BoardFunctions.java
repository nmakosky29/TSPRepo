package com.tsp4.library.Battleship;

public class BoardFunctions {

	// constants to control colors
	private static final String RESET = "\u001B[0m";
	private static final String BLUE = "\u001B[34m";
	private static final String GREEN = "\u001B[32m";
	private static final String RED = "\u001B[31m";
	private static final String YELLOW = "\u001B[33m";
	private static final String CYAN = "\u001B[96m";
	private static final String BRIGHT_RED = "\u001B[91m";
	
	// allows for characters in the config to change
	private static char waterChar;
	private static char shipChar;
	private static char hitChar;
	private static char missChar;
	private static char cursorChar;
	
    /**
     * Creates the Player's board
     * @param cols
     * @param rows
     * @param waterChar
     * @return initialized board
     */
    protected static char[][] initBoard(int cols, int rows, char waterChar) {
        char[][] b = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                b[i][j] = waterChar;
            }
        }
        return b;
    }
    
    /**
     * Initializes board symbols from config.
     * @param water
     * @param ship
     * @param hit
     * @param miss
     * @param cursor
     */
    protected static void initSymbols(char water, char ship, char hit, char miss, char cursor) {
        waterChar = water;
        shipChar = ship;
        hitChar = hit;
        missChar = miss;
        cursorChar = cursor;
    }

    /**
     * Prints the board with letters and numbers
     * @param b
     * @param rows
     * @param cols
     */
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

            // vibe coded slop, slightly optimized
            for (int j = 0; j < cols; j++) {
                char c = b[i][j];
                System.out.print(colorize(c));
                System.out.print(" ");
            }
            System.out.println();
        }

        System.out.flush();
    }

    /**
     * Returns a colored version of the supplied character.
     * @param c
     * @return colored character string
     */
    private static String colorize(char c) {

        if (c == waterChar)
            return BLUE + c + RESET;
        if (c == shipChar)
            return GREEN + c + RESET;
        if (c == hitChar)
            return RED + c + RESET;
        if (c == missChar)
            return YELLOW + c + RESET;
        if (c == cursorChar)
            return CYAN + c + RESET;
        if (c == '!')
            return BRIGHT_RED + c + RESET;

        return Character.toString(c);
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
