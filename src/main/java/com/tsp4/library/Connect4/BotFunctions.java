package com.tsp4.library.Connect4;

import java.util.ArrayList;

import static com.tsp4.library.Connect4.MainGame.*;

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
        String[][] board;
        String team = botChar;
        int score;
        ArrayList<Integer> possiblePlays;

        AIPlayNode(int maxDepth,
                   AIPlayNode previousNode,
                   AIPlayNode play,
                   String[][] board) {
            this.maxDepth = maxDepth;
            this. previousNode = previousNode;
            this.play = play;
            this.board = board;

            for (int i = 0; i < cols; i++) {
                if (board[0][i].equals(emptyChar)) {
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

