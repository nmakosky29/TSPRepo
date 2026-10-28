package com.tsp4.library.Connect4;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class MainGame {

    protected static String name;
    protected static int cols;
    protected static int rows;
    protected static String emptyChar;
    protected static String playerChar;
    protected static String botChar;
    protected static String[][] board;

    public static void main(String[] args) {

        // Makes new object to read JSON file
        JsonNode config = jsonReader("config.json");
        String name = config.get("name").asString();

        // Checks to make sure correct file is loaded
        if (!name.equals("Connect4")) throw new IllegalArgumentException("wrong config file: name is \"" + name + "\"");

        // Loads JSON info
        cols = config.get("columns").asInt();
        rows = config.get("rows").asInt();
        emptyChar = config.get("emptyChar").asString();
        playerChar = config.get("playerChar").asString();
        botChar = config.get("botChar").asString();

        // Creates a board and prints it for testing
        BoardFunctions.initBoard();
        BoardFunctions.printBoard();


    }

    /**
     * Creates a JsonNode object to read JSON files.
     * @param configFileName
     * @return JsonNode
     */
    protected static JsonNode jsonReader(String configFileName) {

        // Tries to find the config file in the game folder
        try (InputStream in = MainGame.class.getResourceAsStream(configFileName)) {
            if (in == null) {
                throw new RuntimeException("config file not found: " + configFileName);
            }
            // Returns the JsonNode if success
            return new ObjectMapper().readTree(in);
        } catch (IOException e) {
            throw new RuntimeException("could not read " + configFileName, e);
        }
    }

}
