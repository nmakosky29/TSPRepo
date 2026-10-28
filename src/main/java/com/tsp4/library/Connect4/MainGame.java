package library.Connect4;

public class MainGame {

    public static void main(String[] args) {

        // Inits an empty board and prints it for testing purposes
        int cols = 7;
        int rows = 6;
        char emptyChar = '.';

        char[][] board = BoardFunctions.initBoard(cols, rows, emptyChar);
        BoardFunctions.printBoard(board, 7, 6);

        // Waits 5 secs to test clearConsole() method
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupt status
            System.err.println("Thread was interrupted!");
        }

        BoardFunctions.testClearConsole();
    }

}
