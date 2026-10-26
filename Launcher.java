import java.io.*;
import java.util.*;
import java.util.Map.Entry;
public class Launcher {
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main (String [] args) throws FileNotFoundException {
        ArrayList<File> library = (ArrayList<File>) ReadLibrary("library");

        // Greeting Dialogue
        System.out.println("""
                           ------------------- SIMPLE GAMES LAUNCHER -------------------
                           \t1. View Library
                           \t2. View Settings""");

        // If used again, validInput must be reset to false
        boolean validInput = false;
        char option = 0;
        while (!validInput) {
            int readbyte;
            try{
                readbyte = System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            option = (char) readbyte;

            if (option == '1' || option == '2') 
                validInput = true;
            else
                System.out.println("Invalid Input\nType 1. or 2.");
        }

        System.out.println("\n");

        if (option == '1') {
            System.out.println("------------------- LIBRARY -------------------");

            if (library.isEmpty()) {
                System.out.println("Library is empty!");
            }
            int i = 1;
            for (File f : library) {
                System.out.println(i + ". " +f.getName());
                i++;
            }
        }
        if (option == '2') {
            System.out.println("------------------- SETTINGS -------------------");
            File parentDir = new File(
                Launcher.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath()
            ).getParentFile();
            File settingsFile = new File(parentDir, "settings.txt");
            Map<String,String> settings = parseConfig(settingsFile);
            for (Entry<String,String> e : settings.entrySet()) {
                System.out.println(e.getKey() + ": " + e.getValue());
            }
        }
    }

    // TODO: Finish testing/debugging this method
    /**
     * Clears the current console. WIP
     * 
     */
    public static void ClearConsole() {
        System.out.println("Clearing the console...");
        System.out.print("\033[H\033[2J");
        System.out.flush();
        try{
        Thread.sleep(2000);
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupt status
            System.err.println("Thread was interrupted!");
        }
    }

    // TODO: Test this method
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

    /**
     * 
     */
    public static Map<String,String> parseConfig (File c) throws FileNotFoundException {
        HashMap<String,String> configMap = new HashMap<String,String>();
        Scanner sc = new Scanner(c);

        String line,key,value = ""; 
        while (sc.hasNext()) {
            line = sc.nextLine();
            String[] s = line.split(":");
            key = s[0];
            value = s[1];
            configMap.put(key, value);
        }

        return configMap;

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