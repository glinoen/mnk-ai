/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnkgame.domain;
import java.math.*;
/**
 *
 * @author julinden
 */
public class Ai {
    private GameLogic logic;
    private int bigNumber;
    private Board board;
    
    /**
     *
     * @param logiikka
     */
    public Ai(GameLogic logiikka) {
        this.logic = logiikka;
        this.bigNumber = 999999;
        this.board = this.logic.getBoard();
    }
    
    /**
     * Finds the best move the current situation
     * @param player
     * @return coordinates of the best move
     */
    public int[] bestMoveFinder(int player) {
        int[] bestMove = new int[2];
        int value = 100;
        for (int i = 0; i < board.n; i++) {
            for (int j = 0; j < board.m; j++) {
                if (board.getGrid()[i][j] == 0) {
                    board.placeStone(player, j, i);
                    int boardValue = alphaBetaValue(-player);
                    board.removeStone(j, i);
                    if (boardValue < value) {
                        value = boardValue;
                        bestMove[0] = j;
                        bestMove[1] = i;
                    }
                }
            }
        }
        
        return bestMove;
        
        
        
//        
//        if (player == 1) {
//            value = -1;
//            for (int i = 0; i < board.n; i++) {
//                for (int j = 0; j < board.m; j++) {
//                    if (currentBoard.getGrid()[i][j] == 0) {
//                            //copiedBoard = currentBoard.makeCopy();
//                            board.placeStone(1, j, i);
//                            int boardValue = alphaBetaValue(board, 1);
//                            if (boardValue > value) {
//                                value = boardValue;
//                                bestMove[0] = j;
//                                bestMove[1] = i;
//                            }
//                            board.removeStone(j, i);
//                    }
//                }
//            }
//        } else {
//            value = 1;
//            for (int i = 0; i < board.n; i++) {
//                for (int j = 0; j < board.m; j++) {
//                    if (currentBoard.getGrid()[i][j] == 0) {
//                            //copiedBoard = currentBoard.makeCopy();
//                            board.placeStone(-1, j, i);
//                            int boardValue = alphaBetaValue(board, -1);
//                            System.out.println("boardis: " + boardValue);
//                            if (boardValue < value) {
//                                value = boardValue;
//                                bestMove[0] = j;
//                                bestMove[1] = i;
//                            }
//                            board.removeStone(j, i);
//                    }
//                }
//            }
//        }
//        System.out.println(value);
//        return bestMove;
        
    }
    
    /**
     *
     * @param player
     * @return
     */
    public int alphaBetaValue(int player) {
        if (player ==  1) {
            return maxValue(-1, 1);
        } else {
            return minValue(-1, 1);
        }
    }
    
    /**
     *
     * @param alpha
     * @param beta
     * @return
     */
    public int maxValue(int alpha, int beta) {
        if (board.checkWin(-1)) {
            return -10;
        }
        if (board.checkWin(1)) {
            return 10;
        }
        if (board.checkFull()) {
            return 0;
        }
        int v = Integer.MIN_VALUE;

        for (int i = 0; i < board.n; i++) {
            for (int j = 0; j < board.m; j++) {
                if (board.getGrid()[i][j] == 0) {
                    board.placeStone(1, j, i);
                    int tempValue = minValue(alpha, beta);
                    v = Math.max(v, tempValue);
                    board.removeStone(j, i);
                    alpha = Math.max(alpha, v);
                    if (alpha >= beta) {
                        return v;
                    }
                    
                }
            }
        }
        return v;
    }
    
    /**
     *
     * @param alpha
     * @param beta
     * @return
     */
    public int minValue(int alpha, int beta) {
        if (board.checkWin(1)) {
            return 10;
        }
        if (board.checkWin(-1)) {
            return -10;
        }
        if (board.checkFull()) {
            return 0;
        }
        int v = Integer.MAX_VALUE;
        
        for (int i = 0; i < board.n; i++) {
            for (int j = 0; j < board.m; j++) {
                if (board.getGrid()[i][j] == 0) {
                    board.placeStone(-1, j, i);
                    int tempValue = maxValue(alpha, beta);
                    v = Math.min(v, tempValue);
                    board.removeStone(j, i);
                    beta = Math.min(beta, v);
                    if (beta <= alpha) {
                        return v;
                    }
                }
            }
        }
        return v;
    }
    
    
}
