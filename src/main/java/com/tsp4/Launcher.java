package com.tsp4;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;
public class Launcher {
    static final ObjectMapper mapper = new JsonMapper();
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main (String [] args) throws URISyntaxException, InterruptedException, IOException {
        try (Scanner sc = new Scanner(System.in)) {
            String input;
            ArrayList<File> library = (ArrayList<File>) ReadLibrary();
            boolean finished = false;
            
            while (!finished) {
            clearConsole();
            
            // Greeting Dialogue
            System.out.println("""
                            ------------------- SIMPLE GAMES LAUNCHER -------------------
                            \t1. View Library
                            \t2. View Settings
                            \t3. Exit""");

            String option = "";
            boolean validInput = false;
            while (!validInput) {
                option = sc.nextLine().trim();

                if (option.equals("1") || option.equals("2") || option.equals("3"))
                    validInput = true;
                //else
                //    System.out.println("Invalid Input\nType 1, 2, or 3.");
            }
            
            clearConsole();
            
            switch (option) {
                // Case "1": User selects 1. Library
                case "1" -> {
                    System.out.println("------------------- LIBRARY -------------------");
                    if (library.isEmpty()) {
                        System.out.println("Library is empty!");
                    }       int i = 1;
                    for (File f : library) {
                        System.out.printf("\t%d. %s\n", i, f.getName());
                        i++;
                    }       // User selects game
                    validInput = false;
                    while (!validInput) {
                        option = sc.nextLine().trim();
                        try {
                            int choice = Integer.parseInt(option);
                            
                            // If the user input is an int between 1 and the number of games, continue to game, otherwise loop
                            if (choice >= 1 && choice <= library.size()) {
                                validInput = true;
                                File gameDir = library.get(choice - 1);
                                
                                // If the main class of the game does not exist, prompt user to return to menu
                                if (!new File(gameDir, "Main.class").exists()) {
                                    System.out.println("No Main.java found in " + gameDir.getAbsolutePath());
                                    
                                    // Otherwise, launch game using ProcessBuilder
                                } else {
                                    try {
                                        String mainClass = "com.tsp4.library." + gameDir.getName() + ".Main";
                                        int exitCode = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), mainClass)
                                                .inheritIO()
                                                .start()
                                                .waitFor();
                                        System.out.println("\nGame exited with code " + exitCode);
                                    } catch (IOException | InterruptedException e) {
                                        System.out.println("Could not launch game: " + e);
                                    }
                                }
                                System.out.println("Press enter to return to the menu.");
                                sc.nextLine();
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("Invalid Input");
                        }
                    }
                }

                // Case "2": User selects 2. Settings
                case "2" -> {
                    validInput = false;
                    while (!validInput) {
                        clearConsole();
                        System.out.println("------------------- SETTINGS -------------------");
                        
                        if (library.isEmpty()) {
                            System.out.println("Library is empty!\n");
                            System.out.println("Press enter to return.");
                            sc.nextLine();
                            validInput = true;
                        } else {
                            System.out.println("Select a game to configure:");
                            
                            int i = 1;
                            for (File f : library) {
                                System.out.printf("\t%d. %s\n", i, f.getName());
                                i++;
                            }
                            
                            input = sc.nextLine().trim();
                            try {
                                int choice = Integer.parseInt(input);
                                if (choice >= 1 && choice <= library.size()) {
                                    validInput = true;
                                    gameConfig(new File(library.get(choice - 1), "config.json"), sc);
                                } else {
                                    System.out.println("Invalid Input");
                                }
                            } catch (NumberFormatException nfe) {
                                System.out.println("Invalid Input");
                            }
                            
                            
                        }
                    }
                }
                default -> finished = true;
            }
            }
        }
    }

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

        } catch (IOException | InterruptedException e) {
            // 3. Fallback: print blank lines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    /**
     * Reads the library folder at directory targetDir.
     * @return List of game folders
     */
    public static List<File> ReadLibrary() throws FileNotFoundException, URISyntaxException {
        ArrayList<File> gameFiles;

        File parentDir = new File(Launcher.class.getResource(".").toURI());
        if (!parentDir.exists()) throw new FileNotFoundException("parentDir not found");
        File libraryDir = new File(parentDir, "library");

        if (!libraryDir.exists()) {
            libraryDir.mkdir();
        }

        gameFiles = new ArrayList<>(Arrays.asList(libraryDir.listFiles()));

        return gameFiles;
    }

    /**
     * User interface for editing a game's config.json file.
     * @param cFile config.json file for game
     * @param in    Scanner object for standard input
     */
    private static void gameConfig(File cFile, Scanner in) {
    // Until return
    while (true) {
        boolean validInput = false;

        ObjectNode config = (ObjectNode) mapper.readTree(cFile);
        String name = config.get("name").asString();
        ArrayList<Map.Entry<String, JsonNode>> indeces = new ArrayList<>(config.properties().size());


        String sInput;
        int iInput = 0;
        int i = 1;

        while (!validInput) {
            clearConsole();
            System.out.printf("------------------- %s Config -------------------\n", name);
            System.out.println("Select a setting to edit:");

            // String storing the Keys for config.properties() at the index they will be accessed from.

            
            for (Map.Entry<String, JsonNode> e : config.properties()) {
                if (e.getKey().compareTo(name) != 0) {
                    System.out.printf("\t%d. %s: %s\n", i, e.getKey(), e.getValue().asString());
                    indeces.add(i-1, e);
                    i++;
                }
            }
            System.out.printf("\t%d. Exit\n", i);

            sInput = in.next();
            // Will throw NumberFormatException if the input is not an integer or double (ex: 2 or 2.)
            iInput = (int)Double.parseDouble(sInput);
            if (iInput == i) return;
            validInput = true;
        }
        
        validInput = false;
        i = 1;
        try {
            while (!validInput) {
            
                int selectedIndex = iInput;

                clearConsole();
                System.out.printf("------------------- %s Config -------------------\n", name);
                for (Map.Entry<String, JsonNode> e : indeces) {
                    if (selectedIndex == i)
                        System.out.printf("\t%d. %s: << %s >>\n", i, e.getKey(), e.getValue().asString());
                    else 
                        System.out.printf("\t%d. %s: %s\n", i, e.getKey(), e.getValue().asString());
                    i++;
                }
                System.out.print("\nNew value: ");

                sInput = in.next();
                // If the setting value is an integer, set it as an integer
                if (indeces.get(selectedIndex - 1).getValue().isInt()) {
                    // Will throw NumberFormatException if the input is not an integer or double (ex: 2 or 2.)
                    iInput = (int)Double.parseDouble(sInput);
                    config.put(indeces.get(selectedIndex - 1).getKey(), iInput);
                    validInput = true;
                } else { 
                //Assuming the only other option than an integer is a string
                    config.put(indeces.get(selectedIndex - 1).getKey(), sInput);
                    validInput = true;
                }
                
                // Write to config.json and return
                mapper.writerWithDefaultPrettyPrinter().writeValue(cFile, config);   // file specified when writing
            }
        } catch (NumberFormatException nfe) {

        }
    }
    }
}