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
    
    public GameLogic() {
        
    }
    
    public void newGame(int m, int n, int k) {
        this.board = new Board(m, n, k);
        this.k = k;
        this.currentPlayer = 1;
    }
    
    public boolean stonePlacer(int x, int y) {
        return board.placeStone(this.currentPlayer, x, y);
    }
    
    public boolean checkWin() {
        return board.checkWin(this.currentPlayer);
    }
    
    public int changePlayer() {
        currentPlayer = -currentPlayer;
        return currentPlayer;
    }
    
    public Board getBoard() {
        return this.board;
    }
    
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    
    public boolean checkFull() {
        return this.board.checkFull();
    }
}
