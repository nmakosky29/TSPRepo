public static void main (String [] args) {
    
}

public static void ClearConsole() {
    System.out.println("Clearing the console...");
    System.out.print("\033[H\033[2J");
    System.out.flush();
}