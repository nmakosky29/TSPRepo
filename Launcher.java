import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
public class Launcher {
    public static void main (String [] args) {

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
}