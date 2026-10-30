package com.tsp4;

import java.io.IOException;
import java.io.InputStream;

import com.tsp4.library.Connect4.MainGame;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class jsontools {

    /**
     * Creates a JsonNode object to read JSON files.
     * @param configFileName
     * @return JsonNode
     */
    public static JsonNode jsonReader(String configFileName) {

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
