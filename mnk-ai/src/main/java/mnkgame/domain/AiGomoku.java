/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnkgame.domain;
import java.math.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
/**
 *
 * @author julinden
 */
public class AiGomoku {
    private GameLogic logic;
    private int bigNumber;
    private Board board;
    private int depth;
    
    /**
     *
     * @param logiikka
     */
    public AiGomoku(GameLogic logiikka, int depth) {
        this.logic = logiikka;
        this.bigNumber = 999999;
        this.board = this.logic.getBoard();
        this.depth = depth;
    }
    
    public Set<int[]> emptySquaresNearOccupied() {
        Set<int[]> emptySquares = new HashSet<>();
        Stack<int[]> occupiedSquares = board.getOccupiedSquares();
        for(int[] coordinate : occupiedSquares) {
            int i = coordinate[0];
            int j = coordinate[1];
            if (board.getGrid()[i][j] != 0) {
                if (j > 0) {
                    if(board.getGrid()[i][j-1] == 0) {
                        emptySquares.add(coordinate(i,j-1));
                    }
                    if (i > 0) {
                        if(board.getGrid()[i-1][j-1] == 0) {
                            emptySquares.add(coordinate(i-1,j-1));
                        }
                    }
                    if (i < board.n -1) {
                        if(board.getGrid()[i+1][j-1] == 0) {
                            emptySquares.add(coordinate(i+1,j-1));
                        }
                    }
                }
                if (j<board.m - 1) {
                    if(board.getGrid()[i][j+1] == 0) {
                        emptySquares.add(coordinate(i,j+1));
                    }
                    if (i > 0) {
                        if(board.getGrid()[i-1][j+1] == 0) {
                            emptySquares.add(coordinate(i-1,j+1));
                        }
                    }
                    if (i < board.n -1) {
                        if(board.getGrid()[i+1][j+1] == 0) {
                            emptySquares.add(coordinate(i+1,j+1));
                        }
                    }
                }
                if (i > 0) {
                    if(board.getGrid()[i-1][j] == 0) {
                        emptySquares.add(coordinate(i-1,j));
                    }
                }
                if (i < board.n - 1) {
                    if(board.getGrid()[i+1][j] == 0) {
                        emptySquares.add(coordinate(i+1,j));
                    }
                }
            }
        }
        
        return emptySquares;
    }
    
    public int[] coordinate(int i, int j) {
        int[] coordinate = new int[2];
        coordinate[0] = i;
        coordinate[1] = j;
        return coordinate;
    }
    
    /**
     * Finds the best move the current situation
     * @param player
     * @return coordinates of the best move
     */
    public int[] bestMoveFinder(int player) {
        int[] bestMove = new int[2];
        int value = Integer.MAX_VALUE;
        Set<int[]> emptySquares = emptySquaresNearOccupied();
        for (int[] coordinate : emptySquares) {
            board.placeStone(player, coordinate[1], coordinate[0]);
            int boardValue = alphaBetaValue(-player);
            board.removeStone(coordinate[1], coordinate[0]);
            if (boardValue < value) {
                value = boardValue;
                bestMove[0] = coordinate[1];
                bestMove[1] = coordinate[0];
                System.out.println(value);
                System.out.println(coordinate[1]);
                System.out.println(coordinate[0]);
                System.out.println("");
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
            return maxValue(-100000, 100000, depth);
        } else {
            return minValue(-100000, 100000, depth);
        }
    }
    
    /**
     *
     * @param alpha
     * @param beta
     * @return
     */
    public int maxValue(int alpha, int beta, int depth) {
        if(depth == 0) {
            int evalboard = board.evalBoard();
            return evalboard;
        }
        if (board.checkWin(-1)) {
            return -100000;
        }
        if (board.checkWin(1)) {
            return 100000;
        }
        if (board.checkFull()) {
            return 0;
        }
        int v = Integer.MIN_VALUE;
        
        Set<int[]> emptySquares = emptySquaresNearOccupied();
        for (int[] coordinate : emptySquares) {
            if (board.getGrid()[coordinate[0]][coordinate[1]] == 0) {
                board.placeStone(1, coordinate[1], coordinate[0]);
                int tempValue = minValue(alpha, beta, depth - 1);
                v = Math.max(v, tempValue);
                board.removeStone(coordinate[1], coordinate[0]);
                alpha = Math.max(alpha, v);
                if (alpha >= beta) {
                        return v;
                }
            }
        }
        return v;

//        for (int i = 0; i < board.n; i++) {
//            for (int j = 0; j < board.m; j++) {
//                if (board.getGrid()[i][j] == 0) {
//                    board.placeStone(1, j, i);
//                    int tempValue = minValue(alpha, beta, depth);
//                    v = Math.max(v, tempValue);
//                    board.removeStone(j, i);
//                    alpha = Math.max(alpha, v);
//                    if (alpha >= beta) {
//                        return v;
//                    }
//                    
//                }
//            }
//        }
//        return v;
    }
    
    /**
     *
     * @param alpha
     * @param beta
     * @return
     */
    public int minValue(int alpha, int beta, int depth) {
        if(depth == 0) {
            int evalboard = board.evalBoard();
            return evalboard;
        }
        if (board.checkWin(1)) {
            return 100000;
        }
        if (board.checkWin(-1)) {
            return -100000;
        }
        if (board.checkFull()) {
            return 0;
        }
        int v = Integer.MAX_VALUE;
        
        Set<int[]> emptySquares = emptySquaresNearOccupied();
        for (int[] coordinate : emptySquares) {
            if (board.getGrid()[coordinate[0]][coordinate[1]] == 0) {
                board.placeStone(-1, coordinate[1], coordinate[0]);
                int tempValue = maxValue(alpha, beta, depth - 1);
                v = Math.min(v, tempValue);
                board.removeStone(coordinate[1], coordinate[0]);
                beta = Math.min(beta, v);
                if (beta <= alpha) {
                    return v;
                }
            }
        }
        return v;
        
//        for (int i = 0; i < board.n; i++) {
//            for (int j = 0; j < board.m; j++) {
//                if (board.getGrid()[i][j] == 0) {
//                    board.placeStone(-1, j, i);
//                    int tempValue = maxValue(alpha, beta, depth);
//                    v = Math.min(v, tempValue);
//                    board.removeStone(j, i);
//                    beta = Math.min(beta, v);
//                    if (beta <= alpha) {
//                        return v;
//                    }
//                }
//            }
//        }
//        return v;
    }
    
    
}
