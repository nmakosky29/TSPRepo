package com.tsp4.library.Connect4;
import static com.tsp4.jsontools.jsonReader;

import com.tsp4.Launcher;
import tools.jackson.databind.JsonNode;

import java.io.File;

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
        if (!name.equals("Connect4")) throw new IllegalArgumentException("wrong config file: name is \"" + name + "\"");

        // Loads JSON info
        int cols = config.get("columns").asInt();
        int rows = config.get("rows").asInt();
        char emptyChar = config.get("emptyChar").asString().charAt(0);
        char playerChar = config.get("playerChar").asString().charAt(0);
        char botChar = config.get("botChar").asString().charAt(0);

        char[][] board = BoardFunctions.initBoard(rows, cols, emptyChar);

        GameLoop.start(board, rows, cols, emptyChar, playerChar, botChar, 4);

        // Uncomment below to test bot functionality when the bot moves first. Use ONLY in your computer's terminal.
        BotFunctions.aiPlay(board, 0, emptyChar, playerChar, botChar);
    }

}
