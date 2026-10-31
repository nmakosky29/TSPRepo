package com.tsp4.library.Connect4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class BotFunctions {

    private static final Random random = new Random();

    // Immutable objects that store data (think returning multiple values from a method)
    private record SimResult(char[][] board, int connected) {}
    private record WinCheck(char team, int selection) {}

    /**
     * Returns a value between 0,1.
     * @param x
     * @return
     */
    private static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    /**
     * Copies the board to simulate a play. Looks in all directions at a specific point.
     * @param b
     * @param selection
     * @param team
     * @param emptyChar
     * @return a SimResult record of the simulated board and the longest string of pieces at that point.
     */
    private static SimResult simulatePlay(char[][] b, int selection, char team, char emptyChar) {
        char[][] newBoard = BoardFunctions.copyBoard(b);

        // Calculates the row the piece will land in
        int row = BoardFunctions.dropRow(newBoard, selection, emptyChar);
        newBoard[row][selection] = team;

        // Checks for a win and returns longest piece string at that point
        return new SimResult(newBoard, BoardFunctions.checkWin(newBoard, row, selection, team));
    }

    /**
     * Selects the bot's next move.
     * @param b
     * @param maxDepth
     * @param emptyChar
     * @param playerChar
     * @param botChar
     * @return the column index of the move.
     */
    private static int aiSelect(char[][] b, int maxDepth, char emptyChar, char playerChar, char botChar) {

        // Creates the root node, which represents the next immediate play
        AIPlayNode baseNode = new AIPlayNode(maxDepth, null, -1, BoardFunctions.copyBoard(b), botChar,
                playerChar, emptyChar, 0);

        // Records the team with the best play and that play's column
        WinCheck win = baseNode.checkForPossibleWin();

        // Clear path to victory
        if (win.team() == botChar) return win.selection();

        // No obvious winning moves
        if (!baseNode.possiblePlays.isEmpty()) {

            // Selects a random play from possiblePlays
            int selection = baseNode.possiblePlays.get(random.nextInt(baseNode.possiblePlays.size()));

            // Creates a list to store move candidates and shuffles them so the bot chooses a random column, rather
            // than the first column every time. Then it chooses the candidate with the lowest player score (best
            // possible bot move) and selects it.
            ArrayList<AIPlayNode> candidates = new ArrayList<>(baseNode.futureNodes);
            Collections.shuffle(candidates, random);
            AIPlayNode lowest = candidates.stream()
                    .filter(n -> baseNode.possiblePlays.contains(n.play))
                    .min(Comparator.comparingDouble(n -> n.score))
                    .orElse(null);

            // if the best possible move exists and is an available move, that is the bot's selection
            if (lowest != null && baseNode.possiblePlays.contains(lowest.play)) selection = lowest.play;

            // Otherwise, return the random move
            return selection;
        }

        // Every play loses, so pick any available column
        ArrayList<Integer> losingButLegalPlays = new ArrayList<>();
        for (int c = 0; c < b[0].length; c++) {
            if (b[0][c] == emptyChar) losingButLegalPlays.add(c);
        }
        return losingButLegalPlays.get(random.nextInt(losingButLegalPlays.size()));
    }

    /**
     * Selects and places the bot's move with animations. Ideally, only method called to get the bot's move.
     * This may change if need be, such as wanting to do something else instead of the animations laid out here.
     * @param b
     * @param maxDepth
     * @param emptyChar
     * @param playerChar
     * @param botChar
     * @return true if the bot has won.
     */
    protected static boolean aiPlay(char[][] b, int maxDepth, char emptyChar, char playerChar, char botChar) {

        // Calculates the bot's move and finds the landing row
        int selection = aiSelect(b, maxDepth, emptyChar, playerChar, botChar);
        int landingRow = BoardFunctions.dropRow(b, selection, emptyChar);

        // Falling animation
        for (int r = 0; r < landingRow; r++) {
            b[r][selection] = botChar;
            redraw(b);
            sleep(300);
            b[r][selection] = emptyChar;
        }
        b[landingRow][selection] = botChar;
        redraw(b);

        // Blink if the column is now full
        if (landingRow == 0) {
            for (int i = 0; i < 5; i++) {
                b[0][selection] = emptyChar;
                redraw(b);
                sleep(100);
                b[0][selection] = botChar;
                redraw(b);
                sleep(100);
            }
        }

        return BoardFunctions.checkWin(b, landingRow, selection, botChar) >= 4;
    }

    /**
     * Sleep method.
     * @param ms
     */
    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Method to redraw board.
     * @param b
     */
    private static void redraw(char[][] b) {
        BoardFunctions.clearConsole();
        BoardFunctions.printBoard(b, b.length, b[0].length);
    }

    /**
     * Static Node class that represents a move.
     */
    private static class AIPlayNode {
        int maxDepth;
        AIPlayNode previousNode;
        ArrayList<AIPlayNode> futureNodes = new ArrayList<>();
        int play;
        char[][] b;
        char team;
        char enemy;
        char emptyChar;
        double score;
        ArrayList<Integer> possiblePlays = new ArrayList<>();

        AIPlayNode(int maxDepth,
                   AIPlayNode previousNode,
                   int play,
                   char[][] b,
                   char team,
                   char enemy,
                   char emptyChar,
                   double score) {
            this.maxDepth = maxDepth;
            this. previousNode = previousNode;
            this.play = play;
            this.b = b;
            this.team = team;
            this.enemy = enemy;
            this.emptyChar = emptyChar;
            this.score = score;

            // If a column is not full, mark it as playable
            for (int c = 0; c < this.b[0].length; c++) {
                if (b[0][c] == emptyChar) possiblePlays.add(c);
            }
        }

        /**
         * Simulates future moves assuming player plays best move
         * @return WinCheck
         */
        protected WinCheck checkForPossibleWin() {
            double totalConnectionScore = 0;
            double connectionWeight = 0.1;
            double totalEnemyNextNodeScore = 0;
            double enemyScoreWeight = 0.001;
            ArrayList<Integer> losingPlays = new ArrayList<>();

            if (maxDepth >= 0) {

                // Simulates the next plays, up to the maxDepth
                for (Integer selection : possiblePlays) {
                    SimResult sim = simulatePlay(b, selection, team, emptyChar);

                    // Wins right now
                    if (sim.connected() >= 4) return new WinCheck(team, selection);
                    totalConnectionScore += sim.connected();

                    // Simulates next best move
                    AIPlayNode child = new AIPlayNode(maxDepth - 1, this, selection,
                            sim.board(), enemy, team, emptyChar, sim.connected());
                    futureNodes.add(child);
                    WinCheck result = child.checkForPossibleWin();
                    totalEnemyNextNodeScore = child.score;

                    if (result.team() == team) return new WinCheck(team, selection);
                    if (result.team() == enemy) losingPlays.add(selection);
                }
            }

            // Ensures it selects no losing plays
            possiblePlays.removeAll(losingPlays);
            score = sigmoid(totalConnectionScore * connectionWeight
                            - totalEnemyNextNodeScore * enemyScoreWeight);

            if (!possiblePlays.isEmpty()) return new WinCheck(emptyChar, -2);
            return new WinCheck(enemy, -1);
        }
    }

}

