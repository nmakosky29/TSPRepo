package com.tsp4.library.Battleship;

import java.io.File;

import static com.tsp4.jsontools.jsonReader;

import tools.jackson.databind.JsonNode;

public class Main {
    public static void main(String[] args) throws Exception {
        // Gets parent directory and config file for game
        File parentDir = new File(Main.class.getResource(".").toURI());
        if (!parentDir.exists()) throw new Exception("parentDir not found");
        File configFile = new File(parentDir, "config.json");

        // Makes new object to read JSON file
        JsonNode config = jsonReader(configFile);
        String name = config.get("name").asString();

        // Checks to make sure correct file is loaded
        if (!name.equals("BattleShip")) {
            throw new IllegalArgumentException("wrong config file: name is \"" + name + "\"");
        }

        int cols = config.get("columns").asInt();
        int rows = config.get("rows").asInt();
        char waterChar = config.get("waterChar").asString().charAt(0);
        char shipChar = config.get("shipChar").asString().charAt(0);
        char hitChar = config.get("hitChar").asString().charAt(0);
        char missChar = config.get("missChar").asString().charAt(0);

        char[][] board = BoardFunctions.initBoard(cols, rows, waterChar);

        BoardFunctions.printBoard(board, rows, cols);

        /* aiInit() and printShipBoards() are for bot testing */
        BotFunctions.aiInit(cols, rows, waterChar, shipChar);
        BotFunctions.printShipBoards();
    }
}
