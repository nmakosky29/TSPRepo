package com.tsp4.library.Connect4;
import static com.tsp4.jsontools.jsonReader;

import tools.jackson.databind.JsonNode;

public class MainGame {

    public static void main(String[] args) {

        // Makes new object to read JSON file
        JsonNode config = jsonReader("config.json");
        String name = config.get("name").asString();

        // Checks to make sure correct file is loaded
        if (!name.equals("Connect4")) throw new IllegalArgumentException("wrong config file: name is \"" + name + "\"");

        // Loads JSON info
        int cols = config.get("columns").asInt();
        int rows = config.get("rows").asInt();
        char emptyChar = config.get("emptyChar").asString().charAt(0);
        char playerChar = config.get("playerChar").asString().charAt(0);
        char botChar = config.get("botChar").asString().charAt(0);

        // Creates a board and prints it for testing
        char[][] board = BoardFunctions.initBoard(rows, cols, emptyChar);
        BoardFunctions.printBoard(board, rows, cols);

        // Uncomment below to test bot functionality when the bot moves first. Use ONLY in your computer's terminal.
        //BotFunctions.aiPlay(board, 0, emptyChar, playerChar, botChar);
    }

}
