package com.tsp4.library.Connect4;

import java.util.Scanner;

public class GameLoop {

    protected static void start(
            char[][] board,
            int rows,
            int cols,
            char emptyChar,
            char playerChar,
            char botChar,
            int maxDepth
    ) {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            BoardFunctions.clearConsole();
            BoardFunctions.printBoard(board, rows, cols);

            //player turn
            System.out.print("\nChoose a column (1-" + cols + "): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Enter a number.");
                scanner.next();
                continue;
            }

            int selection = scanner.nextInt() - 1;

            if (selection < 0 || selection >= cols) {
                System.out.println("Column out of range.");
                continue;
            }

            int landingRow = BoardFunctions.dropRow(board, selection, emptyChar);
            if (landingRow == -1) {
                System.out.println("Column is full.");
                continue;
            }

            board[landingRow][selection] = playerChar;

            // checks for a win
            if (BoardFunctions.checkWin(board, landingRow, selection, playerChar) >= 4) {
                BoardFunctions.clearConsole();
                BoardFunctions.printBoard(board, rows, cols);
                System.out.println("\nPlayer win");
                break;
            }

            // bot turn
            boolean botWon = BotFunctions.aiPlay(board, maxDepth, emptyChar, playerChar, botChar);

            if (botWon) {
                BoardFunctions.clearConsole();
                BoardFunctions.printBoard(board, rows, cols);
                System.out.println("\nBot win");
                break;
            }

            // checks for draw
            boolean full = true;
            for (int c = 0; c < cols; c++) {
                if (board[0][c] == emptyChar) {
                    full = false;
                    break;
                }
            }

            if (full) {
                BoardFunctions.clearConsole();
                BoardFunctions.printBoard(board, rows, cols);
                System.out.println("\nTie");
                break;
            }
        }

        scanner.close();
    }
}
