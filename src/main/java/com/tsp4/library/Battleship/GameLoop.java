package com.tsp4.library.Battleship;

import java.io.File;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import tools.jackson.databind.JsonNode;

import static com.tsp4.jsontools.jsonReader;

public class GameLoop {

    private static final int[] SHIPS = {5, 4, 3, 3, 2};

    public static void main(String[] args) {

        try {

            File parentDir =
                    new File(Main.class.getResource(".").toURI());

            if (!parentDir.exists()) {
                throw new Exception("parentDir not found");
            }

            File configFile =
                    new File(parentDir, "config.json");

            JsonNode config = jsonReader(configFile);

            int cols = config.get("columns").asInt();
            int rows = config.get("rows").asInt();

            char waterChar =
                    config.get("waterChar")
                            .asString()
                            .charAt(0);

            char shipChar =
                    config.get("shipChar")
                            .asString()
                            .charAt(0);

            char cursorChar =
                    config.get("cursorChar")
                            .asString()
                            .charAt(0);

            char[][] playerBoard =
                    BoardFunctions.initBoard(
                            cols,
                            rows,
                            waterChar);

            BotFunctions.aiInit(
                    cols,
                    rows,
                    waterChar,
                    shipChar);

            int cursorRow = 0;
            int cursorCol = 0;

            boolean horizontal = true;

            int currentShipIndex = 0;

            try (Terminal terminal =
                         TerminalBuilder.builder()
                                 .system(true)
                                 .build()) {

                terminal.enterRawMode();

                while (currentShipIndex < SHIPS.length) {

                    int currentShipLength =
                            SHIPS[currentShipIndex];

                    char[][] displayBoard =
                            createDisplayBoard(
                                    playerBoard,
                                    cursorRow,
                                    cursorCol,
                                    cursorChar,
                                    currentShipLength,
                                    horizontal);

                    BoardFunctions.redraw(displayBoard);

                    System.out.println();
                    System.out.println(
                            "Place ship of length "
                                    + currentShipLength);
                    System.out.println(
                            "Arrow Keys = Move");
                    System.out.println(
                            "R = Rotate");
                    System.out.println(
                            "Space = Place");
                    System.out.println(
                            "Q = Quit");

                    int ch = terminal.reader().read();

                    if (ch == 'q' || ch == 'Q') {
                        break;
                    }

                    if (ch == 'r' || ch == 'R') {
                        horizontal = !horizontal;
                        continue;
                    }

                    if (ch == ' ') {

                        if (canPlaceShip(
                                playerBoard,
                                cursorRow,
                                cursorCol,
                                currentShipLength,
                                horizontal,
                                shipChar)) {

                            placeShip(
                                    playerBoard,
                                    cursorRow,
                                    cursorCol,
                                    currentShipLength,
                                    horizontal,
                                    shipChar);

                            currentShipIndex++;
                        }

                        continue;
                    }

                    if (ch == 27) {

                        int second =
                                terminal.reader().read();

                        if (second == '[') {

                            int third =
                                    terminal.reader().read();

                            switch (third) {

                                case 'A':
                                    cursorRow =
                                            Math.max(
                                                    0,
                                                    cursorRow - 1);
                                    break;

                                case 'B':
                                    cursorRow =
                                            Math.min(
                                                    rows - 1,
                                                    cursorRow + 1);
                                    break;

                                case 'C':
                                    cursorCol =
                                            Math.min(
                                                    cols - 1,
                                                    cursorCol + 1);
                                    break;

                                case 'D':
                                    cursorCol =
                                            Math.max(
                                                    0,
                                                    cursorCol - 1);
                                    break;
                            }
                        }
                    }
                }

                BoardFunctions.redraw(playerBoard);

                if (currentShipIndex == SHIPS.length) {
                    System.out.println(
                            "All ships placed!");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static char[][] createDisplayBoard(
            char[][] board,
            int cursorRow,
            int cursorCol,
            char cursorChar,
            int shipLength,
            boolean horizontal) {

        char[][] temp =
                new char[board.length][board[0].length];

        for (int r = 0; r < board.length; r++) {

            System.arraycopy(
                    board[r],
                    0,
                    temp[r],
                    0,
                    board[r].length);
        }

        for (int i = 0; i < shipLength; i++) {

            int row =
                    horizontal
                            ? cursorRow
                            : cursorRow + i;

            int col =
                    horizontal
                            ? cursorCol + i
                            : cursorCol;

            if (row >= board.length
                    || col >= board[0].length) {

                continue;
            }

            temp[row][col] = cursorChar;
        }

        return temp;
    }

    private static boolean canPlaceShip(
            char[][] board,
            int startRow,
            int startCol,
            int shipLength,
            boolean horizontal,
            char shipChar) {

        for (int i = 0; i < shipLength; i++) {

            int row =
                    horizontal
                            ? startRow
                            : startRow + i;

            int col =
                    horizontal
                            ? startCol + i
                            : startCol;

            if (row < 0
                    || row >= board.length
                    || col < 0
                    || col >= board[0].length) {

                return false;
            }

            if (board[row][col]
                    == shipChar) {

                return false;
            }
        }

        return true;
    }

    private static void placeShip(
            char[][] board,
            int startRow,
            int startCol,
            int shipLength,
            boolean horizontal,
            char shipChar) {

        for (int i = 0; i < shipLength; i++) {

            int row =
                    horizontal
                            ? startRow
                            : startRow + i;

            int col =
                    horizontal
                            ? startCol + i
                            : startCol;

            board[row][col] = shipChar;
        }
    }
}