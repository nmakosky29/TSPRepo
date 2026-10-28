package com.tsp4.library.Connect4;

import java.util.ArrayList;

import static com.tsp4.library.Connect4.MainGame.*;

public class BotFunctions {

    protected static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private static class AIPlayNode {
        int maxDepth;
        AIPlayNode previousNode;
        AIPlayNode[] futureNodes;
        AIPlayNode play;
        String[][] board;
        String team = "O";
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


    }
}

