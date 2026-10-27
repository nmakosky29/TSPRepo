package library.Connect4;

public class ConnectFour {

    public static void main(String[] args) {

        // Inits an empty board and prints it for testing purposes.
        int cols = 7;
        int rows = 6;
        char emptyChar = '.';

        char[][] board = BoardFunctions.initBoard(cols, rows, emptyChar);
        BoardFunctions.printBoard(board, 7, 6);
    }

}
