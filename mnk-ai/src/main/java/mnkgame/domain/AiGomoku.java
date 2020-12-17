/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnkgame.domain;
import datastructures.Stack;
import java.math.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author julinden
 */
public class AiGomoku {
    private GameLogic logic;
    private int bigNumber;
    private Board board;
    private int depth;
    private HashMap<String, Integer> calculatedValues;
    private int[] coordinate;
    
    /**
     *
     * @param logiikka
     */
    public AiGomoku(GameLogic logiikka, int depth) {
        this.logic = logiikka;
        this.bigNumber = 999999;
        this.board = this.logic.getBoard();
        this.depth = depth;
        this.calculatedValues = new HashMap<>();
        this.coordinate = new int[2];
    }
    
    public Set<int[]> emptySquaresNearOccupied() {
        Set<int[]> emptySquares = new HashSet<>();
        Stack occupiedSquares = board.getOccupiedSquares();
        for(int z = 0; z<=occupiedSquares.getTop(); z++) {
            int[] coordinate = occupiedSquares.get(z);
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
     * Finds the best move for the current situation
     * @param player
     * @return coordinates of the best move
     */
    public int[] bestMoveFinder(int player) {
        int[] bestMove = new int[2];
        if(board.getOccupiedSquares().empty()) {
            System.out.println("firstAi");
            bestMove[0] = board.m/2;
            bestMove[1] = board.n/2;
            return bestMove;
        }
        int value;
        Set<int[]> emptySquares = emptySquaresNearOccupied();
        if(player == -1) {
            value = Integer.MAX_VALUE;
            for (int[] coordinate : emptySquares) {
                board.placeStone(-1, coordinate[1], coordinate[0]);
                int boardValue = maxValue(-20000000, 20000000, depth);
                board.removeStone(coordinate[1], coordinate[0]);
                if (boardValue < value) {
                    value = boardValue;
                    bestMove[0] = coordinate[1];
                    bestMove[1] = coordinate[0];
                }
            }
        } else {
            value = Integer.MIN_VALUE;
            for (int[] coordinate : emptySquares) {
                board.placeStone(1, coordinate[1], coordinate[0]);
                int boardValue = minValue(-20000000, 20000000, depth);
                board.removeStone(coordinate[1], coordinate[0]);
                if (boardValue > value) {
                    value = boardValue;
                    bestMove[0] = coordinate[1];
                    bestMove[1] = coordinate[0];
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
            return maxValue(-20000000, 20000000, depth);
        } else {
            return minValue(-20000000, 20000000, depth);
        }
    }
    
    public int evalBoard() {
        Integer boardValue = this.calculatedValues.get(board.toString());
        if(boardValue == null) {
            boardValue = board.evalBoard();
            this.calculatedValues.put(board.toString(), boardValue);
        }
        return boardValue;
    }
    
    /**
     *
     * @param alpha
     * @param beta
     * @return
     */
    public int maxValue(int alpha, int beta, int depth) {
        int boardValue = evalBoard();
        if(depth == 0 || boardValue == 10000000 || boardValue == -10000000) {
            return boardValue;
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
        int boardValue = evalBoard();
        if(depth == 0 || boardValue == 10000000 || boardValue == -10000000) {
            return boardValue;
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
