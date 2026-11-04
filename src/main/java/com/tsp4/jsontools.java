package com.tsp4;

import java.io.File;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class jsontools {

    /**
     * Creates a JsonNode object to read JSON files.
     * @param configFile
     * @return JsonNode
     */
    public static JsonNode jsonReader(File configFile) {

        if (!configFile.exists()) throw new RuntimeException("config file not found");
        return new ObjectMapper().readTree(configFile);

    }
}
