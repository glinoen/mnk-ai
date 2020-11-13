/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnkgame.domain;

/**
 *
 * @author julinden
 */
public class GameLogic {
    private Board board;
    private int k;
    private int currentPlayer;
    
    /**
     *
     */
    public GameLogic() {
        
    }
    
    /**
     * Creates a new game with empty board
     * @param m = width
     * @param n = height
     * @param k = how many in row to win
     */
    public void newGame(int m, int n, int k) {
        this.board = new Board(m, n, k);
        this.k = k;
        this.currentPlayer = 1;
    }
    
    /**
     * This method checks if stone can be placed on the spot
     * @param x
     * @param y
     * @return
     */
    public boolean stonePlacer(int x, int y) {
        return board.placeStone(this.currentPlayer, x, y);
    }
    
    /**
     * Check if current player has won.
     * @return
     */
    public boolean checkWin() {
        return board.checkWin(this.currentPlayer);
    }
    
    /**
     *
     * @return
     */
    public int changePlayer() {
        currentPlayer = -currentPlayer;
        return currentPlayer;
    }
    
    /**
     *
     * @return
     */
    public Board getBoard() {
        return this.board;
    }
    
    /**
     *
     * @return
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * Check if board is full and the game is draw.
     * @return
     */
    public boolean checkFull() {
        return this.board.checkFull();
    }
}
