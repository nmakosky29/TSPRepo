import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
public class Launcher {
    public static void main (String [] args) {
    	ReadSettings(); // just for testing, remove later
    }

    /**
     * Clears the current console.
     */
    public static void ClearConsole() {
        System.out.println("Clearing the console...");
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // TODO: complete this method
    /**
     * Reads the library folder at directory targetDir.
     * @param targetDir the library directory
     * @return List of game folders
     */
    public static List<File> ReadLibrary(URI targetDir) throws URISyntaxException {
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
                        .toURI()
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
    public static void ReadSettings() {
        String settingsFile = "settings.txt";
        File file = new File(settingsFile);
        
        // default settings
        int aiDifficulty = 2; // 1 = random, 2 = normal, 3 = hard
        int turnOrder = 1; // 1 = player first, 2 = AI first
        int boardSize = 2; // 1 = small, 2 = normal, 3 = large
        
        try {
        	// if file doesn't exist, creates one with default settings
        	if (!file.exists()) {
        		try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                    writer.println("aiDifficulty=" + aiDifficulty);
                    writer.println("turnOrder=" + turnOrder);
                    writer.println("boardSize=" + boardSize);
                }
        	}
        	
        	// reads integer value attached to settings
        	try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");

                    if (parts.length == 2) {
                        String key = parts[0].trim();
                        String value = parts[1].trim();

                        switch (key) {
                            case "aiDifficulty":
                                aiDifficulty = Integer.parseInt(value);
                                break;
                            case "turnOrder":
                                turnOrder = Integer.parseInt(value);
                                break;
                            case "boardSize":
                                boardSize = Integer.parseInt(value);
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}