/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnkgame.domain;
import datastructures.HashMap;
import datastructures.Stack;

/**
 *
 * @author julinden
 */
public class AiGomoku {
    private Board board;
    private int depth;
    private HashMap<String, Integer> calculatedValues;
    
    /**
     *
     * @param depth depth of minimax
     */
    public AiGomoku(Board board, int depth) {
        this.board = board;
        this.depth = depth;
        this.calculatedValues = new HashMap<>(99999999);
    }
    
    /**
     * Find empty squares surrounding occupied ones
     * @return array of coordinates
     */
    public Coordinate[] emptySquaresNearOccupied() {
        HashMap<Coordinate, Integer> emptySquares = new HashMap<>(board.n * board.m);
        Stack occupiedSquares = board.getOccupiedSquares();
        for(int z = 0; z<=occupiedSquares.getTop(); z++) {
            int[] coordinate = occupiedSquares.get(z);
            int i = coordinate[0];
            int j = coordinate[1];
            if (board.getGrid()[i][j] != 0) {
                if (j > 0) {
                    if(board.getGrid()[i][j-1] == 0) {
                        emptySquares.add(new Coordinate(i, j-1), null);
                    }
                    if (i > 0) {
                        if(board.getGrid()[i-1][j-1] == 0) {
                            emptySquares.add(new Coordinate(i-1, j-1), null);
                        }
                    }
                    if (i < board.n -1) {
                        if(board.getGrid()[i+1][j-1] == 0) {
                            emptySquares.add(new Coordinate(i+1, j-1), null);
                        }
                    }
                }
                if (j<board.m - 1) {
                    if(board.getGrid()[i][j+1] == 0) {
                        emptySquares.add(new Coordinate(i, j+1), null);
                    }
                    if (i > 0) {
                        if(board.getGrid()[i-1][j+1] == 0) {
                            emptySquares.add(new Coordinate(i-1, j+1), null);
                        }
                    }
                    if (i < board.n -1) {
                        if(board.getGrid()[i+1][j+1] == 0) {
                            emptySquares.add(new Coordinate(i+1, j+1), null);
                        }
                    }
                }
                if (i > 0) {
                    if(board.getGrid()[i-1][j] == 0) {
                        emptySquares.add(new Coordinate(i-1, j), null);
                    }
                }
                if (i < board.n - 1) {
                    if(board.getGrid()[i+1][j] == 0) {
                        emptySquares.add(new Coordinate(i+1, j), null);
                    }
                }
            }
        }
        
        return emptySquares.getCoordinates();
    }
    
    /**
     * Finds the best move for the current situation
     * @param player
     * @return coordinates of the best move
     */
    public int[] bestMoveFinder(int player) {
        int[] bestMove = new int[2];
        if(board.getOccupiedSquares().empty()) {
            bestMove[0] = board.m/2;
            bestMove[1] = board.n/2;
            return bestMove;
        }
        int value;
        Coordinate[] emptySquares = emptySquaresNearOccupied();
        if(player == -1) {
            value = Integer.MAX_VALUE;
            for (Coordinate coordinate : emptySquares) {
                board.placeStone(-1, coordinate.x, coordinate.y);
                int boardValue = maxValue(-20000000, 20000000, depth);
                board.removeStone(coordinate.x, coordinate.y);
                if (boardValue < value) {
                    value = boardValue;
                    bestMove[0] = coordinate.x;
                    bestMove[1] = coordinate.y;
                }
            }
        } else {
            value = Integer.MIN_VALUE;
            for (Coordinate coordinate : emptySquares) {
                board.placeStone(1, coordinate.x, coordinate.y);
                int boardValue = minValue(-20000000, 20000000, depth);
                board.removeStone(coordinate.x, coordinate.y);
                if (boardValue > value) {
                    value = boardValue;
                    bestMove[0] = coordinate.x;
                    bestMove[1] = coordinate.y;
                }
            }
        }
        
        return bestMove;    
    }
    
    /**
     * Check if this board value is stored in a hashmap, otherwise evaluate value and store.
     * @return heuristic value
     */
    public int evalBoard() {
        Integer boardValue = this.calculatedValues.get(board.toString());
        if(boardValue == null) {
            boardValue = board.evalBoard();
            this.calculatedValues.add(board.toString(), boardValue);
        }
        return boardValue;
    }
    
    /**
     * Maximizing part of minimax
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
        Coordinate[] emptySquares = emptySquaresNearOccupied();
        for (Coordinate coordinate : emptySquares) {
            if (board.getGrid()[coordinate.y][coordinate.x] == 0) {
                board.placeStone(1, coordinate.x, coordinate.y);
                int tempValue = minValue(alpha, beta, depth - 1);
                v = (tempValue > v) ? tempValue : v;
                board.removeStone(coordinate.x, coordinate.y);
                alpha = (v > alpha) ? v : alpha;
                if (alpha >= beta) {
                    return v;
                }
            }
        }
        return v;
    }
    
    /**
     * Minimizing part of minimax
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
        Coordinate[] emptySquares = emptySquaresNearOccupied();
        for (Coordinate coordinate : emptySquares) {
            if (board.getGrid()[coordinate.y][coordinate.x] == 0) {
                board.placeStone(-1, coordinate.x, coordinate.y);
                int tempValue = maxValue(alpha, beta, depth - 1);
                v = (tempValue < v) ? tempValue : v;
                board.removeStone(coordinate.x, coordinate.y);
                beta = (v < beta) ? v : beta;
                if (beta <= alpha) {
                    return v;
                }
            }
        }
        return v;
    }
}
