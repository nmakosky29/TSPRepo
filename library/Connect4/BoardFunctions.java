package library.Connect4;

public class BoardFunctions {

    protected static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    protected static char[][] initBoard(int cols, int rws, char team1, char team2, char emptyChar) {
        char[][] board = new char[cols][rws];

        for (int i = 0; i < rws - 1; i++) {
            for (int j = 0; j < cols - 1; j++) {
                System.out.print(" | " + emptyChar);
            }
            System.out.print("\n");
        }

        return board;
    }

    private void printBoardBottom() {

    }


}
