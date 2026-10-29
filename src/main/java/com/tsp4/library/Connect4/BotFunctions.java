package com.tsp4.library.Connect4;

import java.util.ArrayList;

public class BotFunctions {

    protected static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    protected static void simulatePlay(Integer selection) {}

    private static class AIPlayNode {
        int maxDepth;
        AIPlayNode previousNode;
        ArrayList<Integer> futureNodes = new ArrayList<>();
        AIPlayNode play;
        char[][] board;
        int cols;
        int rows;
        char team;
        char emptyChar;
        int score;
        ArrayList<Integer> possiblePlays = new ArrayList<>();

        AIPlayNode(int maxDepth,
                   AIPlayNode previousNode,
                   AIPlayNode play,
                   char[][] board,
                   int rows,
                   int cols,
                   char team,
                   char emptyChar) {
            this.maxDepth = maxDepth;
            this. previousNode = previousNode;
            this.play = play;
            this.board = board;
            this.rows = rows;
            this.cols = cols;
            this.team = team;
            this.emptyChar = emptyChar;

            for (int i = 0; i < cols; i++) {
                if (board[0][i] == emptyChar) {
                    possiblePlays.add(i);
                }
            }
        }

        protected void checkForPossibleWin() {
            int totalConnectionScore = 0;
            double connectionWeight = 0.1;
            int totalEnemyNextNodeScore = 0;
            double enemyScoreWeight = 0.001;
            ArrayList<Integer> loosingPlays = new ArrayList<>();

            if (maxDepth >= 0) {
                for (Integer selection : possiblePlays) {

                }
            }
        }
    }

}

