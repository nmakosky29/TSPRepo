package com.tsp4;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;
public class Launcher {
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main (String [] args) throws FileNotFoundException {
        try (Scanner sc = new Scanner(System.in)) {
            String input;
            ArrayList<File> library = (ArrayList<File>) ReadLibrary("library");
            clearConsole();
            
            // Greeting Dialogue
            System.out.println("""
                                       ------------------- SIMPLE GAMES LAUNCHER -------------------
                                       \t1. View Library
                                       \t2. View Settings""");
            
            // If used again, validInput must be reset to false
            boolean validInput = false;
            char option = 0;
            while (!validInput) {
                
                input = sc.nextLine();
                
                
                option = input.charAt(0);
                
                if (option == '1' || option == '2')
                    validInput = true;
                else
                    System.out.println("Invalid Input\nType 1. or 2.");
            }
            
            clearConsole();
            
            if (option == '1') {
                System.out.println("------------------- LIBRARY -------------------");
                
                if (library.isEmpty()) {
                    System.out.println("Library is empty!");
                }
                int i = 1;
                for (File f : library) {
                    System.out.printf("\t%d. %s", i, f.getName());
                    i++;
                }
            }
            if (option == '2') {
                validInput = false;
                while (!validInput) {
                    System.out.println("------------------- SETTINGS -------------------\n");
                    
                    if (library.isEmpty()) {
                        System.out.println("Library is empty!\n");
                        System.out.println("Press enter to return.");
                        sc.nextLine();
                        validInput = true;
                    } else {
                        System.out.println("Select a game to configure:");

                        int i = 1;
                    for (File f : library) {
                        System.out.printf("\t%d. %s", i, f.getName());
                        i++;
                    }
                    
                    // User Inputs which game to select
                    input = sc.nextLine();
                    option = input.charAt(0);
                    
                    if (library.isEmpty()) {

                    } else if (option <= library.size())
                        validInput = true;
                    }
                }
            }
        }
    }

    // TODO: Finish testing/debugging this method
    /**
     * Clears the current console. WIP
     * Rev 2: used the one from BoardFunctions that's from the web
     */
    public static void clearConsole() {
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
     * Reads the library folder at directory targetDir.
     * @param targetDir the library directory
     * @return List of game folders
     */
    public static List<File> ReadLibrary(String targetDir) {
        ArrayList<File> gameFiles;
        File libraryDir = new File(targetDir);

        // Check if targetDir exists, throw error otherwise.
        if (!libraryDir.exists()) {
            System.out.println("Invalid library path, checking Launcher directory for library.");

            // Get the directory where this class is located
            File parentDir = new File(
                Launcher.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath()
            ).getParentFile();

            libraryDir = new File(parentDir, "library");

            // If there is no library in the directory where this class is located,
            // create a new library folder.
            if (!libraryDir.exists()) {
                libraryDir.mkdir();
            }
        }

        gameFiles = new ArrayList<>(Arrays.asList(libraryDir.listFiles()));

        return gameFiles;
    }
    
    // TODO: add ability to write to settings
    /**
     * Reads settings.txt and stores settings as variables, or creates the file if one does not exist.
     * 
     * 
     */
    public static Map<String,String> ReadConfig() {
        String settingsFile = "config.json";
        File file = new File(settingsFile);
        ObjectMapper mapper = new ObjectMapper();        
        ObjectNode oNode;
        HashMap<String,String> returnMap = new HashMap<>();
        
        // default settings
        int aiDifficulty = 2; // 1 = random, 2 = normal, 3 = hard
        int turnOrder = 1; // 1 = player first, 2 = AI first
        int boardSize = 2; // 1 = small, 2 = normal, 3 = large
        
        try {
        	// if file doesn't exist, creates one with default settings
        	if (file.createNewFile()) {
        		System.out.println("Created new config.json file!");
                oNode = (ObjectNode) mapper.readTree("config.json");
                oNode.put("aiDifficulty",aiDifficulty);
                oNode.put("turnOrder",turnOrder);
                oNode.put("boardSize",boardSize);
        	} else {
                oNode = (ObjectNode) mapper.readTree("config.json");
            }
            
            Set<Entry<String,JsonNode>> props = oNode.properties();
            for(Entry<String,JsonNode> e : props) {
                returnMap.put(e.getKey(), e.getValue().asString());
            }

        } catch (IOException | JacksonException e) {
            e.printStackTrace();
        }

        return returnMap;
    }
    /**
     * Writes to the settings file.
     * @param updatedSettings the list of settings that will be written, in order of the file
     */
    public static void WriteSettings(int[] updatedSettings) {
        String settingsFile = "settings.txt";
        File file = new File(settingsFile);

        try {
            // if file doesn't exist, create it with default values
            if (!file.exists()) {
                ReadConfig();
            }

            // prevents out of range settings
            int aiDifficulty = updatedSettings[0];
            if (aiDifficulty < 1) aiDifficulty = 1;
            if (aiDifficulty > 3) aiDifficulty = 3;

            int turnOrder = updatedSettings[1];
            if (turnOrder < 1) turnOrder = 1;
            if (turnOrder > 2) turnOrder = 2;

            int boardSize = updatedSettings[2];
            if (boardSize < 1) boardSize = 1;
            if (boardSize > 3) boardSize = 3;

            // writes new settings into file
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("aiDifficulty=" + aiDifficulty);
                writer.println("turnOrder=" + turnOrder);
                writer.println("boardSize=" + boardSize);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}