package com.tsp4.library.Connect4;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class MainGame {

    public static void main(String[] args) {

        // Inits an empty board and prints it for testing purposes
        int cols = 7;
        int rows = 6;
        String emptyChar = ".";

        String[][] board = BoardFunctions.initBoard(cols, rows, emptyChar);
        BoardFunctions.printBoard(board, 7, 6);


    }
    protected static JsonNode jsonReader(String configFileName) {
        try (InputStream in = MainGame.class.getResourceAsStream(configFileName)) {
            if (in == null) {
                throw new RuntimeException("config file not found: " + configFileName);
            }
            return new ObjectMapper().readTree(in);
        } catch (IOException e) {
            throw new RuntimeException("could not read " + configFileName, e);
        }
    }

}
